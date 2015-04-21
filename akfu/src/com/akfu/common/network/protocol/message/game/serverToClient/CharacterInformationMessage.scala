package com.akfu.common.network.protocol.message.game.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf
import com.akfu.world.game.entity.PlayerCharacter

final class CharacterInformationMessage(character: PlayerCharacter) extends WakfuServerMessage {
  def getOpCode() = OpCode SMSG_CHARACTER_INFORMATION
  
  override def internalSerialize(out: ByteBuf) {
    out writeShort 20 // reserverId ???
    var i = 0
    for(i <- 0 to 19)
      out writeLong(27973262776467742L + i)
    val data = character.serializeForLocalCharacterInformation
    out writeInt data.length
    out writeBytes data
  }
}