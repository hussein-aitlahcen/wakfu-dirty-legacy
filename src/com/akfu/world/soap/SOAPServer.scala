package com.akfu.world.soap

import org.slf4j.LoggerFactory
import org.scalatra.ScalatraServlet
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.{DefaultServlet, ServletContextHandler}
import org.eclipse.jetty.webapp.WebAppContext
import org.scalatra.servlet.ScalatraListener
import org.eclipse.jetty.servlet.ServletHolder

object SOAPServer extends ScalatraServlet {
  
  final val URL = "http://127.0.0.1:8080/akfu/gameservice"
  private val log = LoggerFactory.getLogger(SOAPServer.getClass)
  
  post("akfu/gameservice") {
    log info "!!! SOAPSERVER REQUEST !!!"
  }  
         
  get("akfu/gameservice/*") {
    log info "!!! SOAPSERVER REQUEST !!!"
  }  
             
  final def initialize() {
    val server = new Server(8080)
    val context = new WebAppContext()
      context.setContextPath("/")
      context.addServlet(new ServletHolder(SOAPServer), "/")
      server.setHandler(context)
      server.start
    log info s"SOAPServer bound"    
  }
}