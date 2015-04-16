package com.akfu.common.database

import net.fwbrasil.activate.ActivateContext
import net.fwbrasil.activate.storage.relational.PooledJdbcRelationalStorage
import net.fwbrasil.activate.storage.relational.idiom.mySqlDialect
import net.fwbrasil.activate.entity.Entity
import java.util.Date
import net.fwbrasil.activate.entity.EntityWithCustomID
import com.akfu.common.account.AccountInformations

object mysqlContext extends ActivateContext {
    val storage = new PooledJdbcRelationalStorage {
        val jdbcDriver = "com.mysql.jdbc.Driver"
        val user = Some("root")
        val password = Some("")
        val url = "jdbc:mysql://127.0.0.1/akfu"
        val dialect = mySqlDialect
    }
}

import mysqlContext._

@Alias("account")
sealed class Account(
    val id: Long,
    val name: String, 
    val pseudo: String,
    val password: String,
    val mail: String,
    val adminLevel: Int,
    val creation_date: Date,
    var last_connection: Date,    
    var last_ip: String,
    var online: Boolean,
    var banned_until: Date) extends EntityWithCustomID[Long] {
    
  def getInformations() = new AccountInformations(this)
  
  def setOnline(value: Boolean) = transactional { online = value }
  def setLastConnection() = transactional { last_connection = new Date }
  def setLastIp(ip: String) = transactional { last_ip = ip }
  def setBannedDuration(until: Date) = transactional { banned_until = until }
}

object DatabaseManager {
    
  def getAccount(id: Long) = transactional { byId[Account](id) }.headOption
  def getAccount(name: String) = transactional { select[Account].where(_.name :== name) }.headOption
  def getAccountByNick(nick: String) = transactional { select[Account].where(_.pseudo :== nick).headOption }
  
}