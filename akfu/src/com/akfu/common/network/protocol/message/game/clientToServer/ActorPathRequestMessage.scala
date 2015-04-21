package com.akfu.common.network.protocol.message.game.clientToServer

import com.akfu.common.network.protocol.message.WakfuClientMessage
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf
import com.ankamagames.framework.kernel.core.maths.Direction8Path
import com.ankamagames.framework.kernel.core.common.collections.ByteArray
import java.nio.ByteBuffer

final class ActorPathRequestMessage extends WakfuClientMessage {
  def getOpCode() = OpCode CMSG_ACTOR_PATH_REQUEST
  
  var path: Direction8Path = null
  
  override def deserialize(in: ByteBuf) {
    val pathData = Array.ofDim[Byte](size)
    in readBytes pathData
    path = Direction8Path.decodeFromBuffer(ByteBuffer.wrap(pathData))
  }
}