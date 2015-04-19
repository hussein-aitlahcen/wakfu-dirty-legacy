package com.akfu.common.network.protocol.message.world.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf

final class ClientAdditionalCharacterSlotsUpdateMessage(additionalSlots: Byte) extends WakfuServerMessage  {
  def getOpCode() = OpCode.SMSG_CLIENT_ADD_SLOT_UPDATE
  
  override def internalSerialize(out: ByteBuf) {
    out writeByte additionalSlots
  }
}