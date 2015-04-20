package com.akfu.world.manager

import com.akfu.world.WorldClient
import com.akfu.common.network.protocol.message.world.serverToClient.ClientSystemConfigurationMessage
import com.akfu.common.configuration.SystemConfiguration
import com.akfu.common.configuration.SystemConfigurationType
import com.akfu.world.WorldService

object SystemConfigurationManager {
  def sendClientSystemConfiguration(client: WorldClient) {    
    val config = new SystemConfiguration()    
    config changeProperty(SystemConfigurationType SHOP_ENABLED, "true")
    config changeProperty(SystemConfigurationType SHOP_ENABLE_KROSZ, "false")
    config changeProperty(SystemConfigurationType SOAP_AUTHENTICATION_URL, WorldService SOAP_AUTH_URL)
    config changeProperty(SystemConfigurationType SOAP_ACCOUNT_URL, WorldService SOAP_AUTH_URL)
    config changeProperty(SystemConfigurationType SOAP_SHOP_URL, WorldService SOAP_AUTH_URL)
    config changeProperty(SystemConfigurationType SHOP_KEY, "TEST")
    client.self ! new ClientSystemConfigurationMessage(config)
  }
}