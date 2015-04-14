package com.akfu.common.network.protocol.message

trait FrameMessage {
  def getOpCode() : Int
}