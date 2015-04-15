package com.akfu.auth.network.protocol.message.clientToServer

import com.akfu.common.network.protocol.message.WakfuClientMessage
import io.netty.buffer.ByteBuf
import com.akfu.auth.network.protocol.message.OpCode

class WakfuAuthenticationTokenRequestMessage(
    var serverId: Int = -1,
    var accountId: Long = -1)
    extends WakfuClientMessage {  
    
  def getOpCode() = OpCode.CMSG_AUTH_TOKEN_REQUEST
  
  override def deserialize(out: ByteBuf) {
    serverId = out readInt()
    accountId = out readLong()
  }
}