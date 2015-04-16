package com.akfu.world.manager

import com.akfu.world.WorldClient
import com.akfu.common.network.protocol.message.world.serverToClient.FreeCompanionBreedIdMessage

object CompanionManager {
  def sendFreeCompanionId(client: WorldClient) {
    client.self ! new FreeCompanionBreedIdMessage()
  }
}