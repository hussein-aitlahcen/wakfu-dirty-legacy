package com.akfu.world.game.territory

import com.akfu.common.concurrent.AtomicWorker
import com.ankamagames.wakfu.common.datas.BasicCharacterInfo
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.HashMap
import com.akfu.common.concurrent.WorkerTask
import akka.actor.ActorRef
import com.ankamagames.wakfu.client.core.game.protector.Territory

final case class AddEntity(entity: BasicCharacterInfo) extends WorkerTask
final case class RemoveEntity(entity: BasicCharacterInfo) extends WorkerTask

final class TerritoryInstance(uniqueId: Int, territoryModel: Territory) extends AtomicWorker {
  private var entityById = HashMap[Long, BasicCharacterInfo]()
  
  override def receive = {
    case AddEntity(actor) => 
    case RemoveEntity(actor) =>  
    case unhandled: Any => super.receive(unhandled)
  }
  
  private def onEntityEnter(entity: BasicCharacterInfo) {
    if(entityById.contains(entity.getId)) return
    entityById += (entity.getId -> entity)
    
    log info "entity enter in territory => " + territoryModel.getId
  }
  
  private def onEntityLeave(entity: BasicCharacterInfo) {
    if(!entityById.contains(entity.getId)) return
    entityById -= entity.getId
        
    log info "entity left territory => " + territoryModel.getId
  }
}