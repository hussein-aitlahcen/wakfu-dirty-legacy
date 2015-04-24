package com.akfu.world.game.territory

import com.ankamagames.baseImpl.common.clientAndServer.world.AbstractLocalPartitionManager

class WorldPartitionManager extends AbstractLocalPartitionManager[WorldPartition] {  
    protected def createPartition(p0: Int, p1: Int, p2: Int, p3: Int, p4: Boolean): WorldPartition = {
      return new WorldPartition(p0, p1, p2, p3, p4)
    }    
}