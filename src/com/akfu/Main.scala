package com.akfu

import akka.actor.ActorSystem
import akka.actor.Props
import com.akfu.auth.AuthService
import com.akfu.auth.AuthWorker
import com.akfu.world.WorldService
import org.apache.log4j.BasicConfigurator

object Main extends App {
  BasicConfigurator.configure
  AuthService.initialize
  WorldService.initialize
}