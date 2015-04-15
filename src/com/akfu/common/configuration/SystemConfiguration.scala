package com.akfu.common.configuration

import java.util.regex.Pattern
import scala.collection.mutable.Map
import io.netty.buffer.ByteBufAllocator
import java.nio.charset.StandardCharsets

object SystemConfiguration {
   val DISPATH_SERIALIZATION = 
    List(SystemConfigurationType.SERVER_ID, 
        SystemConfigurationType.COMMUNITY_CHECK_ENABLE,
        SystemConfigurationType.COMMUNITY_REQUIRED,
        SystemConfigurationType.COMMUNITY_FORBIDDEN, 
        SystemConfigurationType.AUTHORIZED_PARTNERS)        
   val SPLIT_PATTERN = Pattern.compile(";")
}
final class SystemConfiguration {        
  private var properties = Map[SystemConfigurationType, String]()
  private var dispatchData: Array[Byte] = null
  
  private def serializeForDispatch() {
    val buffer = ByteBufAllocator.DEFAULT.heapBuffer()
    buffer writeInt SystemConfiguration.DISPATH_SERIALIZATION.size
    for(key <- SystemConfiguration.DISPATH_SERIALIZATION) {
      val value = properties.getOrElse(key, key.default)
      buffer writeShort key.id
      val data = value.getBytes(StandardCharsets.UTF_8)
      buffer writeInt data.length
      buffer writeBytes data
    }
    dispatchData = Array.ofDim[Byte](buffer.writerIndex)
    buffer readBytes dispatchData  
  }
  
  def getDispatchData() = 
    dispatchData
  
  def changeProperty(key: SystemConfigurationType, value: String) = 
    properties(key) = value
    serializeForDispatch
    
  def containsKey(key: SystemConfigurationType) = 
    properties contains key
    
  def getString(key: SystemConfigurationType) =
    if (properties.contains(key)) properties(key) else key.default
    
  def getInt(key: SystemConfigurationType) = 
    getString(key).toInt
    
  def getBool(key: SystemConfigurationType) = 
    getString(key).toBoolean
    
  def getLong(key: SystemConfigurationType) = 
    getString(key).toLong
}