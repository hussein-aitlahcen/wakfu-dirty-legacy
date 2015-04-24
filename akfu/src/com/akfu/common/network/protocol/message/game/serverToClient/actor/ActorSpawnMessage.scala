package com.akfu.common.network.protocol.message.game.serverToClient.actor

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf
import com.akfu.world.game.entity.AbstractCharacterInfo

final class ActorSpawnMessage(entities: List[AbstractCharacterInfo]) extends WakfuServerMessage {
  
  def getOpCode() = OpCode SMSG_ACTOR_SPAWN
  
  override def internalSerialize(out: ByteBuf) {
    out writeByte 0 // fightSpawn
    out writeByte entities.length
    for(entity <- entities) {
      val data = entity.serializeForRemoteCharacterInformation()
      
      out writeByte entity.getType
      out writeLong entity.getId
      out writeShort data.length
      out writeBytes data
    }
  }
//       final ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
//        this.m_myFightSpawn = (buffer.get() == 1);
//        final int charactersCount = buffer.get() & 0xFF;
//        this.m_characterTypes = new TByteArrayList(charactersCount);
//        this.m_characterIds = new TLongArrayList(charactersCount);
//        this.m_characterSerialized = new ArrayList<byte[]>(charactersCount);
//        for (int i = 0; i < charactersCount; ++i) {
//            this.m_characterTypes.add(buffer.get());
//            this.m_characterIds.add(buffer.getLong());
//            final int serializedSize = buffer.getShort() & 0xFFFF;
//            final byte[] serializedCharacter = new byte[serializedSize];
//            buffer.get(serializedCharacter);
//            this.m_characterSerialized.add(serializedCharacter);
//        }
//        return true;
}