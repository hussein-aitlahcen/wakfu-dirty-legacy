package com.akfu.auth

import akka.actor.Actor
import akka.actor.ActorLogging
import com.akfu.common.Community
import com.akfu.common.network.protocol.message.serverToClient.ClientProxiesResultMessage
import com.akfu.common.network.protocol.message.serverToClient.WorldInfo
import com.akfu.common.configuration.SystemConfiguration
import com.akfu.common.configuration.SystemConfigurationType
import com.akfu.common.network.protocol.message.serverToClient.ProxyInfo
import com.akfu.common.network.RemoveFrame
import com.akfu.auth.network.protocol.frame._
import com.akfu.common.network.AddFrame
import akka.actor.actorRef2Scala
import com.akfu.common.network.protocol.message.serverToClient.WakfuAuthenticationTokenResultMessage
import com.akfu.common.network.protocol.message.serverToClient.WakfuAuthenticationTokenResultEnum
import com.akfu.common.network.protocol.message.serverToClient.AuthenticationResultEnum
import com.akfu.common.network.protocol.message.serverToClient.AccountInformation
import com.akfu.common.network.protocol.message.serverToClient.ClientDispatchAuthenticationResultMessage
import com.akfu.auth.manager.AuthenticationManager
import com.akfu.world.WorldService
import com.akfu.world.AddToken

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
    case Authentication(client, account, password) =>     AuthenticationManager login(client, account, password)
    case ProxiesRequest(client) =>                        proxiesRequest(client) 
    case AuthTokenRequest(client, serverId) =>            authTokenRequest(client, serverId)
  }
  
  def connected(client: AuthClient) {
    log.info("auth client connected")
  }
  
  def disconnected(client: AuthClient) {
    log.info("auth client disconnected")     
  }
  
  def authTokenRequest(client: AuthClient, serverId: Int) {
    log.info(s"auth token request\n\t serverId=$serverId")
    
    // add the token on server side 
    WorldService.getWorker ! AddToken(client.getAccount pseudo)
    
    client.self ! new WakfuAuthenticationTokenResultMessage(client.getAccount pseudo, WakfuAuthenticationTokenResultEnum SUCCESS)
  }
  
  def proxiesRequest(client: AuthClient) {
    log.info("proxies request")

    val config = new SystemConfiguration()
    config changeProperty(SystemConfigurationType.SERVER_ID, "1")
    config changeProperty(SystemConfigurationType.COMMUNITY_CHECK_ENABLE, "true")
    config changeProperty(SystemConfigurationType.COMMUNITY_REQUIRED, Community.FR.code.toString)
    config changeProperty(SystemConfigurationType.COMMUNITY_FORBIDDEN, "")
    config changeProperty(SystemConfigurationType.AUTHORIZED_PARTNERS, "default")    
    
    val proxies = List(
        new ProxyInfo(1, "Test", Community.FR.code, "127.0.0.1", List(5555), 0))
        
    val worlds = List(
        new WorldInfo(1, "", 100, 100, false, config))
    
    client.self ! new ClientProxiesResultMessage(proxies, worlds)
  }
  
  def authentication(client: AuthClient, accountName: String, password: String) {
    AuthenticationManager.login(client, accountName, password) 
  }
}