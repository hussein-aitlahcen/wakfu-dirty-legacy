package com.akfu.common.network.protocol.message

import io.netty.buffer.ByteBuf

class ExtendedByteBuf(val buffer: ByteBuf) {
  
  def readShortString(): String = {
    val data = Array.ofDim[Byte](buffer readShort)
    buffer readBytes data
    new String(data)
  }
  
  def writeShortString(value: String) {
    val data = value getBytes()
    buffer writeShort data.length
    buffer writeBytes data
  }
  
  def readIntString(): String = {
    val data = Array.ofDim[Byte](buffer readInt)
    buffer readBytes data
    new String(data)
  }
  
  def writeIntString(value: String) {
    val data = value getBytes()
    buffer writeInt data.length
    buffer writeBytes data
  }
}

abstract case class WakfuMessage() extends FrameMessage {
  
  implicit def extendByteBuf(buffer: ByteBuf) = new ExtendedByteBuf(buffer)
  
  def getOpCode() : Int
}