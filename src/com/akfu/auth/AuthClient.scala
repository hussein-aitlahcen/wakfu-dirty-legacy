package com.akfu.auth

import com.akfu.common.network.WakfuClient
import akka.actor.ActorRef
import com.akfu.auth.network.protocol.AuthMessageBuilder
import com.akfu.common.network.AddFrame
import com.akfu.auth.network.protocol.frame.AuthenticationFrame

final class AuthClient(connection: ActorRef) extends WakfuClient[AuthClient](
    connection, 
    AuthMessageBuilder,
    AuthenticationFrame) {
}