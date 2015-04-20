package com.akfu.common.network.protocol.message.world.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf

final class CompanionListMessage extends WakfuServerMessage {
  def getOpCode() = OpCode SMSG_COMPANION_LIST
  
  override def internalSerialize(out: ByteBuf) {
    out writeByte 0 // count
    // TODO: serialize companions
  }
}