package com.akfu.common.network.protocol.message

abstract case class WakfuMessage() extends FrameMessage {
  def getOpCode() : Int
}