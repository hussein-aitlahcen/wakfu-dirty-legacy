package com.akfu.common.network.protocol.message.chat.clientToServer

import io.netty.buffer.ByteBuf
import com.akfu.common.network.protocol.message.WakfuClientMessage

abstract class UserContentMessage extends WakfuClientMessage {
  var content = "?"
  override def deserialize(in: ByteBuf) {
    content = in readByteString
  }
}