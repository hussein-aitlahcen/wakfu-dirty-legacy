package com.akfu.world

import akka.actor.ActorRef
import com.akfu.common.network.WakfuClient
import com.akfu.world.network.protocol.frame.AuthenticationFrame
import com.akfu.world.network.protocol.frame.DisconnectionFrame
import com.akfu.common.network.protocol.MessageBuilder

final class WorldClient(connection: ActorRef) extends WakfuClient[WorldClient](
    connection, 
    List(AuthenticationFrame, DisconnectionFrame)) {
      

  setWorker(WorldService getWorker)
  getWorker ! WorldConnected(this)
}