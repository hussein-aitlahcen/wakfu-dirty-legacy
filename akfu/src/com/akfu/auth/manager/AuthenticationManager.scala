package com.akfu.auth.manager

import com.akfu.common.database.DatabaseManager
import com.akfu.auth.AuthClient
import com.akfu.common.network._
import com.akfu.auth.network.protocol.frame._
import com.akfu.common.network.protocol.message.serverToClient._
import com.akfu.common.Community
import org.slf4j.LoggerFactory
import java.util.Date

object AuthenticationManager {
  
  private final val log = LoggerFactory.getLogger(AuthenticationManager.getClass)
  
  def login(client: AuthClient, accountName: String, password: String) {
    
    log.info(s"login checking\n\taccount=$accountName\n\tpassword=$password")
    
    client.self ! RemoveFrame(AuthenticationFrame)
    
    val account = DatabaseManager.getAccount(accountName).getOrElse(null)
    if(account == null || account.password != password) {
      sendAutenticationResult(client, AuthenticationResultEnum WRONG_CREDENTIALS)
      return
    }   
    
    if(account.banned_until.after(new Date())) {      
      sendAutenticationResult(client, AuthenticationResultEnum BANNED)
      return
    }
        
    if(account.online) {      
      sendAutenticationResult(client, AuthenticationResultEnum ALREADY_CONNECTED)
      return      
    }
    
    log.info(s"auth success")
    
    client setAccount account
    
    // TODO: set offline properly to uncomment
    //account setOnline(true)
    
    client.self ! AddFrame(ServerSelectionFrame)
    sendAutenticationResult(client, AuthenticationResultEnum.SUCCESS, new AccountInformation(Community.FR.code))    
  }
  
  def sendAutenticationResult(client: AuthClient, result: Byte, infos: AccountInformation = null) {
    client.self ! new ClientDispatchAuthenticationResultMessage(result, infos)
    result match {
      case result if result != AuthenticationResultEnum.SUCCESS =>
        client disconnect
      case _ => // NOTHING
    }
  }  
}