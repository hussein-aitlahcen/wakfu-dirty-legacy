package com.akfu.auth
  
import akka.actor.Actor
import akka.actor.ActorLogging
import akka.io.{ IO, Tcp }
import akka.io.Tcp._
import java.net.InetSocketAddress
import akka.actor.Props
import akka.actor.ActorSystem
import akka.actor.ActorRef

object AuthService {
  
  private var system: ActorSystem = null
  private var listener: ActorRef = null
  private var worker: ActorRef = null
  
  def getWorker = worker
  def getListener = listener
  def getSystem = system
  
  def initialize { 
    system = ActorSystem.create("auth-system")
    listener = system.actorOf(Props(classOf[AuthListener], 80), "listener")
    worker = system.actorOf(Props(classOf[AuthWorker]), "worker")
  }
  
}

final class AuthListener(port: Int) extends Actor with ActorLogging {
  
  override def preStart() = {    
    import context.system
    IO(Tcp) ! Bind(self, new InetSocketAddress("0.0.0.0", port))
  }
  
  override def receive = {
    case Bound(address) =>
      log.info(s"AuthService bound on $address")
      
    case Connected(remote, local) =>
      val connection = sender()
      connection ! Register(context.actorOf(Props(classOf[AuthClient], sender)))
  }
}