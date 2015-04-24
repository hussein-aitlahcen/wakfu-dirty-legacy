package com.akfu.world.game.territory

import scala.collection.mutable.HashMap
import scala.collection.mutable.Set
import com.akfu.world.util.ActorHelper
import com.akfu.world.game.entity.AbstractCharacterInfo
import org.slf4j.LoggerFactory
import akka.actor.ActorLogging
import scala.collection.mutable.ArrayBuffer

trait PartitionedTerritory {
  self: ActorHelper with ActorLogging =>
    
  protected var partitions = HashMap[Int, Set[AbstractCharacterInfo]]()
  
  def executeSurrounding(areas: List[Int], fun: (Set[AbstractCharacterInfo]) => Unit) {
    areas foreach({
      case (area) =>
        partitions.get(area) match {
          case Some(listeners) =>
            fun(listeners)
            
          case None =>
        }
    })
  }
  
  def getSurroundingEntities(partition: Int, except: List[AbstractCharacterInfo] = List()) : List[AbstractCharacterInfo] = {
    return partitions(partition).toList diff except
  }
  
  def dispatchSurrounding(partition: Int, message: Any, except: List[AbstractCharacterInfo] = List()) {
    dispatchToClients(getSurroundingEntities(partition, except) map(_.getClientId), message)
  }
  
  def addToSurroudingPartitions(entity: AbstractCharacterInfo, partitions: List[Int]) {
    executeSurrounding(partitions, (listeners) => {
      listeners += entity
    })
  }
  
  def removeFromSurroundingPartitions(entity: AbstractCharacterInfo, partitions: List[Int]) {
    executeSurrounding(partitions, (listeners) => {
      listeners -= entity
    })
  }    
}