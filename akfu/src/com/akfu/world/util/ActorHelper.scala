package com.akfu.world.util

import akka.actor.Actor
import akka.util.Timeout
import java.util.concurrent.TimeUnit
import scala.concurrent.duration._
import akka.actor.ActorRef
import akka.actor.Status.Success
import com.akfu.common.concurrent.ExecuteInContext
import akka.actor.ActorSelection

trait ActorHelper {
  self: Actor =>
    def applyToActor(selection: ActorSelection, fun: (ActorRef) => Unit) = selection ! ExecuteInContext(fun)
    def applyToClient(id: Long, fun: (ActorRef) => Unit) = applyToActor(getClientRef(id), fun)
    def getClientRef(id: Long) = context.actorSelection("world-system/listener/client_" + id) 
}