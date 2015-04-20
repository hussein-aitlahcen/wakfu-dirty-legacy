package com.akfu.common.network.protocol.message.clientToServer

import com.akfu.common.network.protocol.message.WakfuClientMessage
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf

final class ClientAuthenticationTokenMessage extends WakfuClientMessage {
  def getOpCode() = OpCode.CMSG_AUTH_TOKEN
  
  var token = ""
  
  override def deserialize(in: ByteBuf) {
    val data = Array.ofDim[Byte](in readInt)
    in readBytes data
    token = new String(data)
  }
}