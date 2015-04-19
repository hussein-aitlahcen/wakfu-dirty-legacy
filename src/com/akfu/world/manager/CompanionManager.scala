package com.akfu.world.manager

import com.akfu.world.WorldClient
import com.akfu.common.network.protocol.message.world.serverToClient.FreeCompanionBreedIdMessage
import com.akfu.common.network.protocol.message.world.serverToClient.CompanionListMessage

object CompanionManager {
  def sendFreeCompanionId(client: WorldClient) {
    client.self ! new FreeCompanionBreedIdMessage()
  }
  
  def sendCompanionList(client: WorldClient) {
    client.self ! new CompanionListMessage()
  }
}