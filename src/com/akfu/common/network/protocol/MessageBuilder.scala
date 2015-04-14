package com.akfu.common.network.protocol

import io.netty.buffer.ByteBuf

abstract class MessageBuilder[TMessage] {
  def build(typeId: Short, in: ByteBuf) : TMessage
}