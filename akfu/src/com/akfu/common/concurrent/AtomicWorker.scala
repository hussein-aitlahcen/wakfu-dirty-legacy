package com.akfu.common.concurrent

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.routing.Listeners

trait WorkerTask
case class ExecuteInContext(task: () => Unit) extends WorkerTask

abstract class AtomicWorker extends Actor with ActorLogging with Listeners {
  def receive = {
    case ExecuteInContext(task) => executeInContext(task)
  }  
    
  def executeInContext(task: () => Unit) {
    try {
      task()
    }
    catch {
      case _: Throwable => log info s"task processing error $task"
    }
  }
}