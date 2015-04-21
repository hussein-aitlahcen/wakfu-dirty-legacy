package com.akfu.world.manager

import com.akfu.world.WorldClient
import com.akfu.common.network.protocol.message.nation.serverToClient.ClientNationSynchronizationMessage

object NationManager {  
  def sendNationSynchronization(client: WorldClient) {
    client.self ! new ClientNationSynchronizationMessage()
  }
}