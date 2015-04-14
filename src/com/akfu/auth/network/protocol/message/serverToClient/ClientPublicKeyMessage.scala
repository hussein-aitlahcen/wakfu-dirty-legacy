package com.akfu.auth.network.protocol.message.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import akka.util._
import akka.util.ByteString.ByteString1
import io.netty.buffer.ByteBuf
import java.nio.ByteBuffer
import com.akfu.auth.network.protocol.message.OpCode

final class ClientPublicKeyMessage(
    val salt: Long, 
    val key: Array[Byte])
extends WakfuServerMessage {
  
  def getOpCode() = OpCode.SMSG_CLIENT_PUB_KEY
  
  def internalSerialize() = {
    val out = ByteBuffer allocate(8 + key.length)
    out putLong salt
    out put key   
    out.array
  }  
}