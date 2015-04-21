package com.akfu.world.game.nation

import com.ankamagames.wakfu.common.game.nation.Nation
import com.ankamagames.wakfu.common.game.nation.handlers.NationMembersHandler
import com.ankamagames.wakfu.common.game.nation.Citizen

final class GameNationMembersHandler(nation: Nation) extends NationMembersHandler(nation) {  
  def requestAddCitizen(citizen: Citizen) = ???
  def requestAddCitizen(citizenId: Long) = ???
}