package com.akfu.common.network

import scala.collection.mutable.ListBuffer
import akka.actor._
import com.akfu.common.network.protocol.message.WakfuClientMessage
import com.akfu.common.network.protocol.message.FrameMessage

sealed class FrameManagerEvent()
final case class AddFrame[TClient <: AnyRef, TMessage <: FrameMessage](val frame: FrameBase[TClient, TMessage]) extends FrameManagerEvent
final case class RemoveFrame[TClient <: AnyRef, TMessage <: FrameMessage](val frame: FrameBase[TClient, TMessage]) extends FrameManagerEvent
final case class ProcessMessage[TMessage <: FrameMessage](val message: TMessage) extends FrameManagerEvent

final class FrameManager[TClient <: AnyRef, TMessage <: FrameMessage](
    client: TClient,
    startFrames: List[FrameBase[TClient, TMessage]]) 
    extends Actor with ActorLogging {
    
  private val m_frames = ListBuffer[FrameBase[TClient, TMessage]]()
  
  for(frame <- startFrames)
    m_frames += frame
    
  def receive = { 
         
    case add:AddFrame[TClient, TMessage] => 
      m_frames += add.frame
      
    case remove:RemoveFrame[TClient, TMessage] => 
      m_frames -= remove.frame
      
    case process:ProcessMessage[TMessage] =>
      var i = 0
      val size = m_frames.size
      while(i < size && !m_frames(i).processMessage(client, process.message))        
        i += 1         
  }
}