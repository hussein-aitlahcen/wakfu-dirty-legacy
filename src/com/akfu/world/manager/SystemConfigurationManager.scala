package com.akfu.world.manager

import com.akfu.world.WorldClient
import com.akfu.common.network.protocol.message.world.serverToClient.ClientSystemConfigurationMessage
import com.akfu.common.configuration.SystemConfiguration
import com.akfu.common.configuration.SystemConfigurationType

object SystemConfigurationManager {
  def sendClientSystemConfiguration(client: WorldClient) {
    
    val config = new SystemConfiguration()    
    config changeProperty(SystemConfigurationType SHOP_ENABLED, "false")
    config changeProperty(SystemConfigurationType SHOP_ENABLE_KROSZ, "false")
    
    client.self ! new ClientSystemConfigurationMessage(config)
  }
}