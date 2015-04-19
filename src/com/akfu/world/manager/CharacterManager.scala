package com.akfu.world.manager

import com.akfu.world.WorldClient
import com.akfu.common.network.protocol.message.world.serverToClient.CharactersListMessage
import com.akfu.common.network.protocol.message.world.serverToClient.ClientAdditionalCharacterSlotsUpdateMessage
import com.akfu.common.database.DatabaseManager
import com.akfu.world.game.entity.PlayerCharacter
import com.akfu.common.network.protocol.message.world.serverToClient.CharacterCreationResultEnum
import com.akfu.common.network.protocol.message.world.serverToClient.CharacterCreationResultMessage
import com.ankamagames.wakfu.common.datas.Breed.AvatarBreedConstants
import org.omg.CORBA.UNKNOWN
import com.akfu.common.network.protocol.message.world.serverToClient.CharacterSelectionResultMessage
import com.akfu.common.network.protocol.message.world.serverToClient.CharacterSelectionResultEnum
import com.akfu.common.network.protocol.message.game.serverToClient.CharacterInformationMessage
import com.akfu.common.network.protocol.message.game.serverToClient.CharacterEnterPartitionMessage
import com.akfu.common.network.protocol.message.game.serverToClient.CharacterEnterWorldMessage
import org.slf4j.LoggerFactory

object CharacterManager {
  
  val log = LoggerFactory.getLogger(CharacterManager.getClass)
  
  def selectCharacter(client: WorldClient, characterId: Long) {
    val character = DatabaseManager getCharacter(characterId)
    if(!character.isDefined || character.get.accountId != client.getAccount.id){ 
      sendCharacterSelectionResult(client, CharacterSelectionResultEnum ERROR)
      return 
    }   
    
    processSelection(client, new PlayerCharacter(character get))    
  }
  
  private def processSelection(client: WorldClient, character: PlayerCharacter) {    
    client setCharacter character
    sendCharacterSelectionResult(client, CharacterSelectionResultEnum SUCCESS)
    sendCharacterInformation(client, character)
    sendCharacterEnterPartition(client, character)
    sendCharacterEnterWorld(client, character)
  }
  
  def createCharacter(client: WorldClient, 
      id: Long,
      sex: Byte,
      skinColor: Byte,
      hairColor: Byte,
      pupilColor: Byte,
      skinFactor: Byte,
      hairFactor: Byte,
      clothIndex: Byte,
      faceIndex: Byte,
      breed: Short,
      name: String)
  {
    val characters = DatabaseManager getCharacters(client.getAccount id)
    if(characters.length > 4) {
      sendCharacterCreationResult(client, CharacterCreationResultEnum TOO_MANY_CHARACTERS)
      return
    }
    
    if(DatabaseManager.getCharacter(name).isDefined) {
      sendCharacterCreationResult(client, CharacterCreationResultEnum NAME_EXISTING)
      return
    }
    
    if(!AvatarBreedConstants.isBreedEnabled(breed)) {
      sendCharacterCreationResult(client, CharacterCreationResultEnum UNKNOW_ERROR)
      return
    }
    
    try {
      val characterInfo = DatabaseManager.createCharacter(client.getAccount id,
          name, 
          breed, 
          sex, 
          skinColor, 
          hairColor, 
          pupilColor,
          skinFactor,
          hairFactor, 
          clothIndex, 
          faceIndex, 
          1, 
          0, 
          0, 
          0, 
          0, 
          0, 
          7, 
          333);
      
      sendCharacterCreationResult(client, CharacterCreationResultEnum SUCCESS)
      processSelection(client, new PlayerCharacter(characterInfo))
    }
    catch
    {
      case _: Throwable => sendCharacterCreationResult(client, CharacterCreationResultEnum UNKNOW_ERROR)
    }
  }
  
  def sendCharacterEnterWorld(client: WorldClient, character: PlayerCharacter) {
    client.self ! new CharacterEnterWorldMessage(character)
  }
  
  def sendCharacterEnterPartition(client: WorldClient, character: PlayerCharacter) {
    client.self ! new CharacterEnterPartitionMessage(character.getPosition.getX, character.getPosition.getY)
  }
  
  def sendCharacterInformation(client: WorldClient, character: PlayerCharacter) {
    client.self ! new CharacterInformationMessage(character)
  }
  
  def sendCharacterSelectionResult(client: WorldClient, result: Byte) {
    client.self ! new CharacterSelectionResultMessage(result)
  }
  
  def sendCharacterCreationResult(client: WorldClient, result: Byte) {    
      client.self ! new CharacterCreationResultMessage(result)
  }
  
  def sendCharactersList(client: WorldClient) {
    log info "sending character list"
    client.self ! new CharactersListMessage(
        DatabaseManager.getCharacters(client.getAccount id) map(new PlayerCharacter(_)) )
    log info "character list sent"
  }
  
  def sendAdditionalSlots(client: WorldClient) {
    client.self ! new ClientAdditionalCharacterSlotsUpdateMessage(3)
  }  
}