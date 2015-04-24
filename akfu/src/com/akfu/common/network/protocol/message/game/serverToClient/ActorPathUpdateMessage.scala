package com.akfu.common.network.protocol.message.game.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf
import com.ankamagames.framework.kernel.core.maths.Direction8Path

final class ActorPathUpdateMessage(entityId: Long, path: Direction8Path) extends WakfuServerMessage {
  def getOpCode() = OpCode SMSG_ACTOR_PATH_UPDATE
  
  override def internalSerialize(out: ByteBuf) {
    out writeLong entityId
    out writeBytes path.encode()
  }
}