package com.akfu.auth.network.protocol.message

object OpCode {
  final val CMSG_CLIENT_VERSION = 7
  final val CMSG_DISPATCH_AUTH = 1026
  final val CMSG_CLIENT_PUBLIC_KEY_REQUEST = 1033
  final val CMSG_CLIENT_PROXIES_REQUEST = 1035
  final val CMSG_AUTH_TOKEN_REQUEST = 1211
  
  final val SMSG_DISPATCH_AUTH_RESULT = 1027
  final val SMSG_CLIENT_PUB_KEY = 1034
  final val SMSG_CLIENT_PROXIES_RESULT = 1036
}