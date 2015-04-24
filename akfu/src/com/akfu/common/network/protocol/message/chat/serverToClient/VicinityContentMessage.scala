package com.akfu.common.network.protocol.message.chat.serverToClient

import com.akfu.common.network.protocol.OpCode

final class VicinityContentMessage(entityName: String, entityId: Long, content: String) extends ChatContentMessage(entityName, entityId, content) {
  def getOpCode() = OpCode SMSG_VICINITY_MESSAGE
}