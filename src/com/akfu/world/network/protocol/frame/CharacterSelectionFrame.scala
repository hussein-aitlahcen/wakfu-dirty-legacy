package com.akfu.world.network.protocol.frame

import com.akfu.common.network.FrameBase
import com.akfu.world.WorldClient
import com.akfu.common.network.protocol.message.WakfuClientMessage
import com.akfu.common.network.protocol.OpCode
import com.akfu.common.network.FrameHandler
import com.akfu.common.network.protocol.message.world.clientToServer.CharacterCreationMessage
import com.akfu.world.CreateCharacter
import com.akfu.common.network.protocol.message.world.clientToServer.CharacterSelectionMessage
import com.akfu.world.SelectCharacter

object CharacterSelectionFrame extends FrameBase[WorldClient, WakfuClientMessage] {  
    
  @FrameHandler(opCode = OpCode CMSG_CHARACTER_CREATION)
  def handleCharacterCreation(client: WorldClient, message: CharacterCreationMessage) {
    log info "frame character creation request"
    client.getWorker ! CreateCharacter(client, message)
  }    
  
  @FrameHandler(opCode = OpCode CMSG_CHARACTER_SELECTION)
  def handleCharacterSelection(client: WorldClient, message: CharacterSelectionMessage) {
    client.getWorker ! SelectCharacter(client, message.characterId)
  }
}