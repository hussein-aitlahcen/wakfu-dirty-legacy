package com.akfu.auth

import com.akfu.common.network.WakfuClient
import akka.actor.ActorRef
import com.akfu.common.network.AddFrame
import com.akfu.auth.network.protocol.frame.AuthenticationFrame
import com.akfu.auth.network.protocol.frame.DisconnectionFrame

final class AuthClient(connection: ActorRef) extends WakfuClient[AuthClient](
    connection, 
    List(AuthenticationFrame, DisconnectionFrame)) {
  
  setWorker(AuthService getWorker)
  getWorker ! AuthConnected(this)
}