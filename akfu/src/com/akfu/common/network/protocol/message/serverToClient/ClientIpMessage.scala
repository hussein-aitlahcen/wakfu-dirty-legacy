package com.akfu.common.network.protocol.message.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf
import com.akfu.common.util.StringsUtil

final class ClientIpMessage(ip: String) extends WakfuServerMessage {
  def getOpCode() = OpCode.SMSG_CLIENT_IP
  
  override def internalSerialize(out: ByteBuf)  {
    out writeBytes StringsUtil.asBytes(ip)
  }
}