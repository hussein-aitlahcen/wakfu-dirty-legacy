package com.akfu.auth.network.protocol.frame

import com.akfu.common.network.FrameBase
import com.akfu.auth.AuthClient
import com.akfu.common.network.protocol.message.WakfuClientMessage
import com.akfu.common.network.FrameHandler
import com.akfu.common.network.ClientDisconnected
import com.akfu.auth.AuthService
import com.akfu.auth.AuthDisconnected

object DisconnectionFrame extends FrameBase[AuthClient, WakfuClientMessage] {    
  @FrameHandler(opCode = ClientDisconnected.OP_CODE)
  def handleClientVersion(client: AuthClient, message: ClientDisconnected) {    
    client.getWorker ! AuthDisconnected(client)
  }    
}