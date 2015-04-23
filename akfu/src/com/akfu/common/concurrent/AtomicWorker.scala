package com.akfu.common.concurrent

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.routing.Listeners
import akka.actor.ActorRef
import akka.routing.Listen
import akka.routing.Deafen

trait WorkerTask
case class ExecuteInContext(task: (ActorRef) => Unit) extends WorkerTask
case object ListenMe extends WorkerTask
case object DeafenMe extends WorkerTask

abstract class AtomicWorker extends Actor with ActorLogging with Listeners {
  def receive = {
    case ExecuteInContext(task) => executeInContext(task)
    case ListenMe => sender ! Listen(self)
    case DeafenMe => sender ! Deafen(self)
    case unhandled: Any => log info "unhandled message : actor=" + getClass.getName + " message=" + unhandled
  }  
    
  private def executeInContext(task: (ActorRef) => Unit) {
    try {
      task(self)
    }
    catch {
      case _: Throwable => log info s"task processing error $task"
    }
  }
}