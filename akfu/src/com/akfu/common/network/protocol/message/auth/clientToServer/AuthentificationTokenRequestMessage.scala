package com.akfu.common.network.protocol.message.auth.clientToServer

import com.akfu.common.network.protocol.message.WakfuClientMessage
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf

final class AuthentificationTokenRequestMessage extends WakfuClientMessage {
  
  def getOpCode() = OpCode.CMSG_GAME_AUTH_TOKEN_REQUEST
  
  var address: Long = -1
  var lang: String = "en"
  
  override def deserialize(in: ByteBuf) {    
    address = in readLong()
    lang = in readShortString()
  }
}