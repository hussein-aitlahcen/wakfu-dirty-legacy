package com.akfu.common.network.protocol.message.chat.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import io.netty.buffer.ByteBuf

abstract class ChatContentMessage(entityName: String, entityId: Long, content: String) extends WakfuServerMessage {  
  override def internalSerialize(out: ByteBuf) {
    out writeByteString entityName
    out writeLong entityId
    out writeByteString content
  }
}