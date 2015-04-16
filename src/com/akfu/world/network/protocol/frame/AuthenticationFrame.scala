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

object AuthenticationFrame extends FrameBase[WorldClient, WakfuClientMessage] {  

  @FrameHandler(opCode = OpCode.CMSG_CLIENT_VERSION)
  def handleClientVersion(client: WorldClient, message: ClientVersionMessage) {
   log.info("client version = " + message.major + "." + message.minor + "." + message.revision)
  }  
  
  @FrameHandler(opCode = OpCode.CMSG_CLIENT_PUBLIC_KEY_REQUEST)
  def handlePublicKeyRequest(client: WorldClient, message: ClientPublicKeyRequestMessage) {
    client.self ! new ClientPublicKeyMessage(0, ConnectionEncryptionManager.getPublicKey)
  }
  
  @FrameHandler(opCode = OpCode.CMSG_AUTH_TOKEN)
  def handleClientAuthenticationToken(client: WorldClient, message: ClientAuthenticationTokenMessage) {
    client.getWorker ! Authenticate(client, message token)
  }
}