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
import com.akfu.world.game.entity.AbstractCharacterInfo

final case class CheckEntrance(entity: AbstractCharacterInfo) extends WorkerTask
final case class AddEntity(entity: AbstractCharacterInfo) extends WorkerTask
final case class RemoveEntity(entity: AbstractCharacterInfo) extends WorkerTask
final case class MoveEntity(entityId: Long, movementPath: Direction8Path) extends WorkerTask

abstract class AbstractField extends AtomicWorker with ActorHelper with ClientDispatcher {
 private var entityById = HashMap[Long, AbstractCharacterInfo]()
  
  override def receive = {
    case AddEntity(entity) => onEntityEnter(entity)
    case RemoveEntity(entity) =>  onEntityLeave(entity)
    case MoveEntity(entityId, movementPath) => onEntityMove(entityId, movementPath)
    case CheckEntrance(entity) => if(onCheckEntrance(entity)) self ! AddEntity(entity)
    case unhandled: Any => super.receive(unhandled)
  }
  
  def hasEntity(id: Long) = entityById contains id
  def getEntity(id: Long) = if(entityById contains id) entityById(id) else null
  
  protected def onEntityEnter(entity: AbstractCharacterInfo) = {
    if(entityById.contains(entity.getId)) false
    entityById += (entity.getId -> entity)
    
    // TODO: gossip actorspawn
    if(entity.is(CriterionUserType.PLAYER_CHARACTER)) {
      val playerCharacter = entity.asInstanceOf[PlayerCharacter]
      if(playerCharacter hasClient) {
        addClientListener(playerCharacter getClientId)
      }
    }
   
    log info "entity enter territory : entityId=" + entity.getId
    
    true
  }
  
  protected def onEntityLeave(entity: AbstractCharacterInfo): Boolean = {
    if(!hasEntity(entity getId)) return true
    entityById -= entity.getId
    
    // TODO: gossip actordespawn
    if(entity.is(CriterionUserType.PLAYER_CHARACTER)) {
      val playerCharacter = entity.asInstanceOf[PlayerCharacter]
      if(playerCharacter hasClient) {
        removeClientListener(playerCharacter getClientId)
      }
    }
    log info "entity left territory : entityId=" + entity.getId
    return false
  }
  
  protected def onCheckEntrance(entity: AbstractCharacterInfo): Boolean
  protected def onEntityMove(entityId: Long, movementPath: Direction8Path): Boolean  
}