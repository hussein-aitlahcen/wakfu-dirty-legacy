package com.akfu.common.network.protocol.message.world.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf
import com.akfu.common.configuration.SystemConfiguration

final class ClientSystemConfigurationMessage(config: SystemConfiguration) extends WakfuServerMessage {
  def getOpCode() = OpCode.SMSG_CLIENT_SYSTEM_CONF
  
  override def internalSerialize(out: ByteBuf) {
    out writeInt config.serializeForClient.length
    out writeBytes config.serializeForClient
  }
}