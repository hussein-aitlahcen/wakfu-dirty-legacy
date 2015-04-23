package com.akfu.world.game.territory

import com.ankamagames.wakfu.client.core.world.WorldInfoManager.WorldInfo
import com.ankamagames.wakfu.client.core.world.WorldInfoManager.WorldInfo
import com.ankamagames.wakfu.common.datas.BasicCharacterInfo
import com.akfu.world.game.entity.PlayerCharacter
import akka.routing.Deafen
import akka.routing.Listen
import com.ankamagames.wakfu.common.datas.CriterionUserType
import akka.actor.Props
import scala.collection.mutable.Map
import akka.actor.ActorRef
import com.ankamagames.framework.kernel.core.maths.Direction8Path
import com.akfu.world.game.entity.AbstractCharacterInfo
import com.akfu.world.game.entity.AbstractCharacterInfo


final class WorldInstance (val worldModel: WorldInfo) extends AbstractField {
  
  private var territories = Map[Int, ActorRef]()
  
  {
    val iterator = worldModel getTerritoriesIterator()
    while(iterator.hasNext()) {
      iterator.advance()
      val territory = iterator.value()
      territories +=  (territory.getId -> context.actorOf(Props(new TerritoryInstance(territory)), "territory_" + territory.getId))
    }
  }  
  
  protected def onCheckEntrance(entity: AbstractCharacterInfo) = entity.getInstanceId == worldModel.m_worldId
  
  protected override def onEntityEnter(entity: AbstractCharacterInfo): Boolean = {
    
    
    
    super.onEntityEnter(entity)
  }
  
  protected override def onEntityMove(id: Long, path: Direction8Path): Boolean = {
    val entity = getEntity(id)
    if(entity == null) return false
    
    
    
    return true
  }
}