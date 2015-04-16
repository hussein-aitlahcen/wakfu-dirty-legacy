package com.akfu.common.network.protocol.message.world.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.common.network.protocol.OpCode

final class ClientCalendarSynchronizationMessage extends WakfuServerMessage {
  def getOpCode() = OpCode.SMSG_CALENDAR_SYNC
}