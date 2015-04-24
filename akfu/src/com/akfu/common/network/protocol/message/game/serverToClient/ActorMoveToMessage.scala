package com.akfu.common.network.protocol.message.game.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf

final class ActorMoveToMessage(id: Long, x: Int, y: Int, z: Short, direction: Byte) extends WakfuServerMessage {
  def getOpCode() = OpCode SMSG_ACTOR_MOVE_TO
  
  override def internalSerialize(out: ByteBuf) {
    out writeLong id
    out writeInt x
    out writeInt y
    out writeShort z
    out writeByte direction
  }
}