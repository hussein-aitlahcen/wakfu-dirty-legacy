package com.akfu.common.network.protocol.message.game.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.world.game.entity.PlayerCharacter
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf

final class CharacterEnterWorldMessage(character: PlayerCharacter) extends WakfuServerMessage {
  def getOpCode() = OpCode SMSG_CHARACTER_ENTER_WORLD
  
  override def internalSerialize(out: ByteBuf) {
    val data = character.serializeForCharacterPositionInformation()
    
    // character info
    out writeShort data.length
    out writeBytes data
    
    // serializedProtectorTotalSize
    out writeShort 2
    // serializedProtectors
    out writeShort 0
    
    // serializerProtectorsInfosTotalSize
    out writeShort 2
    // serializedProtectorsInfos
    out writeShort 0   
  }
}