package com.akfu.common.network

import scala.annotation.StaticAnnotation
import com.akfu.common.network.protocol.message.FrameMessage
import scala.reflect.runtime.universe._
import scala.reflect.runtime.{currentMirror => cm}
import org.slf4j.Logger
import java.lang.reflect.Method
import org.slf4j.LoggerFactory

abstract class FrameBase[TClient <: AnyRef, TMessage <: FrameMessage] { 
  
  final val log = LoggerFactory.getLogger(getClass.getName)
  
  var handlers: Map[Int, Method] = Map()
  
  var methods = getClass().getDeclaredMethods()
  for (method <- methods) {
    var annotations = method.getAnnotations
    for(annotation <- annotations) {
      if(annotation.isInstanceOf[FrameHandler]) {
        handlers += (annotation.asInstanceOf[FrameHandler].opCode -> method)
      }
    }
  }

  def processMessage(client: TClient, message: TMessage) : Boolean = {    
    val handler = handlers getOrElse(message.getOpCode(), null)
    if(handler == null) 
      return false      
    handler.invoke(this, client, message)
    return true
  }
}