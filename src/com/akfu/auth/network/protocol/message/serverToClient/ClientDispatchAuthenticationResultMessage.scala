package com.akfu.auth.network.protocol.message.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.auth.network.protocol.message.OpCode
import java.nio.ByteBuffer
import io.netty.buffer.ByteBuf
import com.akfu.common.Community

object AuthenticationResultEnum {
  final val SUCCESS: Byte = 0
  final val WRONG_CREDENTIALS: Byte = 2
  final val ALREADY_CONNECTED: Byte = 3
  final val LOCKED: Byte = 9
  final val LOGIN_SERVER_DOWN: Byte = 10
  final val TOO_MANY_CONNECTION: Byte = 11
  final val INVALID_PARTNER: Byte = 12
}

final class AccountInformation(community: Int /*, adminInfos: xx */) {  
  def serialize(out: ByteBuf) {
    out writeInt community
    out writeBoolean false // hasAdminInfos 
    // TODO: admin informations
  }
}

final class ClientDispatchAuthenticationResultMessage(result: Byte, infos: AccountInformation = null) extends WakfuServerMessage {
  
  def getOpCode() = OpCode.SMSG_DISPATCH_AUTH_RESULT
  
  override def internalSerialize(out: ByteBuf) = {    
    out writeByte result
    out writeByte 0 // steamhint
    out writeBoolean infos != null
    if(infos != null) {
      infos serialize out
    }
  }  
  
}