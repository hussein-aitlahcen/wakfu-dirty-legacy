package com.akfu.common.network.protocol.message.game.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf
import com.akfu.world.game.entity.PlayerCharacter

final class ClientCharacterUpdateMessage(character: PlayerCharacter) extends WakfuServerMessage {
 def getOpCode() = OpCode SMSG_CLIENT_CHARACTER_UPDATE
 
 override def internalSerialize(out: ByteBuf) {
   val data = character serializeForLocalCharacterInformation()
   out writeLong character.getId
   out writeShort data.length
   out writeBytes data
 }
}