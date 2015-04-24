package com.akfu.world.util

import akka.actor.Actor
import akka.util.Timeout
import java.util.concurrent.TimeUnit
import scala.concurrent.duration._
import akka.actor.ActorRef
import akka.actor.Status.Success
import com.akfu.common.concurrent.ExecuteInContext
import akka.actor.ActorSelection
import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.common.network.MessageList

trait ActorHelper {
  self: Actor =>
    
    def applyInActorContext(selection: ActorSelection, fun: (ActorRef) => Unit) = 
      selection ! ExecuteInContext(fun)
    
    def applyInClientContext(id: Long, fun: (ActorRef) => Unit) = 
      applyInActorContext(getClientRef(id), fun)
    
    def getClientRef(id: Long) = 
      context.system actorSelection("/user/listener/client_" + id) 
    
    def dispatchToClient(id: Long, message: Any) =
      if(id != -1)
        getClientRef(id) ! message
    
    def dispatchToClients(ids: List[Long], message: Any) = 
      ids foreach(dispatchToClient(_, message))
    
    def dispatchMultiToClient(id: Long, messages: List[WakfuServerMessage]) = 
      dispatchToClient(id, MessageList(messages))
      
    def dispatchMultiToClients(ids: List[Long], messages: List[WakfuServerMessage]) = 
      dispatchToClients(ids, MessageList(messages))
}