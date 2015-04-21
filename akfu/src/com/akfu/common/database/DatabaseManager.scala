package com.akfu.common.database

import net.fwbrasil.activate.ActivateContext
import net.fwbrasil.activate.storage.relational.PooledJdbcRelationalStorage
import net.fwbrasil.activate.storage.relational.idiom.mySqlDialect
import net.fwbrasil.activate.entity.Entity
import java.util.Date
import net.fwbrasil.activate.entity.EntityWithCustomID
import com.akfu.common.account.AccountInformations
import net.fwbrasil.activate.entity.id.SequencedIdGenerator
import net.fwbrasil.activate.sequence.LongSequenceEntity
import net.fwbrasil.activate.sequence.Sequence

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

@Alias("player")
sealed class CharacterInfo(
    val accountId: Long,
    var name: String,
    var breed: Int,
    var sex: Int,
    var skinColor: Int,
    var hairColor: Int,
    var pupilColor: Int,
    var skinFactor: Int,
    var hairFactor: Int,
    var clothIndex: Int,
    var faceIndex: Int,
    var level: Int,
    var experience: Long,
    var title: Int,
    var instanceX: Int,
    var instanceY: Int,
    var instanceZ: Int,
    var direction: Int,
    var instanceId: Int) extends EntityWithGeneratedID[Long] { 
  
  def save(process: () => Unit) = transactional { process() }  
}

class CharacterIdGenerator 
extends SequencedIdGenerator[CharacterInfo](LongSequenceEntity("characterSequence", 1))
 
object DatabaseManager {
    
  def main(args: Array[String]): Unit = {
    transactional {
      new CharacterInfo(0, "Smarken", 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0)
    }
  }
  
  def getAccount(id: Long) = transactional { byId[Account](id) }
  def getAccount(name: String) = transactional { select[Account] where(_.name :== name) headOption } 
  def getAccountByNick(nick: String) = transactional { select[Account] where(_.pseudo :== nick) headOption }
  
  def getCharacters(accountId: Long) = transactional { select[CharacterInfo] where(_.accountId :== accountId) }
  def getCharacter(id: Long) = transactional { byId[CharacterInfo](id) }
  def getCharacter(name: String) = transactional { select[CharacterInfo] where(_.name :== name) headOption } 
  
  def createCharacter(
    accountId: Long,
    name: String,
    breed: Int,
    sex: Int,
    skinColor: Int,
    hairColor: Int,
    pupilColor: Int,
    skinFactor: Int,
    hairFactor: Int,
    clothIndex: Int,
    faceIndex: Int,
    level: Int,
    experience: Long,
    title: Int,
    instanceX: Int,
    instanceY: Int,
    instanceZ: Int,
    direction: Int,
    instanceId: Int) = {    
    transactional {
      new CharacterInfo(
          accountId, 
          name, 
          breed, 
          sex, 
          skinColor, 
          hairColor, 
          pupilColor, 
          skinFactor, 
          hairFactor, 
          clothIndex, 
          faceIndex, 
          level, 
          experience, 
          title, 
          instanceX, 
          instanceY, 
          instanceZ, 
          direction, 
          instanceId)
    }
  }
}