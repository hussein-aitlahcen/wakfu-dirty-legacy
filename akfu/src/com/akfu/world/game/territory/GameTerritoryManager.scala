package com.akfu.world.game.territory

import scala.collection.mutable.HashMap
import com.ankamagames.wakfu.client.core.game.protector.Territory
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props

object GameTerritoryManager {
  private var territoryNextId = 0
  private var territories = HashMap[Int, HashMap[Int, ActorRef]]()
  
  def createInstance(system: ActorSystem, territory: Territory) {
    territoryNextId += 1
    val actor = system.actorOf(Props(new TerritoryInstance(territoryNextId, territory)), "territory-" + territoryNextId)
    if(!territories.contains(territory.getInstanceId))
      territories += (territory.getInstanceId -> HashMap())
    territories(territory.getInstanceId) += (territoryNextId -> actor)
  }
  
  def getByWorld(worldId: Int) = territories(worldId)(0)    
  def getByUniqueId(worldId: Int, uniqueId: Int = 0) = territories(worldId)(uniqueId)
}