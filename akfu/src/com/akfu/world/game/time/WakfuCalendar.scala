package com.akfu.world.game.time

import com.ankamagames.baseImpl.common.clientAndServer.game.time.calendar.GameCalendar

object WakfuCalendar extends GameCalendar(1970, 1) {
  def getDayPercentage() = 0
}