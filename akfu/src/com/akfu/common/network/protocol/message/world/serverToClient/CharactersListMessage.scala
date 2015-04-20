package com.akfu.common.network.protocol.message.world.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf
import com.akfu.world.game.entity.PlayerCharacter

final class CharactersListMessage(characters: List[PlayerCharacter]) extends WakfuServerMessage {
  def getOpCode() = OpCode.SMSG_CHARACTERS_LIST
  
  override def internalSerialize(out: ByteBuf) {
    out writeByte characters.length
    for(character <- characters) {
      val data = character serializeForCharacterList()
      out writeShort data.length
      out writeBytes data
    }
    
  }
}