package com.akfu.common.network.protocol.message.clientToServer

import com.akfu.common.network.protocol.message.WakfuClientMessage
import com.akfu.common.network.protocol.OpCode

final class ClientProxiesRequestMessage extends WakfuClientMessage {
  
  def getOpCode() = OpCode.CMSG_CLIENT_PROXIES_REQUEST
  
}