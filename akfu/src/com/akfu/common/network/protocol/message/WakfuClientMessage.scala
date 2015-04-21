package com.akfu.common.network.protocol.message

import io.netty.buffer.ByteBuf

abstract class WakfuClientMessage() extends WakfuMessage() {
     
  def getOpCode() : Int
    
  var size = -1
  
  def deserialize(in: ByteBuf) {        
  }
}