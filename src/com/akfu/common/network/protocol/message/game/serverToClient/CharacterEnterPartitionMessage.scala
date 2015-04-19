package com.akfu.common.network.protocol.message.game.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf

final class CharacterEnterPartitionMessage(x: Int, y: Int) extends WakfuServerMessage {
  def getOpCode() = OpCode SMSG_CHARACTER_ENTER_PARTITION
  
  override def internalSerialize(out: ByteBuf) {
    out writeInt x
    out writeInt y
  }
}