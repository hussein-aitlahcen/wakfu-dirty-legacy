package com.akfu.world.game.entity

import com.ankamagames.wakfu.common.datas.BasicCharacterInfo
import com.ankamagames.wakfu.common.datas.CharacterSerializer
import com.ankamagames.wakfu.common.datas.PlayerCharacterSerializer
import java.util.Collections
import com.ankamagames.wakfu.common.datas.Breed.Breed
import com.ankamagames.wakfu.common.game.fighter.BreedCharacteristicManager
import com.ankamagames.wakfu.common.datas.Breed.AvatarBreedConstants
import com.ankamagames.wakfu.common.datas.Breed.AvatarBreed
import com.akfu.common.database.CharacterInfo
import com.akfu.common.maths.MathHelper
import com.ankamagames.wakfu.common.game.nation.impl.GameCitizenComportment
import com.akfu.world.game.nation.PlayerCharacterComportment
import com.akfu.world.game.nation.PlayerCharacterComportment
import com.ankamagames.framework.kernel.core.maths.Direction8
import com.ankamagames.wakfu.common.datas.CharacterInfoPart
import com.ankamagames.wakfu.common.datas.CharacterSerializedLocalGuildInfo

class PlayerCharacter(character: CharacterInfo) extends AbstractPlayerCharacter  {
          
  setSkinColorIndex(MathHelper.ensureByte(character skinColor))
  setHairColorIndex(MathHelper.ensureByte(character hairColor))
  setPupilColorIndex(MathHelper.ensureByte(character pupilColor))
  setSkinColorFactor(MathHelper.ensureByte(character skinFactor))
  setHairColorFactor(MathHelper.ensureByte(character hairFactor))
  setClothIndex(MathHelper.ensureByte(character clothIndex))
  setFaceIndex(MathHelper.ensureByte(character faceIndex))
  setTitle(MathHelper.ensureShort(character title))
  setName(character name)
  setPosition(character instanceX, character instanceY, MathHelper.ensureShort(character instanceZ))
  setId(character id)
  setInstanceId( MathHelper.ensureShort(character instanceId))
  setHavenWorldId(66)
  setType(0)
  setOwnerId(character accountId)
  setBreed(AvatarBreed getBreedFromId(character breed))
      
  initializeSerializer()
  
  m_log info "character initialized"
          
  override def initializeSerializer() {
    super.initializeSerializer()
  }
    
  def initialize() = ???  
  def addKamas(x$1: Int): Int = ???  
  def defaultBreed(): com.ankamagames.wakfu.common.datas.Breed.Breed = AvatarBreed.COMMON
  def defaultCharacterType(): Byte = -1
  def getBreedManager() = null
  def getControlled(x$1: Long) = this
  def getControlled() = Collections.emptyList()
  def getCurrentTerritoryId(): Int = -1
  def getCurrentTerritoryNationId(): Int = -1
  def getItemManager()  = ???
  def getItemSetManager() = ???
  def getKamasCount(): Int = ???
  def getOriginalController() = this
  def getSkillCharacteristics(): com.ankamagames.wakfu.common.game.characteristics.skill.SkillCharacteristics = ???
  def getWakfuGaugeValue(): Float = ???
  def initialiseCharacteristicsToBaseValue(): Unit = ??? 
  def onControllerEvent(x$1: Int,x$2: Any): Unit = ???
  def returnDefaultAIControl(): Boolean = ???
  def returnToOriginalController(): Unit = ???
  def setFight(x$1: Int): Unit = ???
  def substractKamas(x$1: Int): Int = ???
  def summonMonster(x$1: Long,x$2: com.ankamagames.framework.kernel.core.maths.Point3,x$3: Short,x$4: com.ankamagames.wakfu.common.datas.specific.BasicInvocationCharacteristics,x$5: Boolean,x$6: com.ankamagames.wakfu.common.datas.specific.PropertyManager[com.ankamagames.wakfu.common.game.fighter.FightPropertyType]): com.ankamagames.wakfu.common.datas.BasicCharacterInfo = ???
  
  // Members declared in com.ankamagames.wakfu.common.game.fight.BasicFighter
  def getCurrentFight() = 
    null
    
  def onSpecialFighterEvent(x$1: com.ankamagames.baseImpl.common.clientAndServer.game.fight.SpecialEvent) {
    
  }
  
  // Members declared in com.ankamagames.wakfu.common.datas.specific.Carrier
  def getController() = 
    this
  
  // Members declared in com.ankamagames.wakfu.common.datas.CriterionUser
  def isOnFight() = 
    super.isInPlay()
}