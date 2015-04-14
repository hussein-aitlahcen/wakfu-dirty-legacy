package com.akfu.common.network.protocol.message

import akka.util.ByteString
import io.netty.buffer.ByteBuf

abstract class WakfuServerMessage() extends WakfuMessage() {
  def getOpCode() : Int
  def internalSerialize() : Array[Byte]
  def serialize(out: ByteBuf) {
    val data = internalSerialize
    out writeShort data.length + 4
    out writeShort getOpCode
    out writeBytes data
  }
}