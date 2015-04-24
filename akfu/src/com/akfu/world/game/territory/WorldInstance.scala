package com.akfu.world.game.territory

import com.akfu.common.concurrent.AtomicWorker
import com.ankamagames.wakfu.common.datas.BasicCharacterInfo
import com.akfu.common.concurrent.WorkerTask
import com.ankamagames.framework.kernel.core.maths.Direction8Path
import com.ankamagames.wakfu.client.core.game.protector.Territory
import scala.collection.mutable.HashMap
import com.akfu.world.util.ActorHelper
import com.akfu.world.util.ClientDispatcher
import com.akfu.world.game.entity.PlayerCharacter
import com.ankamagames.wakfu.common.datas.CriterionUserType
import com.akfu.world.game.entity.AbstractCharacterInfo
import scala.collection.mutable.Set
import com.ankamagames.baseImpl.common.clientAndServer.utils.PartitionList
import com.ankamagames.wakfu.client.core.world.WorldInfoManager.WorldInfo
import com.ankamagames.framework.kernel.core.maths.MathHelper
import com.akfu.world.game.entity.AbstractCharacterInfo
import com.akfu.world.game.entity.AbstractCharacterInfo
import com.akfu.common.network.protocol.message.game.serverToClient.actor.ActorSpawnMessage
import com.akfu.common.network.protocol.message.game.serverToClient.actor.ActorDespawnMessage
import com.akfu.common.network.protocol.message.game.serverToClient.ActorMoveToMessage
import com.akfu.world.game.entity.AbstractPlayerCharacter
import com.akfu.common.network.protocol.message.game.serverToClient.ActorPathUpdateMessage
import scala.collection.mutable.ListBuffer
import com.akfu.common.network.protocol.message.chat.serverToClient.VicinityContentMessage
import com.akfu.common.network.protocol.message.chat.clientToServer.UserContentMessage

final case class Dispatch(message: Any) extends WorkerTask
final case class ChatEntity(entity: AbstractCharacterInfo, message: UserContentMessage) extends WorkerTask
final case class CheckEntrance(entity: AbstractCharacterInfo) extends WorkerTask
final case class AddEntity(entity: AbstractCharacterInfo) extends WorkerTask
final case class RemoveEntity(entity: AbstractCharacterInfo) extends WorkerTask
final case class MoveEntity(entity: AbstractCharacterInfo, movementPath: Direction8Path) extends WorkerTask

final class WorldInstance(worldModel: WorldInfo) extends AtomicWorker
with ActorHelper
with ClientDispatcher
with PartitionedTerritory {
  
 private var entityById = HashMap[Long, AbstractCharacterInfo]()
 
 {
   val iterator = worldModel.getTerritoriesIterator
   while(iterator hasNext) {
     iterator advance()
     val territory = iterator value()
     val partitionIterator = territory.getPartitions.list().iterator()
     while(partitionIterator hasNext) {
       partitions += (partitionIterator.next() -> Set[AbstractCharacterInfo]())
     }
   }
   
   log info "world partitions created : " + partitions.size
 }
  
  override def receive = {
    case Dispatch(message) => gossip(message)
    case ChatEntity(entity, message) => onEntityChat(entity, message)
    case AddEntity(entity) => onEntityEnter(entity)
    case RemoveEntity(entity) =>  onEntityLeave(entity)
    case MoveEntity(entity, movementPath) => onEntityMove(entity, movementPath)
    case CheckEntrance(entity) => if(onCheckEntrance(entity)) self ! AddEntity(entity)
    case unhandled: Any => super.receive(unhandled)
  }
 
  def fireAddEntity(entity: AbstractCharacterInfo) = fireEvent(AddEntity(entity))
  def fireRemoveEntity(entity: AbstractCharacterInfo) = fireEvent(RemoveEntity(entity))
  def fireMoveEntity(entity: AbstractCharacterInfo, movementPath: Direction8Path) = fireEvent(MoveEntity(entity, movementPath))
  
  def hasEntity(id: Long) = entityById contains id
  def getEntity(id: Long) = if(entityById contains id) entityById(id) else null
  
  def getEntitiesInPartition(id: Int): Iterable[AbstractCharacterInfo] = 
    entityById.values filter(_.getCurrentPartitionId == id)
  
  def getEntitiesInPartitions(partitions: List[Int]): List[AbstractCharacterInfo] = {
    var entities = Set[AbstractCharacterInfo]()
    for(partition <- partitions) 
      entities ++= getEntitiesInPartition(partition)
    return entities toList
  }
  
  def onEntityChat(entity: AbstractCharacterInfo, message: UserContentMessage) {        
    // TODO: CHECK MESSAGE TYPE TO KNOW HOW TO DISPATCH
    dispatchSurrounding(entity getCurrentPartitionId, new VicinityContentMessage(entity getName, entity getId, message content))
  }
  
  def onCheckEntrance(entity: AbstractCharacterInfo) = 
    entity.getInstanceId == worldModel.m_worldId
 
  def onEntityEnter(entity: AbstractCharacterInfo) {
    
    dispatchToClient(entity getClientId, new ActorSpawnMessage(getSurroundingEntities(entity getCurrentPartitionId, List(entity))))
        
    entityById += (entity.getId -> entity)
    
    dispatchSurrounding(entity getCurrentPartitionId, new ActorSpawnMessage(List(entity)), List(entity))
    addToSurroudingPartitions(entity, entity getSurroundingPartitions)
    
    addClientListener(entity getClientId)    
  }
     
  def onEntityMove(entity: AbstractCharacterInfo, path: Direction8Path) {       
    val lastPartition = entity getCurrentPartitionId()
    val lastSurrounding = entity getSurroundingPartitions()       
    
    dispatchSurrounding(entity getCurrentPartitionId, new ActorPathUpdateMessage(entity getId, path), List(entity))
    
    entity setPosition(path getEndingPosition)    
    entity setDirection(path getStep(path.steps() - 1) direction)
                
    if(entity.isInstanceOf[PlayerCharacter]) {
      val player = entity.asInstanceOf[PlayerCharacter]
      player.setCurrentPath(path)
    }
    
    val currentSurrouding = entity getSurroundingPartitions()
    
    if(entity.getCurrentPartitionId != lastPartition) {
      log info "entity entered in a new partition"
      
      val lastSurroundingDiffCurrent = lastSurrounding diff currentSurrouding
      val currentSurroundingDiffLast = currentSurrouding diff lastSurrounding
            
      removeFromSurroundingPartitions(entity, lastSurroundingDiffCurrent)     
      addToSurroudingPartitions(entity, currentSurroundingDiffLast)
       
      val outOfSightEntities = getEntitiesInPartitions(lastSurroundingDiffCurrent)   
      val newlyVisibleEntities = getEntitiesInPartitions(currentSurroundingDiffLast)
       
      if(outOfSightEntities.size > 0)
        dispatchToClients(outOfSightEntities map(_ getClientId), new ActorDespawnMessage(List(entity)))
      if(newlyVisibleEntities.size > 0)
        dispatchToClients(newlyVisibleEntities map(_ getClientId), new ActorSpawnMessage(List(entity)))
      
      log info "entity discovered " + newlyVisibleEntities.length + " entities"
      log info "entity lost sight on " + outOfSightEntities.length + " entities"
      if(outOfSightEntities.size > 0 || newlyVisibleEntities.size > 0)
        dispatchMultiToClient(entity getClientId, 
            List(new ActorSpawnMessage(newlyVisibleEntities), new ActorDespawnMessage(outOfSightEntities)))
    }
  }  
  
  def onEntityLeave(entity: AbstractCharacterInfo) {
    entityById -= entity getId()
        
    dispatchSurrounding(entity getCurrentPartitionId, new ActorDespawnMessage(List(entity)))
    removeFromSurroundingPartitions(entity, entity getSurroundingPartitions())
    
    removeClientListener(entity getClientId)
  }
}