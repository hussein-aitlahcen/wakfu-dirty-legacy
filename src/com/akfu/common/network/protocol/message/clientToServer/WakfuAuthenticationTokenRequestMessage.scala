package com.akfu.common.network.protocol.message.clientToServer

import com.akfu.common.network.protocol.message.WakfuClientMessage
import io.netty.buffer.ByteBuf
import com.akfu.common.network.protocol.OpCode

class WakfuAuthenticationTokenRequestMessage extends WakfuClientMessage {  
    
  def getOpCode() = OpCode.CMSG_AUTH_TOKEN_REQUEST

  var serverId: Int = -1
  var accountId: Long = -1
    
  override def deserialize(out: ByteBuf) {
    serverId = out readInt()
    accountId = out readLong()
  }
}