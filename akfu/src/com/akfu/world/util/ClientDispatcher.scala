package com.akfu.world.util

import akka.actor.Actor
import com.akfu.common.concurrent.ListenMe
import com.akfu.common.concurrent.DeafenMe

trait ClientDispatcher {
  self : Actor with ActorHelper =>
    
    def addClientListener(id: Long) = if(id != -1) getClientRef(id) ! ListenMe
    def removeClientListener(id: Long) = if(id != -1) getClientRef(id) ! DeafenMe
}