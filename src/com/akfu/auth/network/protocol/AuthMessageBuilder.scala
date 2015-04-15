package com.akfu.auth.network.protocol

import com.akfu.common.network.protocol.MessageBuilder
import com.akfu.common.network.protocol.message.WakfuClientMessage
import io.netty.buffer.ByteBuf
import com.akfu.auth.network.protocol.message.OpCode
import com.akfu.auth.network.protocol.message.clientToServer.ClientVersionMessage
import com.akfu.common.network.protocol.message.WakfuClientMessage
import com.akfu.auth.network.protocol.message.clientToServer.ClientDispatchAuthenticationMessage
import com.akfu.auth.network.protocol.message.clientToServer.ClientPublicKeyRequestMessage
import com.akfu.auth.network.protocol.message.clientToServer.ClientProxiesRequestMessage
import com.akfu.auth.network.protocol.message.clientToServer.WakfuAuthenticationTokenRequestMessage

object AuthMessageBuilder extends MessageBuilder[WakfuClientMessage] {
  
  def build(typeId: Short, in: ByteBuf) : WakfuClientMessage = {
    
    var message: WakfuClientMessage = null
    
    typeId match {
      case OpCode.CMSG_CLIENT_VERSION =>            message = new ClientVersionMessage        
      case OpCode.CMSG_DISPATCH_AUTH =>             message = new ClientDispatchAuthenticationMessage        
      case OpCode.CMSG_CLIENT_PUBLIC_KEY_REQUEST => message = new ClientPublicKeyRequestMessage      
      case OpCode.CMSG_CLIENT_PROXIES_REQUEST =>    message = new ClientProxiesRequestMessage     
      case OpCode.CMSG_AUTH_TOKEN_REQUEST =>        message = new WakfuAuthenticationTokenRequestMessage
      case _ => message = null
    }    
    
    if(message != null)
      message deserialize in
    message    
  }
  
}