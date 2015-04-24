package com.akfu.world.game.territory

import com.ankamagames.baseImpl.common.clientAndServer.world.AbstractPartition
import scala.collection.mutable.Set
import com.akfu.world.game.entity.AbstractCharacterInfo

final class WorldPartition(x: Int, y: Int, width: Int, height: Int, val center: Boolean) extends AbstractPartition[WorldPartition] {
  
  setBounds(x, y, width, height)
  
  private var entities = Set[AbstractCharacterInfo]()
  
  def addEntity(entity: AbstractCharacterInfo) = entities += entity
  def removeEntity(entity: AbstractCharacterInfo) = entities -= entity
}