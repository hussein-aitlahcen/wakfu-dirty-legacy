package com.akfu.world.manager

import com.akfu.world.WorldClient
import com.akfu.common.network.protocol.message.world.serverToClient.ClientSystemConfigurationMessage
import com.akfu.common.configuration.SystemConfiguration
import com.akfu.common.configuration.SystemConfigurationType
import com.akfu.world.soap.SOAPServer

object SystemConfigurationManager {
  def sendClientSystemConfiguration(client: WorldClient) {
    
    val config = new SystemConfiguration()    
    config changeProperty(SystemConfigurationType SHOP_ENABLED, "false")
    config changeProperty(SystemConfigurationType SHOP_ENABLE_KROSZ, "false")
    config changeProperty(SystemConfigurationType SOAP_AUTHENTICATION_URL, SOAPServer.URL)
    config changeProperty(SystemConfigurationType SOAP_ACCOUNT_URL, SOAPServer.URL)
    
    client.self ! new ClientSystemConfigurationMessage(config)
  }
}