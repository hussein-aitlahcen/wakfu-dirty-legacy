package com.akfu.common.network.protocol.message.game.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.world.game.entity.PlayerCharacter
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf

final class CharacterUpdateMessage(character: PlayerCharacter) extends WakfuServerMessage {
  def getOpCode() = OpCode SMSG_CHARACTER_UPDATE
  
  override def internalSerialize(out: ByteBuf) {
    val data = character.serializeForRemoteCharacterInformation()
    out writeLong character.getId
    out writeShort data.length
    out writeBytes data
  }
}