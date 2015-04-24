package com.akfu.common.network.protocol.message.game.serverToClient.actor

import com.akfu.common.network.protocol.message.WakfuServerMessage
import com.akfu.common.network.protocol.OpCode
import io.netty.buffer.ByteBuf
import com.akfu.world.game.entity.AbstractCharacterInfo

final class ActorDespawnMessage(entities: List[AbstractCharacterInfo]) extends WakfuServerMessage {
  def getOpCode() = OpCode SMSG_ACTOR_DESPAWN
  
  override def internalSerialize(out: ByteBuf) {
    out writeByte 0 // applyApsOnDespawn
    out writeByte 0 // fightDespawn
    out writeByte entities.length
    for(entity <- entities) {
      out writeByte entity.getType
      out writeLong entity.getId
    }
  }
//        if (!this.checkMessageSize(rawDatas.length, 3, false)) {
//            return false;
//        }
//        final ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
//        this.m_applyApsOnDespawn = (buffer.get() == 1);
//        this.m_fightDespawn = (buffer.get() == 1);
//        final int actorsCount = buffer.get() & 0xFF;
//        if (!this.checkMessageSize(buffer.remaining(), actorsCount * 9, true)) {
//            return false;
//        }
//        for (int i = 0; i < actorsCount; ++i) {
//            final byte type = buffer.get();
//            final long id = buffer.getLong();
//            this.m_actorsIds.add(new ObjectPair<Byte, Long>(type, id));
//        }
//        return true;
//    }
}