package com.akfu.world

import akka.actor.ActorRef
import com.akfu.common.network.WakfuClient
import com.akfu.world.network.protocol.frame.AuthenticationFrame
import com.akfu.world.network.protocol.frame.DisconnectionFrame
import com.akfu.common.network.protocol.MessageBuilder
import com.akfu.world.game.entity.PlayerCharacter

final class WorldClient(connection: ActorRef) extends WakfuClient[WorldClient](
    connection, 
    List(AuthenticationFrame, DisconnectionFrame)) {
      
  private var currentCharacter: PlayerCharacter = null
  
  def getCharacter() = currentCharacter
  def setCharacter(character: PlayerCharacter) = currentCharacter = character
    
  setWorker(WorldService getWorker)
  getWorker ! WorldConnected(this)
}