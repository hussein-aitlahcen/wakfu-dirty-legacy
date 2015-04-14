package com.akfu.common.network

import akka.actor._
import io.netty.buffer.ByteBufAllocator
import akka.util.ByteString
import com.akfu.common.network.protocol.message.WakfuServerMessage
import akka.io.Tcp._
import com.akfu.common.network.protocol.message.WakfuClientMessage
import com.akfu.common.network.protocol.message.DisconnectClient
import java.nio.ByteBuffer
import com.akfu.common.network.protocol.MessageBuilder
import akka.util.CompactByteString
  
abstract class WakfuClient[TClient <: WakfuClient[TClient]](
    connection: ActorRef, 
    builder: MessageBuilder[WakfuClientMessage], 
    startFrame: FrameBase[TClient, WakfuClientMessage]) 
    extends Actor with ActorLogging {
  
  import akka.io.Tcp._
  
  private val m_frameMgr = context.actorOf(Props(classOf[FrameManager[TClient, WakfuClientMessage]], this, startFrame))    
  private val messageBuilder = builder  
  private var size: Short = -1;
  private var typeId: Short = -1;
  
  private val in = ByteBufAllocator.DEFAULT.directBuffer()
  private val out = ByteBufAllocator.DEFAULT.heapBuffer()
  
  def receive = {
    
    case Received(data) =>  
      in.writeBytes(data.asByteBuffer)   
      read()
      
    case message: WakfuServerMessage =>
      send(message)
                  
    case message @ (ProcessMessage(_) | AddFrame(_) | RemoveFrame(_)) => 
      m_frameMgr ! message
      
    case message @ (Closed | PeerClosed | Aborted | ErrorClosed(_)) =>
      m_frameMgr ! DisconnectClient
      
    case message: Any =>      
      println(message)
  }
  
  def send(message: WakfuServerMessage) {
    message.serialize(out)
    // TODO: set cache 
    connection ! Write(ByteString.fromArray(out.array(), 0, out.writerIndex))
    out.resetWriterIndex()
  }
  
  def read() {
    
    if(size == -1 && in.readableBytes() < 2) {
      return
    }
    
    in markReaderIndex()    
    size = in readShort()
    
    if(typeId == -1 && in.readableBytes < 2) {
      in resetReaderIndex()
      return
    }
    
    if(in.readableBytes < size - 5) {
      in resetReaderIndex()
      return
    }
    
    val architectureTarget = in readByte() // wut ?
    
    typeId = in readShort()
        
    println("msgId=" + typeId + " size=" + size)
    
    in.markReaderIndex()
    
    val message = builder build(typeId, in)
    if(message != null)
      m_frameMgr ! ProcessMessage(message)
      
    in.resetReaderIndex()
    in.readerIndex(in.readerIndex() + size - 5)
    
    size = -1
    typeId = -1
    
    if(in.readableBytes > 0)
      read
    else
      if(size == -1 && typeId == -1 && in.readableBytes() < 2)
        in clear        
  }
}