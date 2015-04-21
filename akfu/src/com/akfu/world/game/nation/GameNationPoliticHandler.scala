package com.akfu.world.game.nation

import com.ankamagames.wakfu.common.game.nation.handlers.NationPoliticHandler
import com.ankamagames.wakfu.common.game.nation.Nation

class GameNationPoliticHandler(nation: Nation) extends NationPoliticHandler(nation) {
  // Members declared in com.ankamagames.wakfu.common.game.nation.event.NationPoliticEventHandler
  def onCandidateRegistered(x$1: Long): Unit = ???
  def onCandidateRevalidate(x$1: Long): Unit = ???
  def onCandidateWithdraw(x$1: Long): Unit = ???
  def onCitizenHasVoted(x$1: Long,x$2: Long): Unit = ???
  def onForbiddenCandidatesChanged(): Unit = ???
  
  // Members declared in com.ankamagames.wakfu.common.game.nation.handlers.NationPoliticHandler
  def requestCandidateRegistration(x$1: Long,x$2: com.ankamagames.wakfu.common.game.nation.election.CandidateInfo): Unit = ???
  def requestCandidateRegistration(x$1: Long,x$2: String): Unit = ???
  def requestCitizenVote(x$1: Long,x$2: Long): Unit = ???
}