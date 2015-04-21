package com.akfu.world.network.protocol.frame

import com.akfu.common.network.FrameBase
import com.akfu.world.WorldClient
import com.akfu.common.network.protocol.message.WakfuClientMessage
import com.akfu.common.network.protocol.OpCode
import com.akfu.common.network.FrameHandler
import com.akfu.common.network.protocol.message.game.clientToServer.ActorPathRequestMessage
import com.ankamagames.baseImpl.common.clientAndServer.world.topology.TopologyMapManager
import com.ankamagames.wakfu.common.game.map.MapHandler
import com.akfu.common.concurrent.ExecuteInContext
import com.akfu.world.manager.MapManager

object WorldMapFrame extends FrameBase[WorldClient, WakfuClientMessage] {    
  @FrameHandler(opCode = OpCode CMSG_ACTOR_PATH_REQUEST)  
  def handleClientGameTokenRequest(client: WorldClient, message: ActorPathRequestMessage) {
    log info s"path request = " + message.path
    
    client.getWorker ! ExecuteInContext(() => MapManager actorMovementRequest(client, message path))
  }  
}