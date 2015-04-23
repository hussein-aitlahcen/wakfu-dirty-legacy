package com.akfu.world.manager

import com.akfu.world.WorldClient
import com.ankamagames.framework.kernel.core.maths.Direction8Path
import com.ankamagames.wakfu.common.datas.BasicCharacterInfo
import org.slf4j.LoggerFactory
import com.akfu.world.game.territory.MoveEntity

object MovementManager {
  
  val log = LoggerFactory.getLogger(MovementManager.getClass)
  
  def movementRequest(client: WorldClient, path: Direction8Path) {
    //TODO: CHECK PATH N SHITS
    moveActor(client getCharacter, path)
  }
  
  def moveActor(actor: BasicCharacterInfo, path: Direction8Path) {
    WorldManager.getById(actor getInstanceId) ! MoveEntity(actor.getId, path)
  }
}