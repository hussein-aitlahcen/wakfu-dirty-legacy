package com.akfu.common.network.protocol.message.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf

final class ClientAuthenticationResultsMessage(
    result: Byte = AuthenticationResultEnum.SUCCESS,
    banDuration: Int = 0)
    extends WakfuServerMessage {
  
  def getOpCode() = OpCode.SMSG_AUTH_RESULTS   
  
  override def internalSerialize(out: ByteBuf) {
    out writeByte result
    result match {
      case AuthenticationResultEnum.BANNED => 
        out writeInt banDuration
      
      case AuthenticationResultEnum.SUCCESS =>
        // accountLocalInformation serialize out
      
      case _ =>
        None
    }
  }
}