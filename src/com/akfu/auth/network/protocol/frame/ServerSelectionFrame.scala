package com.akfu.auth.network.protocol.frame

import com.akfu.common.network.FrameHandler
import com.akfu.auth.network.protocol.message.OpCode
import com.akfu.common.network.FrameBase
import com.akfu.auth.AuthClient
import com.akfu.common.network.protocol.message.WakfuClientMessage
import com.akfu.auth.network.protocol.message.clientToServer.WakfuAuthenticationTokenRequestMessage
import com.akfu.auth.AuthService
import com.akfu.auth._
import com.akfu.auth.network.protocol.message.clientToServer._

object ServerSelectionFrame extends FrameBase[AuthClient, WakfuClientMessage] {  
  
  @FrameHandler(opCode = OpCode.CMSG_CLIENT_PROXIES_REQUEST)  
  def handleProxiesRequest(client: AuthClient, message: ClientProxiesRequestMessage) =
    AuthService.getWorker ! ProxiesRequest(client) 
  
  @FrameHandler(opCode = OpCode.CMSG_AUTH_TOKEN_REQUEST)  
  def handleAuthTokenRequest(client: AuthClient, message: WakfuAuthenticationTokenRequestMessage) =
    AuthService.getWorker ! AuthTokenRequest(client, message.serverId)
  
}
