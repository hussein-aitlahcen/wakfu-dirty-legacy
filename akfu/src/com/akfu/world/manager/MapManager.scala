package com.akfu.world.manager

import com.akfu.world.WorldClient
import com.ankamagames.framework.kernel.core.maths.Direction8Path
import com.ankamagames.wakfu.common.datas.BasicCharacterInfo
import org.slf4j.LoggerFactory

object MapManager {
  
  val log = LoggerFactory.getLogger(MapManager.getClass)
  
  def actorMovementRequest(client: WorldClient, path: Direction8Path) {
    log info("endposition=" + path.getEndingPosition)
    moveActor(client getCharacter, path)
  }
  
  def moveActor(actor: BasicCharacterInfo, path: Direction8Path) {
    actor.setPosition(path getEndingPosition)
  }
}