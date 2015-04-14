package com.akfu.auth.network.protocol.frame

import java.nio.ByteBuffer
import com.akfu.auth.AuthClient
import com.akfu.auth.AuthService
import com.akfu.auth.ProcessAuthentication
import com.akfu.auth.network.protocol.message.clientToServer.ClientDispatchAuthenticationMessage
import com.akfu.auth.network.protocol.message.clientToServer.ClientVersionMessage
import com.akfu.auth.network.protocol.message.serverToClient.ClientPublicKeyMessage
import com.akfu.common.network.FrameBase
import com.akfu.common.network.FrameHandler
import com.akfu.common.network.protocol.message.WakfuClientMessage
import com.akfu.common.network.security.ConnectionEncryptionManager
import com.akfu.auth.network.protocol.message.OpCode

object AuthenticationFrame extends FrameBase[AuthClient, WakfuClientMessage] {  
  
  @FrameHandler(opCode = OpCode.CMSG_CLIENT_VERSION)
  def handleClientVersion(client: AuthClient, message: ClientVersionMessage) {
   println("client version = " + message.major + "." + message.minor + "." + message.revision)
   client.self ! new ClientPublicKeyMessage(0, ConnectionEncryptionManager.getPublicKey)
  }  
  
  @FrameHandler(opCode = OpCode.CMSG_DISPATCH_AUTH)
  def handleClientAuthentication(client: AuthClient, message: ClientDispatchAuthenticationMessage) {
    val loginData = ByteBuffer.wrap(ConnectionEncryptionManager.decrypt(message.encryptedLoginAndPassword))
    val salt = loginData getLong
    val rawAcc = Array.ofDim[Byte](loginData get)
    loginData get rawAcc
    val rawPass = Array.ofDim[Byte](loginData get)
    loginData get rawPass
         
    AuthService.getWorker ! new ProcessAuthentication(client, new String(rawAcc), new String(rawPass))
  }
}