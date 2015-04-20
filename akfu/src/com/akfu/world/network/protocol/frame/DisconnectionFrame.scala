package com.akfu.world.network.protocol.frame

import com.akfu.common.network.FrameBase
import com.akfu.world.WorldClient
import com.akfu.common.network.protocol.message.WakfuClientMessage
import com.akfu.common.network.FrameHandler
import com.akfu.common.network.ClientDisconnected
import com.akfu.world.WorldService
import com.akfu.world.WorldDisconnected

object DisconnectionFrame extends FrameBase[WorldClient, WakfuClientMessage] {    
  @FrameHandler(opCode = ClientDisconnected.OP_CODE)
  def handleClientVersion(client: WorldClient, message: ClientDisconnected) {
    client.getWorker ! WorldDisconnected(client)
  }    
}