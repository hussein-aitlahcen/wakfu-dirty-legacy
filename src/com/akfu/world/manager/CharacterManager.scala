package com.akfu.world.manager

import com.akfu.world.WorldClient
import com.akfu.common.network.protocol.message.world.serverToClient.CharactersListMessage

object CharacterManager {
  def sendCharactersList(client: WorldClient) {
    client.self ! new CharactersListMessage()
  }
}