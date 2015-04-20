package com.akfu.common.network.protocol.message.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.common.network.protocol.OpCode
import java.nio.ByteBuffer
import io.netty.buffer.ByteBuf
import com.akfu.common.Community

object AuthenticationResultEnum {
  final val SUCCESS = 0
  final val WRONG_CREDENTIALS = 2
  final val ALREADY_CONNECTED = 3
  final val BANNED = 5
  final val LOCKED = 9
  final val LOGIN_SERVER_DOWN = 10
  final val TOO_MANY_CONNECTION = 11
  final val INVALID_PARTNER = 12
  final val ACCOUNT_MODERATION = 21
  final val INVALID_LOGIN = 40
  final val INVALID_TOKEN = 42
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