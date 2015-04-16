package com.akfu.world.manager

import com.akfu.world.WorldClient
import com.akfu.common.network.protocol.message.world.serverToClient.ClientCalendarSynchronizationMessage

object CalendarManager {
  def sendCalendarSynchronization(client: WorldClient) {
    client.self ! new ClientCalendarSynchronizationMessage()
  }
}