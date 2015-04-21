package com.akfu.common.network.protocol.message.nation.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf
import com.ankamagames.wakfu.common.game.nation.NationManager
import com.ankamagames.wakfu.common.game.nation.data.NationSerializationType

final class ClientNationSynchronizationMessage extends WakfuServerMessage {
  def getOpCode() = OpCode SMSG_CLIENT_NATION_SYNC
  
  override def internalSerialize(out: ByteBuf) {
    val iterator = NationManager.INSTANCE.nationsIterator()
    while(iterator.hasNext()) {
      iterator.advance()
      val nation = iterator.value()
      val data = nation.serializeForGameToClient()
      out writeInt nation.getNationId
      out writeInt data.length
      out writeBytes data
    }
  }
}