package com.akfu.common.network.protocol.message.world.serverToClient

import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf
import com.akfu.common.network.protocol.message.WakfuServerMessage

object CharacterCreationResultEnum {
  final val SUCCESS = 0
  final val UNKNOW_ERROR = 1
  final val NAME_EXISTING = 10
  final val NAME_INVALID = 11
  final val TOO_MANY_CHARACTERS = 12
}

final class CharacterCreationResultMessage(result: Byte) extends WakfuServerMessage {
  def getOpCode() = OpCode SMSG_CHARACTER_CREATION_RESULT
  
  override def internalSerialize(out: ByteBuf) {
    out writeByte result
  }
}