package com.akfu.common.network.protocol.message.world.clientToServer

import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf
import com.akfu.common.network.protocol.message.WakfuClientMessage

final class CharacterSelectionMessage extends WakfuClientMessage {
  def getOpCode() = OpCode CMSG_CHARACTER_SELECTION
  
  var characterId: Long = 0
  
  override def deserialize(in: ByteBuf) {
    characterId = in readLong
  }
}