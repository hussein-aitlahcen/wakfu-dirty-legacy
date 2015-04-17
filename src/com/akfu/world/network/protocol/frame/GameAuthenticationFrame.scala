package com.akfu.world.network.protocol.frame

import com.akfu.common.network.FrameBase
import com.akfu.world.WorldClient
import com.akfu.common.network.protocol.message.WakfuClientMessage
import com.akfu.common.network.FrameHandler
import com.akfu.common.network.protocol.OpCode
import com.akfu.common.network.protocol.message.clientToServer.ClientVersionMessage
import com.akfu.common.network.protocol.message.clientToServer.ClientPublicKeyRequestMessage
import com.akfu.common.network.protocol.message.serverToClient.ClientPublicKeyMessage
import com.akfu.common.network.security.ConnectionEncryptionManager
import com.akfu.common.network.protocol.message.clientToServer.ClientAuthenticationTokenMessage
import com.akfu.world.Authenticate
import com.akfu.common.network.protocol.message.auth.clientToServer.AuthentificationTokenRequestMessage
import com.akfu.world.GameTokenRequest

object GameAuthenticationFrame extends FrameBase[WorldClient, WakfuClientMessage] {  
  @FrameHandler(opCode = OpCode.CMSG_GAME_AUTH_TOKEN_REQUEST)
  def handleClientGameTokenRequest(client: WorldClient, message: AuthentificationTokenRequestMessage) {
    client.getWorker ! GameTokenRequest(client)
  }
}