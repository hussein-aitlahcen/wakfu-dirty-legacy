package com.akfu.world

import akka.actor.Actor
import akka.actor.ActorLogging
import com.akfu.world.manager.AuthenticationManager
import com.akfu.common.network.protocol.message.serverToClient.ClientAuthenticationResultsMessage
import com.akfu.common.network.protocol.message.serverToClient.AuthenticationResultEnum
import com.akfu.world.network.protocol.frame.AuthenticationFrame
import com.akfu.common.network.RemoveFrame
import com.akfu.common.network.protocol.message.world.clientToServer.CharacterCreationMessage
import com.akfu.common.network.protocol.message.world.clientToServer.CharacterCreationMessage
import com.akfu.world.manager.CharacterManager
import com.akfu.common.concurrent.AtomicWorker
import com.akfu.common.concurrent.WorkerTask

final case class WorldConnected(client: WorldClient) extends WorkerTask
final case class WorldDisconnected(client: WorldClient) extends WorkerTask
final case class Authenticate(client: WorldClient, token: String) extends WorkerTask
final case class AddToken(token: String) extends WorkerTask
final case class GameTokenRequest(client: WorldClient) extends WorkerTask
final case class CreateCharacter(client: WorldClient, message: CharacterCreationMessage) extends WorkerTask
final case class SelectCharacter(client: WorldClient, characterId: Long) extends WorkerTask

final class WorldWorker extends AtomicWorker {  
  
  override def receive = {
    case WorldConnected(client) =>                         connected(client)
    case WorldDisconnected(client) =>                      disconnected(client)
    case Authenticate(client, token) =>                    authenticate(client, token)
    case AddToken(token) =>                                addToken(token)
    case GameTokenRequest(client) =>                       gameTokenRequest(client)
    case CreateCharacter(client, message) =>               characterCreation(client, message)
    case SelectCharacter(client, characterId) =>           characterSelection(client, characterId)   
    case unhandled: Any => super.receive(unhandled)
  }    
  
  def connected(client: WorldClient) {
    log info "world client connected"
  }
  
  def disconnected(client: WorldClient) {
    log info "world client disconnected" 
    if(client.getAccount != null) {
      // TODO: set offline n shits
      if(client.getCharacter != null) {
        client getCharacter() save()
      }
    }
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
  
  def characterSelection(client: WorldClient, characterId: Long) {
    log info s"character selection request : $characterId"
    CharacterManager selectCharacter(client, characterId)  
  }
  
  def characterCreation(client: WorldClient, message: CharacterCreationMessage) {
    log info "character creation request"
    CharacterManager createCharacter(client, 
        message.characterId,
        message.sex,
        message.skinColorIndex,
        message.hairColorIndex,
        message.pupilColorIndex,
        message.skinColorFactor,
        message.hairColorFactor,
        message.clothIndex,
        message.faceIndex,
        message.breed,
        message.name)
  }
}