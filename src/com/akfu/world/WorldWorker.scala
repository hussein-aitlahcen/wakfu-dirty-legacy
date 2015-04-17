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
final case class AddToken(token: String) extends WorkerProcess
final case class GameTokenRequest(client: WorldClient) extends WorkerProcess

final class WorldWorker extends Actor with ActorLogging {  
  def receive = {
    case WorldConnected(client) =>                         connected(client)
    case WorldDisconnected(client) =>                      disconnected(client)
    case Authenticate(client, token) =>                    authenticate(client, token)
    case AddToken(token) =>                                addToken(token)
    case GameTokenRequest(client) =>                       gameTokenRequest(client)
  }  
    
  def connected(client: WorldClient) {
    log info "world client connected"
  }
  
  def disconnected(client: WorldClient) {
    log info "world client disconnected"     
  }  
  
  def authenticate(client: WorldClient, token: String) {
    AuthenticationManager login(client, token)
  }
  
  def addToken(token: String) {
    log info "auth token received"
    AuthenticationManager addToken token
  }
  
  def gameTokenRequest(client: WorldClient) {
    log info "game token requested"
    AuthenticationManager generateGameToken client 
  }
}