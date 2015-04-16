package com.akfu.common.network.protocol.message.world.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf

final class CharactersListMessage extends WakfuServerMessage {
  def getOpCode() = OpCode.SMSG_CHARACTERS_LIST
  
  override def internalSerialize(out: ByteBuf) {
    out writeByte 0 // char count
    // char serialized parts
  }
}