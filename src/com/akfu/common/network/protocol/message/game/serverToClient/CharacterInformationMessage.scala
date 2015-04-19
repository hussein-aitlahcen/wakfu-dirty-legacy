package com.akfu.common.network.protocol.message.game.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf
import com.akfu.world.game.entity.PlayerCharacter

final class CharacterInformationMessage(character: PlayerCharacter) extends WakfuServerMessage {
  def getOpCode() = OpCode SMSG_CHARACTER_INFORMATION
  
  override def internalSerialize(out: ByteBuf) {
    out writeShort 0 // reserverId ???
    val data = character.serializeForLocalCharacterInformation()
    out writeShort data.length
    out writeBytes data
  }
}