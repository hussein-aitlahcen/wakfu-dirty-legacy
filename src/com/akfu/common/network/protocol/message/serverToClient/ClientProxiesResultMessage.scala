package com.akfu.common.network.protocol.message.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf
import com.akfu.common.configuration.SystemConfiguration
import java.nio.charset.StandardCharsets

final class WorldInfo(id: Int, version: String, playerCount: Int, playerLimit: Int, locked: Boolean, config: SystemConfiguration) {
  final def serialize(out: ByteBuf) {
    out writeInt id
    val versionData = version.getBytes
    out writeInt versionData.length
    out writeBytes versionData
    out writeInt config.serializeForDispatch.length
    out writeBytes config.serializeForDispatch
    out writeInt playerCount
    out writeInt playerLimit
    out writeBoolean locked
  }
}

final class ProxyInfo(id: Int, name: String, community: Int, address: String, ports: List[Int], order: Byte) {
  final def serialize(out: ByteBuf) {
    val nameBytes = name getBytes StandardCharsets.UTF_8
    val addressBytes = address getBytes StandardCharsets.UTF_8    
    out writeInt id
    out writeInt nameBytes.length
    out writeBytes nameBytes
    out writeInt community
    out writeInt addressBytes.length
    out writeBytes addressBytes
    out writeInt ports.length
    for(port <- ports)
      out writeInt port
    out writeByte order
  }
}

final class ClientProxiesResultMessage(proxies: List[ProxyInfo], worlds: List[WorldInfo]) extends WakfuServerMessage {
  def getOpCode() = OpCode.SMSG_CLIENT_PROXIES_RESULT
  
  override def internalSerialize(out: ByteBuf) {
    out writeInt proxies.length 
    for(proxy <- proxies)
      proxy serialize out    
    out writeInt worlds.length // worlds
    for(world <- worlds) 
      world serialize out
  }
}