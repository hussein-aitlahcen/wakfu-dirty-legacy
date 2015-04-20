package com.akfu.common.network.protocol.message.world.clientToServer

import com.akfu.common.network.protocol.message.WakfuClientMessage
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf

final class CharacterCreationMessage extends WakfuClientMessage {
  def getOpCode() = OpCode CMSG_CHARACTER_CREATION
  
  var characterId: Long = 0
  var sex: Byte = 0
  var skinColorIndex: Byte = 0
  var hairColorIndex: Byte = 0
  var pupilColorIndex: Byte = 0
  var skinColorFactor: Byte = 0
  var hairColorFactor: Byte = 0
  var clothIndex: Byte = 0
  var faceIndex: Byte = 0
  var breed: Short = 0 
  var name = "?"
  
  override def deserialize(in: ByteBuf) {
    characterId = in readLong()
    sex = in readByte()
    skinColorIndex = in readByte()
    hairColorIndex = in readByte()
    pupilColorIndex = in readByte()
    skinColorFactor = in readByte()
    hairColorFactor = in readByte()
    clothIndex = in readByte()
    faceIndex = in readByte()
    breed = in readShort()
    name = in readByteString()
  }
}