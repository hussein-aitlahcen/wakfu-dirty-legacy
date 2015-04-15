package com.akfu.auth

import akka.actor.Actor
import akka.actor.ActorLogging
import com.akfu.auth.manager.AuthenticationManager
import com.akfu.auth.network.protocol.message.serverToClient.ClientDispatchAuthenticationResultMessage
import com.akfu.auth.network.protocol.message.serverToClient.AuthenticationResultEnum
import com.akfu.auth.network.protocol.message.serverToClient.ClientPublicKeyMessage
import com.akfu.common.network.security.ConnectionEncryptionManager
import com.akfu.common.Community
import com.akfu.auth.network.protocol.message.serverToClient.AccountInformation
import com.akfu.auth.network.protocol.message.serverToClient.ClientProxiesResultMessage
import com.akfu.auth.network.protocol.message.serverToClient.WorldInfo
import com.akfu.common.configuration.SystemConfiguration
import com.akfu.common.configuration.SystemConfigurationType
import com.akfu.auth.network.protocol.message.serverToClient.ProxyInfo
import com.akfu.common.network.RemoveFrame
import com.akfu.auth.network.protocol.frame._
import com.akfu.common.network.AddFrame

sealed trait WorkerProcess
final case class AuthConnected(client: AuthClient) extends WorkerProcess
final case class AuthDisconnected(client: AuthClient) extends WorkerProcess
final case class Authentication(client: AuthClient, account: String, password: String) extends WorkerProcess
final case class ProxiesRequest(client: AuthClient) extends WorkerProcess
final case class AuthTokenRequest(client: AuthClient, serverId: Int) extends WorkerProcess

final class AuthWorker extends Actor with ActorLogging {
  
  def receive = {
    case AuthConnected(client) =>                         connected(client)
    case AuthDisconnected(client) =>                      disconnected(client)
    case Authentication(client, account, password) =>     authentication(client, account, password)      
    case ProxiesRequest(client) =>                        proxiesRequest(client) 
    case AuthTokenRequest(client, serverId) =>            authTokenRequest(client, serverId)
  }
  
  def connected(client: AuthClient) {
    println("client connected")
  }
  
  def disconnected(client: AuthClient) {
   println("client disconnected") 
  }
  
  def authTokenRequest(client: AuthClient, serverId: Int) {
    println("auth token request")
  }
  
  def proxiesRequest(client: AuthClient) {
    println("proxies request")
    
    val config = new SystemConfiguration()
    config changeProperty(SystemConfigurationType.SERVER_ID, "1")
    config changeProperty(SystemConfigurationType.COMMUNITY_CHECK_ENABLE, "true")
    config changeProperty(SystemConfigurationType.COMMUNITY_REQUIRED, Community.FR.code.toString)
    config changeProperty(SystemConfigurationType.COMMUNITY_FORBIDDEN, "")
    config changeProperty(SystemConfigurationType.AUTHORIZED_PARTNERS, "default")    
    
    val proxy = new ProxyInfo(1, "Test", Community.FR.code, "127.0.0.1", List(5555), 0)
    val world = new WorldInfo(1, "", 100, 100, false, config)
    
    client.self ! new ClientProxiesResultMessage(List(proxy), List(world))
  }
  
  def authentication(client: AuthClient, account: String, password: String) {
    println(s"client auth account=$account password=$password")
    
    client.self ! RemoveFrame(AuthenticationFrame)
    
    if(!AuthenticationManager.checkCredentials(account, password)) {
      println("wrong credentials")
      sendAutenticationResult(client, AuthenticationResultEnum.WRONG_CREDENTIALS)
      return
    }

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