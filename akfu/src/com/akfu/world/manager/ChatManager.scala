package com.akfu.world.manager

import com.akfu.world.WorldClient
import com.akfu.common.network.protocol.message.chat.clientToServer.UserContentMessage
import com.akfu.world.game.entity.AbstractCharacterInfo
import com.akfu.common.network.protocol.message.chat.clientToServer.UserContentMessage
import com.akfu.world.game.territory.ChatEntity

object ChatManager {
  def chatRequest(client: WorldClient, message: UserContentMessage) {
    processChatMessage(client getCharacter, message)
  }
  
  def processChatMessage(entity: AbstractCharacterInfo, message: UserContentMessage) {
    entity.getWorld ! ChatEntity(entity, message)
  }
}