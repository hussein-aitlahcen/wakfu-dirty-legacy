package com.akfu.common.network.protocol

import io.netty.buffer.ByteBuf
import com.akfu.common.network.protocol.message.WakfuClientMessage
import com.akfu.common.network.protocol.message.clientToServer._
import org.clapper.classutil.ClassFinder
import scala.collection.mutable.Map

object MessageBuilder {
  
  private val messageByOpCode = Map[Int, () => WakfuClientMessage]()
  private val finder = ClassFinder()  
  private val classes = finder.getClasses.iterator
  private val classMap = ClassFinder.classInfoMap(classes)
  private val messages = ClassFinder.concreteSubclasses("com.akfu.common.network.protocol.message.WakfuClientMessage", classMap)
  private val method = classOf[WakfuClientMessage].getMethod("getOpCode")
  for(message <- messages) {
    val clazz = Class.forName(message.name)
    val instance = clazz.newInstance.asInstanceOf[WakfuClientMessage]
    messageByOpCode += (method.invoke(instance).asInstanceOf[Int] -> (() => clazz.newInstance().asInstanceOf[WakfuClientMessage]))
  }
  
  
  def build(typeId: Short, in: ByteBuf) : WakfuClientMessage = {    
    var constructor: () => WakfuClientMessage = messageByOpCode.getOrElse(typeId, null)
    if(constructor == null)
      return null
    val message = constructor()
    message deserialize in
    message    
  }
}