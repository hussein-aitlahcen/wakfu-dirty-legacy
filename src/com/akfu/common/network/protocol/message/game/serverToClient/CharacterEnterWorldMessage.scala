package com.akfu.common.network.protocol.message.game.serverToClient

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.world.game.entity.PlayerCharacter
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf

final class CharacterEnterWorldMessage(character: PlayerCharacter) extends WakfuServerMessage {
  def getOpCode() = OpCode SMSG_CHARACTER_ENTER_WORLD
  
  override def internalSerialize(out: ByteBuf) {
    val data = character.serializeForCharacterPositionInformation()
    out writeShort data.length
    out writeBytes data
    
    out writeShort 2 // protectorDataSize
    out writeShort 0 // 0
    
    
    out writeShort 2 // nationProtectorDataSize
    out writeShort 0
    
    out writeShort 2 // ??
    out writeShort 0    
    
    out writeShort 2 // ??
    out writeShort 0    
  }
}