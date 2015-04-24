package com.akfu.world
  
import akka.actor.Actor
import akka.actor.ActorLogging
import akka.io.{ IO, Tcp }
import akka.io.Tcp._
import java.net.InetSocketAddress
import akka.actor.Props
import akka.actor.ActorSystem
import akka.actor.ActorRef
import com.ankamagames.wakfu.common.datas.Breed.AvatarBreedConstants
import com.ankamagames.framework.kernel.core.common.message.Worker
import com.ankamagames.framework.kernel.core.common.message.scheduler.process.ScheduledProcess
import com.ankamagames.baseImpl.common.clientAndServer.game.time.calendar.GameCalendar
import com.ankamagames.framework.kernel.core.common.message.scheduler.process.ProcessScheduler
import com.akfu.world.game.time.WakfuCalendar
import com.ankamagames.wakfu.common.game.nation.NationManager
import com.ankamagames.wakfu.common.game.nation.Nation
import com.akfu.world.game.nation.GameNationHandlersFactory
import com.akfu.world.game.content.ContentLoader
import com.ankamagames.framework.fileFormat.io.binaryStorage2.BinaryDocumentManager
import com.ankamagames.wakfu.common.game.item.ReferenceItemManager
import com.ankamagames.wakfu.client.core.game.item.ItemTypeManager
import com.ankamagames.wakfu.client.core.game.item.ItemType
import com.ankamagames.wakfu.client.core.WakfuConfiguration
import com.ankamagames.wakfu.client.core.world.WorldInfoManager
import com.ankamagames.framework.kernel.core.common.MonitoredPool
import com.ankamagames.framework.kernel.core.common.ObjectFactory
import com.ankamagames.framework.kernel.core.common.Poolable
import com.akfu.world.manager.WorldManager

object WorldService {
  
  final val BIND_PORT = 5555
  final val SOAP_AUTH_URL = "http://127.0.0.1/authentification.php"
  
  private var system: ActorSystem = null
  private var listener: ActorRef = null
  private var worker: ActorRef = null
  
  def getWorker = worker
  def getListener = listener
  def getSystem = system
  
  def initialize { 
    
    Worker.getInstance start()
    WakfuCalendar start(1000)
    
    AvatarBreedConstants initBreeds()
    WakfuConfiguration getInstance() load()
    WorldInfoManager getInstance() load(WakfuConfiguration getInstance() getString("worldInfoFile"))
        
    Nation setHandlersFactory(GameNationHandlersFactory)
    NationManager.INSTANCE registerNation(Nation createNation(Nation AMAKNA))
    NationManager.INSTANCE registerNation(Nation createNation(Nation SUFOKIA))
    NationManager.INSTANCE registerNation(Nation createNation(Nation BONTA))
    NationManager.INSTANCE registerNation(Nation createNation(Nation BRAKMAR))
    
    BinaryDocumentManager.getInstance setPath(WakfuConfiguration getContentPath("binaryDataFile"))
    ContentLoader initialize()
        
    system = ActorSystem create("world-system")
    listener = system actorOf(Props(classOf[WorldListener], BIND_PORT), "listener")
    worker = system actorOf(Props(classOf[WorldWorker]), "worker")
    
    WorldManager initialize system
  }  
}

final class WorldListener(port: Int) extends Actor with ActorLogging {
  
  private var clientNextId = 1
  
  override def preStart() = {    
    import context.system
    IO(Tcp) ! Bind(self, new InetSocketAddress("0.0.0.0", port))
  }
  
  override def receive = {
    case Bound(address) =>
      log.info(s"WorldService bound on $address")
      
    case Connected(remote, local) =>
      clientNextId += 1
      val connection = sender()
      connection ! Register(context.actorOf(Props(new WorldClient(connection, clientNextId)), "client_" + clientNextId))
  }
}