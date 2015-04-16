package com.akfu

import akka.actor.ActorSystem
import akka.actor.Props
import com.akfu.auth.AuthService
import com.akfu.auth.AuthWorker
import com.akfu.world.WorldService

object Main extends App {
  AuthService.initialize
  WorldService.initialize
}