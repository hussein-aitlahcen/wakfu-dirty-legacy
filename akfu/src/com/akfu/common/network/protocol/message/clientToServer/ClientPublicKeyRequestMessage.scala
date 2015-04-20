package com.akfu.common.network.protocol.message.clientToServer

import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf
import com.akfu.common.network.protocol.message.WakfuClientMessage

final class ClientPublicKeyRequestMessage extends WakfuClientMessage {
  
  def getOpCode() = OpCode.CMSG_CLIENT_PUBLIC_KEY_REQUEST
  
}