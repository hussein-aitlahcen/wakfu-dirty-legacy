package com.akfu.common.network.protocol.message.world.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf
import com.ankamagames.baseImpl.common.clientAndServer.game.time.calendar.GameCalendar
import com.akfu.world.game.time.WakfuCalendar

final class ClientCalendarSynchronizationMessage extends WakfuServerMessage {
  def getOpCode() = OpCode.SMSG_CALENDAR_SYNC
  
  override def internalSerialize(out: ByteBuf) {
    out writeLong WakfuCalendar.getSynchronizationTime
  }
}