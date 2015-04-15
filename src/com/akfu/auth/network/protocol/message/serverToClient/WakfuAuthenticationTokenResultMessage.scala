package com.akfu.auth.network.protocol.message.serverToClient

import com.akfu.auth.network.protocol.message.OpCode
import io.netty.buffer.ByteBuf
import com.akfu.common.network.protocol.message.WakfuServerMessage
import java.nio.charset.StandardCharsets

object WakfuAuthenticationTokenResultEnum {
  final val SUCCESS = 0
  final val ERROR = 1
}

final class WakfuAuthenticationTokenResultMessage(token: String, result: Int) extends WakfuServerMessage {
  def getOpCode() = OpCode.SMSG_AUTH_TOKEN_RESULT
  
  override def internalSerialize(out: ByteBuf) {
    out writeByte result
    out writeInt token.length
    out writeBytes token.getBytes(StandardCharsets.UTF_8)
  }
}