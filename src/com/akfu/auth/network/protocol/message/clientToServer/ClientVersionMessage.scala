package com.akfu.auth.network.protocol.message.clientToServer

import com.akfu.common.network.protocol.message.WakfuClientMessage
import com.akfu.auth.network.protocol.message.OpCode
import io.netty.buffer.ByteBuf

final class ClientVersionMessage(
  var major: Byte = 0,
  var minor: Short = 0,
  var revision: Byte = 0)
  extends WakfuClientMessage {
  
  def getOpCode() = OpCode.CMSG_CLIENT_VERSION
  def deserialize(in: ByteBuf) {
    major = in readByte()
    minor = in readShort()
    revision = in readByte()
    in readBytes(in readByte())
  }
}