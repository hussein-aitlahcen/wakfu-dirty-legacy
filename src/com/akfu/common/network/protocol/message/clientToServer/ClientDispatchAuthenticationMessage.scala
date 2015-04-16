package com.akfu.common.network.protocol.message.clientToServer

import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf
import com.akfu.common.network.protocol.message.WakfuClientMessage

final class ClientDispatchAuthenticationMessage
    extends WakfuClientMessage {

  def getOpCode() = OpCode.CMSG_DISPATCH_AUTH
  
  
  var encryptedLoginAndPassword: Array[Byte] = null
    
  override def deserialize(in: ByteBuf) {
    encryptedLoginAndPassword = Array.ofDim[Byte](in readInt)
    in.readBytes(encryptedLoginAndPassword)
  }
}