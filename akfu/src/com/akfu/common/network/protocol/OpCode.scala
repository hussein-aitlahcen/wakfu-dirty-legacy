package com.akfu.common.network.protocol

object OpCode {
  final val CMSG_CLIENT_VERSION = 7
  final val CMSG_DISPATCH_AUTH = 1026
  final val CMSG_CLIENT_PUBLIC_KEY_REQUEST = 1033
  final val CMSG_CLIENT_PROXIES_REQUEST = 1035
  final val CMSG_AUTH_TOKEN_REQUEST = 1211
  final val CMSG_AUTH_TOKEN = 1213
  final val CMSG_GAME_AUTH_TOKEN_REQUEST = 2079
  final val CMSG_CHARACTER_CREATION = 2053
  final val CMSG_CHARACTER_SELECTION = 2049
  final val CMSG_ACTOR_PATH_REQUEST = 4113
  final val CMSG_USER_VICINITY_MESSAGE = 3153
  
  final val SMSG_CLIENT_IP = 110
  final val SMSG_DISPATCH_AUTH_RESULT = 1027
  final val SMSG_CLIENT_PUB_KEY = 1034
  final val SMSG_CLIENT_PROXIES_RESULT = 1036
  final val SMSG_AUTH_TOKEN_RESULT = 1212
  final val SMSG_AUTH_RESULTS = 1025
  final val SMSG_WORLD_SELECTION_RESULT = 1202
  final val SMSG_CALENDAR_SYNC = 2063
  final val SMSG_FREE_COMPANION_BREED_ID = 2078
  final val SMSG_CLIENT_SYSTEM_CONF = 2067
  final val SMSG_CLIENT_ADD_SLOT_UPDATE = 2069
  final val SMSG_CHARACTERS_LIST = 2048
  final val SMSG_COMPANION_LIST = 2077
  final val SMSG_GAME_AUTH_TOKEN_RESULT = 2079  
  final val SMSG_CHARACTER_CREATION_RESULT = 2054
  final val SMSG_CHARACTER_SELECTION_RESULT = 2050
  final val SMSG_CHARACTER_INFORMATION = 4098
  final val SMSG_CHARACTER_ENTER_WORLD = 4100
  final val SMSG_CHARACTER_ENTER_PARTITION = 4125
  final val SMSG_CHARACTER_UPDATE = 4130  
  final val SMSG_CLIENT_CHARACTER_UPDATE = 20002
  final val SMSG_CLIENT_NATION_SYNC = 20000
  final val SMSG_FRIEND_LIST = 3144
  final val SMSG_IGNORE_LIST = 3146
  final val SMSG_ACTOR_SPAWN = 4102
  final val SMSG_ACTOR_DESPAWN = 4104
  final val SMSG_ACTOR_MOVE_TO = 4127
  final val SMSG_ACTOR_PATH_UPDATE = 4114
  final val SMSG_VICINITY_MESSAGE = 3152
}