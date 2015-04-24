package com.akfu.world.game.entity

import com.ankamagames.wakfu.common.datas.BasicCharacterInfo
import com.ankamagames.baseImpl.common.clientAndServer.world.PartitionConstants
import com.ankamagames.framework.kernel.core.maths.MathHelper
import akka.actor.ActorRef
import com.akfu.world.manager.WorldManager

abstract class AbstractCharacterInfo extends BasicCharacterInfo {
  
  private var currentTerritoryId: Int = -1
  private var currentWorld: ActorRef = null
  
  def getWorld(): ActorRef = {
    if(currentWorld == null)
      currentWorld = WorldManager getById(getInstanceId)
    return currentWorld
  }

  override def setInstanceId(id: Short) {
    currentWorld = WorldManager getById(id)
    super.setInstanceId(id)    
  }
  
  def getTerritoryId() = currentTerritoryId
  def setTerritoryId(territoryId: Int) = currentTerritoryId = territoryId
  
  def getCurrentPartition() = 
    (PartitionConstants.getPartitionXFromCellX(getWorldCellX), PartitionConstants.getPartitionYFromCellY(getWorldCellY))
    
  def getCurrentPartitionId(): Int = {
    val (x, y) = getCurrentPartition
    return MathHelper.getIntFromTwoInt(x, y)
  }
    
  
  def getSurroundingPartitions() = 
    List(
        MathHelper.getIntFromTwoInt(PartitionConstants.getPartitionXFromCellX(getWorldCellX), 
            PartitionConstants.getPartitionYFromCellY(getWorldCellY)),   
            
         MathHelper.getIntFromTwoInt(PartitionConstants.getPartitionXFromCellX(getWorldCellX) + 1, 
            PartitionConstants.getPartitionYFromCellY(getWorldCellY)),
            
         MathHelper.getIntFromTwoInt(PartitionConstants.getPartitionXFromCellX(getWorldCellX) - 1, 
            PartitionConstants.getPartitionYFromCellY(getWorldCellY)),
            
         MathHelper.getIntFromTwoInt(PartitionConstants.getPartitionXFromCellX(getWorldCellX), 
            PartitionConstants.getPartitionYFromCellY(getWorldCellY) + 1),
            
         MathHelper.getIntFromTwoInt(PartitionConstants.getPartitionXFromCellX(getWorldCellX), 
            PartitionConstants.getPartitionYFromCellY(getWorldCellY) - 1),
            
          MathHelper.getIntFromTwoInt(PartitionConstants.getPartitionXFromCellX(getWorldCellX) + 1, 
            PartitionConstants.getPartitionYFromCellY(getWorldCellY) + 1),
            
          MathHelper.getIntFromTwoInt(PartitionConstants.getPartitionXFromCellX(getWorldCellX) - 1, 
            PartitionConstants.getPartitionYFromCellY(getWorldCellY) - 1),
            
          MathHelper.getIntFromTwoInt(PartitionConstants.getPartitionXFromCellX(getWorldCellX) + 1, 
            PartitionConstants.getPartitionYFromCellY(getWorldCellY) - 1),
            
          MathHelper.getIntFromTwoInt(PartitionConstants.getPartitionXFromCellX(getWorldCellX) - 1, 
            PartitionConstants.getPartitionYFromCellY(getWorldCellY) + 1)         
        )
}