package com.akfu.world.game.nation

import com.ankamagames.wakfu.common.game.nation.handlers.NationHandlersFactory
import com.ankamagames.wakfu.common.game.nation.Nation
import com.ankamagames.wakfu.common.game.nation.handlers.NationBuffsHandler

object GameNationHandlersFactory extends NationHandlersFactory {
  def createBuffsHandler(nation: Nation) = new GameNationBuffsHandler(nation)
  def createMembersHandler(nation: Nation) = new GameNationMembersHandler(nation)
  def createPoliticHandler(nation: Nation) = new GameNationPoliticHandler(nation)
  def createJusticeHandler(nation: Nation) = new GameNationJusticeHandler(nation)
}