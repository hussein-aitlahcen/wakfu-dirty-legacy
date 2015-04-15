package com.akfu.common

sealed abstract class Community(val code: Int, val value: String)

object Community {
  object FR extends Community(0, "fr") 
  object UK  extends Community(1, "en") 
  object INT extends Community(2, "int")
  object DE extends Community(3, "de") 
  object ES extends Community(4, "es")
  object RU extends Community(5, "ru")
  object PT extends Community(6, "pt")
  object NL extends Community(7, "nl")
  object JP extends Community(8, "jp")
  object IT extends Community(9, "it")
  object NA extends Community(11, "na") 
  object CN extends Community(12, "cn")
  object ASIA extends Community(13, "asia")
  object TW extends Community(14, "tw")
}