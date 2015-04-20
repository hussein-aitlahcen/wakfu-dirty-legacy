package com.akfu.common.network

import akka.actor._
import io.netty.buffer.ByteBufAllocator
import akka.util.ByteString
import com.akfu.common.network.protocol.message.WakfuServerMessage
import akka.io.Tcp._
import com.akfu.common.network.protocol.message.WakfuClientMessage
import java.nio.ByteBuffer
import com.akfu.common.network.protocol.MessageBuilder
import akka.util.CompactByteString
import akka.actor.TypedActor.PreStart
import com.akfu.common.database.Account

object ClientDisconnected {
  final val OP_CODE = -1
  final val REASON_DISCONNECTED = 0
  final val REASON_KICKED = 1
}

final class ClientDisconnected(reason: Int) extends WakfuClientMessage {
  def this() = this(ClientDisconnected.REASON_DISCONNECTED)
  def getOpCode() = -1
}
  
abstract class WakfuClient[TClient <: WakfuClient[TClient]](
    connection: ActorRef, 
    startFrames: List[FrameBase[TClient, WakfuClientMessage]]) 
    extends Actor with ActorLogging {
     
  private val m_frameMgr = context.actorOf(Props(classOf[FrameManager[TClient, WakfuClientMessage]], this, startFrames))
  private var size: Short = -1;
  private var typeId: Short = -1;
  private var disconnectReason: Int = ClientDisconnected.REASON_DISCONNECTED
  
  private val in = ByteBufAllocator.DEFAULT.directBuffer()
  private val out = ByteBufAllocator.DEFAULT.heapBuffer()
  
  private var currentWorker: ActorRef = null
  private var currentAccount: Account = null
   
  def setWorker(worker: ActorRef) = currentWorker = worker
  def getWorker = currentWorker
  
  def setAccount(account: Account) = currentAccount = account
  def getAccount = currentAccount
    
  // dead when connection closed
  context watch connection
  
  def receive = {
    
    case Received(data) =>  
      in.writeBytes(data.asByteBuffer)   
      read()
      
    case message: WakfuServerMessage =>
      send(message)
                  
    case message @ (ProcessMessage(_) | AddFrame(_) | RemoveFrame(_)) => 
      println(message)
      m_frameMgr ! message
      
    case Close =>
      connection ! Close
      
    case Terminated(watched) =>
      in release()
      out release()
      
    case message @ (Closed | PeerClosed | Aborted | ErrorClosed(_)) =>
      m_frameMgr ! ProcessMessage(new ClientDisconnected(disconnectReason))
                  
    case message: Any =>      
      log.info("WakfuClient::receive unhandled message : " + message)
  }
  
  def disconnect() = {
    log.info("kicked")
    disconnectReason = ClientDisconnected.REASON_KICKED
    self ! Close
  }
  
  def send(message: WakfuServerMessage) {
    message.serialize(out)
    // TODO: set cache 
    connection ! Write(ByteString.fromArray(out.array, 0, out.writerIndex))
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
        
    log.info("msgId=" + typeId + " size=" + size)
    
    in.markReaderIndex()
    
    val message = MessageBuilder build(typeId, in)
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