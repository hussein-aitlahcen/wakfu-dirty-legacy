package com.akfu.world.game.territory

import com.akfu.common.concurrent.AtomicWorker
import com.ankamagames.wakfu.common.datas.BasicCharacterInfo
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.HashMap
import com.akfu.common.concurrent.WorkerTask
import akka.actor.ActorRef
import com.ankamagames.wakfu.client.core.game.protector.Territory
import com.ankamagames.wakfu.common.datas.CriterionUserType
import com.akfu.world.game.entity.PlayerCharacter
import akka.routing.Listen
import akka.routing.Deafen
import com.akfu.common.concurrent.DeafenMe
import com.akfu.common.concurrent.ListenMe
import com.ankamagames.framework.kernel.core.maths.Direction8Path
import com.ankamagames.baseImpl.common.clientAndServer.game.pathfind.PathFinder
import com.ankamagames.wakfu.common.game.effect.runningEffect.util.movementEffect.PathComputer
import com.ankamagames.baseImpl.common.clientAndServer.game.pathfind.PathChecker
import com.akfu.world.game.entity.AbstractCharacterInfo

final class TerritoryInstance(val territoryModel: Territory) extends AbstractField { 
  
  protected def onCheckEntrance(entity: AbstractCharacterInfo) = territoryModel.containsWorldPosition(entity getWorldCellX, entity getWorldCellY)
  
  protected def onEntityMove(id: Long, movementPath: Direction8Path): Boolean = {
    val entity = getEntity(id)
    if(entity == null) return false
    
    
    
    return true
  }
}