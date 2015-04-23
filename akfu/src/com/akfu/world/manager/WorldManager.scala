package com.akfu.world.manager

import org.slf4j.LoggerFactory
import akka.actor.ActorRef
import akka.actor.ActorSystem
import com.ankamagames.wakfu.client.core.world.WorldInfoManager
import com.ankamagames.wakfu.client.core.world.WorldInfoManager.WorldInfo
import akka.actor.Props
import com.akfu.world.game.territory.WorldInstance
import scala.collection.mutable.HashMap

object WorldManager {
  private val log = LoggerFactory getLogger(WorldManager getClass) 
  private var worlds = HashMap[Short, ActorRef]()
  
  def initialize(system: ActorSystem) {
    val iterator = WorldInfoManager getInstance() getInfos()
    while(iterator hasNext()) {
      iterator advance()
      createInstance(system, iterator value)
    }
    
  }
  
  def createInstance(system: ActorSystem, worldInfo: WorldInfo) {
    worlds += (worldInfo.m_worldId -> system.actorOf(Props(new WorldInstance(worldInfo)), "world_" + worldInfo.m_worldId))
    log info s"world registered id=" + worldInfo.m_worldId
  }
  
  def getById(worldId: Short) = worlds(worldId)
}