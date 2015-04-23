package com.akfu.world.game.entity

import com.ankamagames.wakfu.common.datas.BasicCharacterInfo

abstract class AbstractCharacterInfo extends BasicCharacterInfo {
  
  private var currentTerritoryId: Int = -1
  
  def getTerritoryId() = currentTerritoryId
  def setTerritoryId(territoryId: Int) = currentTerritoryId = territoryId
}