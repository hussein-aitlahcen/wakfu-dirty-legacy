package com.akfu.auth

import com.akfu.common.network.WakfuClient
import akka.actor.ActorRef
import com.akfu.auth.network.protocol.AuthMessageBuilder
import com.akfu.common.network.AddFrame
import com.akfu.auth.network.protocol.frame.AuthenticationFrame
import com.akfu.auth.network.protocol.frame.DisconnectionFrame

final class AuthClient(connection: ActorRef) extends WakfuClient[AuthClient](
    connection, 
    AuthMessageBuilder,
    List(AuthenticationFrame, DisconnectionFrame)) {
  
  AuthService.getWorker ! AuthConnected(this)
}