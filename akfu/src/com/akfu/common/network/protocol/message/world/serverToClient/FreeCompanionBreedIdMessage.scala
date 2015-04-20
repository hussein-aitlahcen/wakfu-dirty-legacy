package com.akfu.common.network.protocol.message.world.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf

final class FreeCompanionBreedIdMessage extends WakfuServerMessage {
  def getOpCode() = OpCode.SMSG_FREE_COMPANION_BREED_ID
  
  override def internalSerialize(out: ByteBuf) {
    out writeShort 0 // companion id
  }
}