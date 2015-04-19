package com.akfu.world
  
import akka.actor.Actor
import akka.actor.ActorLogging
import akka.io.{ IO, Tcp }
import akka.io.Tcp._
import java.net.InetSocketAddress
import akka.actor.Props
import akka.actor.ActorSystem
import akka.actor.ActorRef
import com.ankamagames.wakfu.common.datas.Breed.AvatarBreedConstants

object WorldService {
  
  final val BIND_PORT = 5555
  final val SOAP_AUTH_URL = "http://127.0.0.1/authentification.php"
  
  private var system: ActorSystem = null
  private var listener: ActorRef = null
  private var worker: ActorRef = null
  
  def getWorker = worker
  def getListener = listener
  def getSystem = system
  
  def initialize { 
    
    AvatarBreedConstants initBreeds
    
    system = ActorSystem create("world-system")
    listener = system actorOf(Props(classOf[WorldListener], BIND_PORT), "listener")
    worker = system actorOf(Props(classOf[WorldWorker]), "worker")
  }
  
}

final class WorldListener(port: Int) extends Actor with ActorLogging {
  
  override def preStart() = {    
    import context.system
    IO(Tcp) ! Bind(self, new InetSocketAddress("0.0.0.0", port))
  }
  
  override def receive = {
    case Bound(address) =>
      log.info(s"WorldService bound on $address")
      
    case Connected(remote, local) =>
      val connection = sender()
      connection ! Register(context.actorOf(Props(classOf[WorldClient], sender)))
  }
}