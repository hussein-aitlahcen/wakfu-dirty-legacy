package com.akfu.auth.network.protocol.message.clientToServer

import com.akfu.auth.network.protocol.message.OpCode
import io.netty.buffer.ByteBuf
import com.akfu.common.network.protocol.message.WakfuClientMessage

final class ClientDispatchAuthenticationMessage(
    var encryptedLoginAndPassword: Array[Byte] = null)
    extends WakfuClientMessage {

  def getOpCode() = OpCode.CMSG_DISPATCH_AUTH
  
  def deserialize(in: ByteBuf) {
    encryptedLoginAndPassword = Array.ofDim[Byte](in readInt)
    in.readBytes(encryptedLoginAndPassword)
  }
}