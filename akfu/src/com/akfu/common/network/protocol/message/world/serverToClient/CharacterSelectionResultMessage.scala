package com.akfu.common.network.protocol.message.world.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf

object CharacterSelectionResultEnum {
  final val SUCCESS = 0
  final val ERROR = 1
}

final class CharacterSelectionResultMessage(result: Byte) extends WakfuServerMessage {
  def getOpCode() = OpCode SMSG_CHARACTER_SELECTION_RESULT
  
  override def internalSerialize(out: ByteBuf) {
    out writeByte result
  }
}