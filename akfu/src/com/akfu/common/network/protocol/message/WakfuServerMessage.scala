package com.akfu.common.network.protocol.message

import akka.util.ByteString
import io.netty.buffer.ByteBuf

abstract class WakfuServerMessage() extends WakfuMessage() {
  
  def getOpCode() : Int
  
  def internalSerialize(out: ByteBuf) {    
  }
  
  final def serialize(out: ByteBuf) {
    out markWriterIndex() // get current offset
    out writeShort 0 // msg size placeholder
    out writeShort getOpCode // msg opcode
    internalSerialize(out) // msg data
    val current = out writerIndex() // msg size = end - begin
    out resetWriterIndex
    val size = current - out.writerIndex
    out writeShort size
    out writerIndex current
  }
  
}