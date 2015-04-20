package com.akfu.common.network.protocol.message.auth.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf

final class AuthentificationTokenResultMessage(gameToken: String) extends WakfuServerMessage {
  
  def getOpCode() = OpCode.SMSG_GAME_AUTH_TOKEN_RESULT
  
  override def internalSerialize(out: ByteBuf) {
    out writeIntString gameToken
  }
}