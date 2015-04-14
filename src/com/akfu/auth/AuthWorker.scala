package com.akfu.auth

import akka.actor.Actor
import akka.actor.ActorLogging
import com.akfu.auth.manager.AuthenticationManager
import com.akfu.auth.network.protocol.message.serverToClient.ClientAuthenticationResultsMessage
import com.akfu.auth.network.protocol.message.serverToClient.AuthenticationResultsEnum

sealed trait AuthProcess
final case class ProcessAuthentication(client: AuthClient, account: String, password: String) extends AuthProcess

final class AuthWorker extends Actor with ActorLogging {
  
  def receive = {
    case ProcessAuthentication(client, account, password) =>
      authenticate(client, account, password)
  }
  
  def authenticate(client: AuthClient, account: String, password: String) {
    println(s"client auth account=$account password=$password")
    
    if(!AuthenticationManager.checkCredentials(account, password)) {
      println("wrong credentials")
      sendAutenticationResult(client, AuthenticationResultsEnum.WRONG_CREDENTIALS)
      return
    }
    
    println("good credentials")
    sendAutenticationResult(client, AuthenticationResultsEnum.SUCCESS)    
  }
  
  def sendAutenticationResult(client: AuthClient, result: Byte) {
    client.self ! new ClientAuthenticationResultsMessage(result)
  }  
}