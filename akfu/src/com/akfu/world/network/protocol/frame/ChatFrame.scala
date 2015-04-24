package com.akfu.world.network.protocol.frame

import com.akfu.common.network.FrameBase
import com.akfu.world.WorldClient
import com.akfu.common.network.protocol.message.WakfuClientMessage
import com.akfu.common.network.protocol.OpCode
import com.akfu.common.network.protocol.message.chat.clientToServer.UserVicinityContentMessage
import com.akfu.common.network.FrameHandler
import com.akfu.world.ChatMessage

object ChatFrame extends FrameBase[WorldClient, WakfuClientMessage] {    
  @FrameHandler(opCode = OpCode CMSG_USER_VICINITY_MESSAGE)
  def handleClientVersion(client: WorldClient, message: UserVicinityContentMessage) {
    client.getWorker ! ChatMessage(client, message)
  }    
}