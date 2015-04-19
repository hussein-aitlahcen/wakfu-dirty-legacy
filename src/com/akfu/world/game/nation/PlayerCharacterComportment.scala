package com.akfu.world.game.nation

import com.ankamagames.wakfu.common.game.nation.impl.GameCitizenComportment
import com.ankamagames.wakfu.common.game.nation.Citizen

final class PlayerCharacterComportment(citizen: Citizen) extends GameCitizenComportment(citizen) {
  override def startCrimePurgation(parameter: Int) {
    // TODO: impl crime purgation
  }
}