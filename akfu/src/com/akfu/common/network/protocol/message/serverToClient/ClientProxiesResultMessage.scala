package com.akfu.common.network.protocol.message.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf
import com.akfu.common.configuration.SystemConfiguration
import java.nio.charset.StandardCharsets
import io.netty.buffer.ByteBufAllocator
import com.akfu.common.configuration.SystemConfigurationType
import com.akfu.common.Community
import com.akfu.world.WorldService
import org.codehaus.jettison.json.JSONObject
import org.codehaus.jettison.json.JSONArray
import java.util.ArrayList

final class WorldInfo(id: Int, version: String, playerCount: Int, playerLimit: Int, locked: Boolean, config: SystemConfiguration) {
  final def serialize(out: ByteBuf) {
    out writeInt id
    
    out writeInt 7
    writeVersion(out, version)
    out writeInt config.serializeForDispatch.length
    out writeBytes config.serializeForDispatch
    out writeInt playerCount
    out writeInt playerLimit
    out writeBoolean locked
  }
  
  def writeVersion(out: ByteBuf, version: String) {
    out writeByte 1
    out writeShort 42
    out writeByte 1
    out writeByte "-1".length
    out writeBytes "-1".getBytes
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

object ClientProxiesResultMessage {
  def main(args: Array[String]) {
    val out = ByteBufAllocator.DEFAULT.heapBuffer()
    
    val config = new SystemConfiguration()
    config changeProperty(SystemConfigurationType.SERVER_ID, "2")
    config changeProperty(SystemConfigurationType.COMMUNITY_CHECK_ENABLE, "true")
    config changeProperty(SystemConfigurationType.COMMUNITY_REQUIRED, Community.FR.code.toString)
    config changeProperty(SystemConfigurationType.COMMUNITY_FORBIDDEN, "")
    config changeProperty(SystemConfigurationType.AUTHORIZED_PARTNERS, "default;steam")    
    
    val proxies = List(
        new ProxyInfo(2, "Test", Community.FR.code, "127.0.0.1", List(443), 0))
        
    val worlds = List(
        new WorldInfo(2, "", 100, 100, false, config))
        
    val message = new ClientProxiesResultMessage(proxies, worlds)
    message serialize out
    val json = new JSONObject()
    json put("opcode", OpCode SMSG_CLIENT_PROXIES_RESULT)
    json put("type", 0)
    val array = new ArrayList[String]()
    var i = 0
    for(i <- 0 to out.writerIndex) {
      array add String.format("%02X", java.lang.Byte.valueOf(out.getByte(i)))
    }
    json put("raw", array)
    println("result")
    println(json)
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