package com.akfu.auth.manager

import akka.actor.Actor
import akka.actor.ActorLogging
import com.akfu.auth.AuthClient

object AuthenticationManager {
  
  def checkCredentials(account: String, password: String) : Boolean =
    account == "Smarken" && password == "test"
    
}