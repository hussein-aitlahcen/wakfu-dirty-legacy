package com.akfu.common.network.protocol.message.chat.clientToServer

import com.akfu.common.network.protocol.OpCode

final class UserVicinityContentMessage extends UserContentMessage {
  def getOpCode() = OpCode CMSG_USER_VICINITY_MESSAGE
}