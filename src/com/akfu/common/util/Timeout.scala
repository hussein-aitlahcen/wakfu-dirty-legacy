package com.akfu.common.util

final class Timeout(val duration: Long){
  private var expireTime = System.currentTimeMillis() + duration
  
  def isDone = System.currentTimeMillis() > expireTime
  def reset() = expireTime = System.currentTimeMillis() + duration
}

object Timeout {
  def create(duration: Int) = new Timeout(duration)
}