package com.akfu.common.concurrent

import akka.actor.Actor
import akka.actor.ActorLogging

trait WorkerTask
case class ExecuteInContext(task: () => Unit) extends WorkerTask

abstract class AtomicWorker extends Actor with ActorLogging {
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