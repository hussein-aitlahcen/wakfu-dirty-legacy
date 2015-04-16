package com.akfu.world

import akka.actor.Actor
import akka.actor.ActorLogging
import com.akfu.world.manager.AuthenticationManager
import com.akfu.common.network.protocol.message.serverToClient.ClientAuthenticationResultsMessage
import com.akfu.common.network.protocol.message.serverToClient.AuthenticationResultEnum
import com.akfu.world.network.protocol.frame.AuthenticationFrame
import com.akfu.common.network.RemoveFrame

sealed trait WorkerProcess
final case class WorldConnected(client: WorldClient) extends WorkerProcess
final case class WorldDisconnected(client: WorldClient) extends WorkerProcess
final case class Authenticate(client: WorldClient, token: String) extends WorkerProcess

final class WorldWorker extends Actor with ActorLogging {  
  def receive = {
    case WorldConnected(client) =>                         connected(client)
    case WorldDisconnected(client) =>                      disconnected(client)
    case Authenticate(client, token) =>                    authenticate(client, token)
  }  
    
  def connected(client: WorldClient) {
    log.info("world client connected")
  }
  
  def disconnected(client: WorldClient) {
    log.info("world client disconnected")     
  }  
  
  def authenticate(client: WorldClient, token: String) {
    log.info(s"world auth checking\n\ttoken=$token")
    
    client.self ! RemoveFrame(AuthenticationFrame)
    
    if(!AuthenticationManager.checkToken(token)) {
      sendAuthenticationResult(client, AuthenticationResultEnum.INVALID_TOKEN)
      return
    }
    
    
  }
  
  def sendAuthenticationResult(client: WorldClient, result: Byte, banDuration: Int = 0) {
    client.self ! new ClientAuthenticationResultsMessage(result, banDuration)
    
    result match {
      case result if result != AuthenticationResultEnum.SUCCESS =>
        client disconnect
      case _ => // NOTHING
    }
  }
}