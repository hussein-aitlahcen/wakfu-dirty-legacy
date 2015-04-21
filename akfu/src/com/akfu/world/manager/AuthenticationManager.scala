package com.akfu.world.manager

import org.slf4j.LoggerFactory
import com.akfu.world.network.protocol.frame._
import com.akfu.world.WorldClient
import com.akfu.common.network.protocol.message.serverToClient._
import com.akfu.common.network.RemoveFrame
import scala.collection.mutable.ListBuffer
import com.akfu.common.util.Timeout
import scala.collection.mutable.Map
import scala.collection.mutable.TreeSet
import com.akfu.common.account.AccountInformations
import com.akfu.common.database.DatabaseManager
import com.akfu.common.network.protocol.message.auth.serverToClient.AuthentificationTokenResultMessage
import com.akfu.common.network.AddFrame

final class Token(val value: String) {
  private val timer = Timeout create(AuthenticationManager TOKEN_DURATION)
  def isDead = timer isDone
}


object AuthenticationManager {
  final val TOKEN_DURATION = 5000
  
  private final val log = LoggerFactory.getLogger(AuthenticationManager.getClass)
  
  private var tokens = Map[String, Token]()
  
  def removeToken(token: String) = tokens remove token
  def addToken(token: String) = tokens(token) = new Token(token)
  
  def generateGameToken(client: WorldClient) {
    client.self ! RemoveFrame(GameAuthenticationFrame)
    client.self ! AddFrame(CharacterSelectionFrame)
    client.self ! new AuthentificationTokenResultMessage("salut")
  }
    
  def login(client: WorldClient, tokenValue: String) {
    log info s"world auth checking\n\ttoken=$tokenValue"
    
    client.self ! RemoveFrame(AuthenticationFrame)
    
    val token = tokens.getOrElse(tokenValue, null)
    if(token == null || token.isDead)  {
      sendAuthenticationResult(client, AuthenticationResultEnum INVALID_TOKEN)
      return
    }
    
    val account = DatabaseManager.getAccountByNick(tokenValue).getOrElse(null)
    if(account == null)  {
      sendAuthenticationResult(client, AuthenticationResultEnum INVALID_TOKEN)
      return
    }
        
    client.self ! AddFrame(GameAuthenticationFrame)
    
    log info "world valid token, auth success"
    
    removeToken(tokenValue)
    client setAccount account
    
    sendAuthenticationResult(client, AuthenticationResultEnum SUCCESS, account getInformations)
    sendWorldSelectionResult(client, WorldSelectionResultEnum SUCCESS)
    
    client.self ! new ClientIpMessage("127.0.0.1")
    
    CalendarManager sendCalendarSynchronization client
    SystemConfigurationManager sendClientSystemConfiguration client
    CompanionManager sendCompanionList client
    CompanionManager sendFreeCompanionId client    
    CharacterManager sendAdditionalSlots client
    CharacterManager sendCharactersList client
  }
  
  def sendAuthenticationResult(client: WorldClient, result: Byte, infos: AccountInformations = null, banDuration: Int = 0) {
    client.self ! new ClientAuthenticationResultsMessage(result, infos, banDuration)
    
    result match {
      case result if result != AuthenticationResultEnum.SUCCESS =>
        client disconnect
      case _ => // NOTHING
    }
  }
  
  def sendWorldSelectionResult(client: WorldClient, result: Byte) {
    client.self ! new WorldSelectionResultMessage(result)
  }
}