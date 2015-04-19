package com.akfu.world.game.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.akfu.world.game.nation.PlayerCharacterComportment;
import com.ankamagames.wakfu.common.account.WakfuAccountInformationHandler;
import com.ankamagames.wakfu.common.account.antiAddiction.AntiAddictionLevel;
import com.ankamagames.wakfu.common.account.subscription.SubscriptionLevel;
import com.ankamagames.wakfu.common.datas.*;
import com.ankamagames.wakfu.common.game.characteristics.skill.SkillCharacteristics;
import com.ankamagames.wakfu.common.game.characteristics.skill.SkillCharacteristicsForPlayer;
import com.ankamagames.wakfu.common.game.fighter.FighterCharacteristicManager;
import com.ankamagames.wakfu.common.game.fighter.FighterCharacteristicType;
import com.ankamagames.wakfu.common.game.nation.CitizenComportment;
import com.ankamagames.wakfu.common.game.nation.Nation;
import com.ankamagames.wakfu.common.game.wakfu.WakfuGauge;
import com.ankamagames.wakfu.common.game.xp.BonusPointCharacteristics;
import com.ankamagames.wakfu.common.rawData.RawStateRunningEffects;

abstract class AbstractPlayerCharacter extends BasicCharacterInfo {

	protected final Logger m_log = LoggerFactory.getLogger(AbstractPlayerCharacter.class);
    protected final PlayerCharacterSerializer m_serializer;
    protected byte m_skinColorIndex;
    protected byte m_skinColorFactor;
    protected byte m_hairColorIndex;
    protected byte m_hairColorFactor;
    protected byte m_pupilColorIndex;
    protected byte m_clothIndex;
    protected byte m_faceIndex;
    protected short m_title;
    protected int m_nationId;
    protected long m_experience;
    protected int m_havenWorldId;
    //protected BonusPointCharacteristics m_bonusPointCharacteristics;
    protected WakfuGauge m_wakfuGauge;
    public boolean m_initialized;
    protected RawStateRunningEffects m_stateREToUnserializeAtInit;
    private SkillCharacteristics m_skillCharacteristics;
    private WakfuAccountInformationHandler m_accountInformationHandler;
    private short m_level;
       
    public AbstractPlayerCharacter() { 
    	m_serializer = new PlayerCharacterSerializer();
    	m_characteristics = new FighterCharacteristicManager();
    	m_accountInformationHandler = new WakfuAccountInformationHandler();
    	m_accountInformationHandler.setSubscriptionLevel(SubscriptionLevel.EU_FREE);
    	m_accountInformationHandler.setAntiAddictionLevel(AntiAddictionLevel.UN_ACTIVATED);
    	m_skillCharacteristics = new SkillCharacteristicsForPlayer();
    	m_wakfuGauge = new WakfuGauge();
    	//m_bonusPointCharacteristics = BonusPointCharacteristics.checkOut();
    }
    
    public void setHavenWorldId(int id) {
    	m_havenWorldId = id;
    }
    
    public void setSkinColorIndex(byte color) {
    	m_skinColorIndex = color;
    } 
    
    public void setHairColorIndex(byte color) {
    	m_hairColorIndex = color;
    } 
    
    public void setPupilColorIndex(byte color) {
    	m_pupilColorIndex = color;
    } 
    
    public void setSkinColorFactor(byte factor) {
    	m_skinColorFactor = factor;
    } 
    
    public void setHairColorFactor(byte factor) {
    	m_hairColorFactor = factor;
    } 
    
    public void setClothIndex(byte cloth) {
    	m_clothIndex = cloth;
    } 
    
    public void setFaceIndex(byte face) {    	
    	m_faceIndex = face;
    } 
    
    public void setTitle(short title) {
    	m_title = title;
    }

    public CharacterSerializer getSerializer() {
    	return m_serializer;
    }
    
    public void initializeSerializer() {
    	super.initializeSerializer();
    	new PlayerCharacterPartAppearance(this.m_serializer.getAppearancePart());
        new PlayerCharacterPartEquipmentAppearance(this.m_serializer.getEquipmentAppearancePart());
        new PlayerCharacterPartHp(this.m_serializer.getHpPart());
        new PlayerCharacterPartXp(this.m_serializer.getXpPart());
        new PlayerCharacterPartXpCharacteristics(this.m_serializer.getXpCharacteristicsPart());
        new PlayerCharacterPartCreationData(this.m_serializer.getCreationDataPart());
        new PlayerCharacterPartRunningEffects(this.m_serializer.getRunningEffectsPart());
        new PlayerCharacterPartCurrentMovementPath(this.m_serializer.getCurrentMovementPathPart());
        new PlayerCharacterPartOccupation(this.m_serializer.getOccupationPart());
        new PlayerCharacterPartGuildRemoteInfo(this.m_serializer.getGuildRemoteInfoPart());
        new PlayerCharacterPartNationId(this.m_serializer.getNationIdPart());
        new PlayerCharacterPartCharacterListNationId(this.m_serializer.getCharacterListNationIdPart());
        new PlayerCharacterPartNationSynchro(this.m_serializer.getNationSynchroPart());
        new PlayerCharacterPartNationPvp(this.m_serializer.getNationPvpPart());
        new PlayerCharacterPartNationCitizenScore(this.m_serializer.getCitizenPart());
        new PlayerCharacterPartSocialStates(this.m_serializer.getSocialStatesPart());
        new PlayerCharacterPartPasseportInfo(this.m_serializer.getPasseportInfoPart());
        new PlayerCharacterPartRemoteAccountInformation(this.m_serializer.getRemoteAccountInformationPart());
        new PlayerCharacterPartPet(this.m_serializer.getPetPart());
        new PlayerCharacterPartVisibility(this.m_serializer.getVisibilityPart());
        new PlayerCharacterPartCharacteristics(this.m_serializer.getPublicCharacteristicsPart());
        new PlayerCharacterPartAchievements(this.m_serializer.getAchievementsPart());
        new LocalPlayerCharacterPartBags(this.m_serializer.getBagsPart());
        new LocalPlayerCharacterPartBreedSpecific(this.m_serializer.getBreedSpecificPart());
        new LocalPlayerCharacterPartDimensionalBagForClient(this.m_serializer.getDimensionalBagForLocalClientPart());
        new LocalPlayerCharacterPartChallenges(this.m_serializer.getChallengesPart());
        new LocalPlayerCharacterPartShortcutInventories(this.m_serializer.getShortcutInventoriesPart());
        new LocalPlayerCharacterPartEmoteInventory(this.m_serializer.getEmoteInventoryPart());
        new LocalPlayerCharacterPartLandMarkInventory(this.m_serializer.getLandMarkInventoryPart());
        new LocalPlayerCharacterPartDiscoveredItems(this.m_serializer.getDiscoveredItemsPart());
        new LocalPlayerCharacterPartSkillInventory(this.m_serializer.getSkillInventoryPart());
        new LocalPlayerCharacterPartCraft(this.m_serializer.getCraftPart());
        new LocalPlayerCharacterPartAptitudeInventory(this.m_serializer.getAptitudeInventoryPart());
        new LocalPlayerCharacterPartAptitudeBonusInventory(this.m_serializer.getAptitudeBonusInventoryPart());
        new LocalPlayerCharacterPartSpellInventory(this.m_serializer.getSpellInventoryPart());
        new LocalPlayerCharacterPartEquipmentInventory(this.m_serializer.getEquipmentInventoryPart());
        new LocalPlayerCharacterPartTitle(this.m_serializer.getTitlePart());
        new LocalPlayerCharacterPartAccountInformation(this.m_serializer.getAccountInformationPart());
        new LocalPlayerCharacterPartDimensionalBagViewsInventory(this.m_serializer.getDimensionalBagViewInventoryPart());
        new LocalPlayerCharacterPartInventories(this.m_serializer.getInventoriesPart());
        new LocalPlayerCharacterPartGuildLocalInfo(this.m_serializer.getGuildLocalInfoPart());
        new LocalPlayerCharacterPartLocks(this.m_serializer.getLockClientPart());
        new PlayerCharacterPartPersonalEffects(this.m_serializer.getPersonalEffectsPart());
        new LocalPlayerCharacterPartAntiAddiction(this.m_serializer.getAntiAddictionPart());
        new PlayerCharacterPartNationPvpMoney(this.m_serializer.getNationPvpMoneyPart());
    }

    public CitizenComportment createCitizenComportment() { 
    	return new PlayerCharacterComportment(this);
    }
    
 	private final class PlayerCharacterPartAppearance extends CharacterInfoPart
    {
        private final CharacterSerializedAppearance m_part;
        
        PlayerCharacterPartAppearance(final CharacterSerializedAppearance part) {
            super();
            this.m_part = part;
            this.m_part.getBinarPart().setDataSource(this);
        }
        
        public void updateToSerializedPart() {
            this.m_part.sex = AbstractPlayerCharacter.this.m_sex;
            this.m_part.skinColorIndex = AbstractPlayerCharacter.this.m_skinColorIndex;
            this.m_part.skinColorFactor = AbstractPlayerCharacter.this.m_skinColorFactor;
            this.m_part.hairColorIndex = AbstractPlayerCharacter.this.m_hairColorIndex;
            this.m_part.hairColorFactor = AbstractPlayerCharacter.this.m_hairColorFactor;
            this.m_part.pupilColorIndex = AbstractPlayerCharacter.this.m_pupilColorIndex;
            this.m_part.clothIndex = AbstractPlayerCharacter.this.m_clothIndex;
            this.m_part.faceIndex = AbstractPlayerCharacter.this.m_faceIndex;
            this.m_part.currentTitle = AbstractPlayerCharacter.this.m_title;
        }
        
        public void onDataChanged() {
        }
    }
 	
    private final class PlayerCharacterPartEquipmentAppearance extends CharacterInfoPart
    {
        private final CharacterSerializedEquipmentAppearance m_part;
        
        PlayerCharacterPartEquipmentAppearance(final CharacterSerializedEquipmentAppearance part) {
            super();
            this.m_part = part;
            this.m_part.getBinarPart().setDataSource(this);
        }
        
        public void updateToSerializedPart() {
            this.m_part.content.clear();
//	            for (final EquipmentPosition pos : EquipmentPosition.values()) {
//	                final Item item = ((ArrayInventoryWithoutCheck<Item, R>)AbstractPlayerCharacter.this.getEquipmentInventory()).getFromPosition(pos.m_id);
//	                if (item != null && item.isActive()) {
//	                    final CharacterSerializedEquipmentAppearance.EquipmentAppearance equipmentAppearance = new CharacterSerializedEquipmentAppearance.EquipmentAppearance();
//	                    equipmentAppearance.position = pos.m_id;
//	                    equipmentAppearance.referenceId = item.getReferenceId();
//	                    this.m_part.content.add(equipmentAppearance);
//	                }
//	            }
        }
        
        public void onDataChanged() {
        }
    }
    
    private final class PlayerCharacterPartHp extends CharacterInfoPart
    {
        private final CharacterSerializedHp m_part;
        
        PlayerCharacterPartHp(final CharacterSerializedHp part) {
            super();
            this.m_part = part;
            this.m_part.getBinarPart().setDataSource(this);
        }
        
        public void updateToSerializedPart() {
            this.m_part.hp = AbstractPlayerCharacter.this.getCharacteristicValue(FighterCharacteristicType.HP);
        }
        
        public void onDataChanged() {
        }
    }
	    
    private final class PlayerCharacterPartXpCharacteristics extends CharacterInfoPart
    {
        private final CharacterSerializedXpCharacteristics m_part;
        
        PlayerCharacterPartXpCharacteristics(final CharacterSerializedXpCharacteristics part) {
            super();
            this.m_part = part;
            this.m_part.getBinarPart().setDataSource(this);
        }
        
        public void updateToSerializedPart() {
        	m_part.wakfuGauge = m_wakfuGauge.getActionValue();
        	//getBonusPointCharacteristics().toRaw(m_part.bonusPointCharacteristics);
        }
        
        public void onDataChanged() {
        }
    }
    
	    private final class PlayerCharacterPartXp extends CharacterInfoPart
	    {
	        private final CharacterSerializedXp m_part;
	        
	        PlayerCharacterPartXp(final CharacterSerializedXp part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        public void updateToSerializedPart() {
	            m_part.xp = m_experience;
	        }
	        
	        public void onDataChanged() {
	        }
	    }
	    
	    private final class PlayerCharacterPartCreationData extends CharacterInfoPart
	    {
	        private final CharacterSerializedCreationData m_part;
	        
	        PlayerCharacterPartCreationData(final CharacterSerializedCreationData part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        public void updateToSerializedPart() {
	            if (this.m_part.creationData == null) {
	                this.m_part.creationData = new CharacterSerializedCreationData.CreationData();
	            }
	        }
	        
	        public void onDataChanged() {
	        }
	    }
	    
	    private final class PlayerCharacterPartRunningEffects extends CharacterInfoPart
	    {
	        private final CharacterSerializedRunningEffects m_part;
	        
	        PlayerCharacterPartRunningEffects(final CharacterSerializedRunningEffects part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        public void updateToSerializedPart() {
	        	
	        }
	        
	        public void onDataChanged() {
//	            AbstractPlayerCharacter.this.getRunningEffectManager().clear();
//	            if (this.m_part.outFightData != null) {
//	                if (AbstractPlayerCharacter.this.m_initialized) {
//	                    AbstractPlayerCharacter.this.getRunningEffectManager().fromRawStateRunningEffects(this.m_part.outFightData.stateRunningEffects, AbstractPlayerCharacter.this.m_ownContext, AbstractPlayerCharacter.this);
//	                }
//	                else {
//	                    AbstractPlayerCharacter.this.setStateREToUnserializeAtInit(this.m_part.outFightData.stateRunningEffects);
//	                }
//	            }
	        }
	    }
	    
	    private final class PlayerCharacterPartCurrentMovementPath extends CharacterInfoPart
	    {
	        private final CharacterSerializedCurrentMovementPath m_part;
	        
	        PlayerCharacterPartCurrentMovementPath(final CharacterSerializedCurrentMovementPath part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        public void updateToSerializedPart() {
	        }
	        
           public void onDataChanged() {
//	            if (this.m_part.currentPath != null) {
//	                final ByteBuffer buffer = ByteBuffer.wrap(this.m_part.currentPath.encodedPath);
//	                final Direction8Path path = Direction8Path.decodeFromBuffer(buffer);
//	                if (path != null && AbstractPlayerCharacter.this.getActor() != null) {
//	                    AbstractPlayerCharacter.this.getActor().updateActorPath(path);
//	                }
//	            }
        	   
	        }
	    }
	    
	    private final class PlayerCharacterPartOccupation extends CharacterInfoPart
	    {
	        private final CharacterSerializedOccupation m_part;
	        
	        PlayerCharacterPartOccupation(final CharacterSerializedOccupation part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        public void updateToSerializedPart() {
	        }
	        
	        public void onDataChanged() {
//	            if (this.m_part.occupation != null) {
//	                final short occupationId = this.m_part.occupation.occupationId;
//	                switch (occupationId) {
//	                    case 4: {
//	                        final DeadOccupation deadOccupation = new DeadOccupation(AbstractPlayerCharacter.this);
//	                        deadOccupation.build(this.m_part.occupation.occupationData);
//	                        deadOccupation.begin();
//	                        break;
//	                    }
//	                    case 20: {
//	                        final EmoteOccupation emoteOccupation = new EmoteOccupation(AbstractPlayerCharacter.this);
//	                        emoteOccupation.build(this.m_part.occupation.occupationData);
//	                        emoteOccupation.begin();
//	                        break;
//	                    }
//	                    case 1: {
//	                        final BasicOccupation restOccupation = new RestOccupation(AbstractPlayerCharacter.this);
//	                        restOccupation.begin();
//	                        break;
//	                    }
//	                    case 14: {
//	                        final BasicOccupation rideOccupation = new RideOccupation(AbstractPlayerCharacter.this);
//	                        rideOccupation.begin();
//	                        break;
//	                    }
//	                    case 16: {
//	                        final SitOccupation sitOccupation = new SitOccupation(AbstractPlayerCharacter.this);
//	                        sitOccupation.build(this.m_part.occupation.occupationData);
//	                        AbstractPlayerCharacter.this.setCurrentOccupation(sitOccupation);
//	                        break;
//	                    }
//	                    default: {
//	                        AbstractPlayerCharacter.m_logger.error((Object)("Occupation inconnue : id=" + occupationId));
//	                        break;
//	                    }
//	                }
	            //}
	        }
	    }
	    
	    private final class PlayerCharacterPartGuildRemoteInfo extends CharacterInfoPart
	    {
	        private final CharacterSerializedRemoteGuildInfo m_part;
	        
	        PlayerCharacterPartGuildRemoteInfo(final CharacterSerializedRemoteGuildInfo part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        public void updateToSerializedPart() {
	        	
	        }
	        
	        public void onDataChanged() {
//	            final GuildRemoteInformationHandler handler = (GuildRemoteInformationHandler)AbstractPlayerCharacter.this.m_guildHandler;
//	            handler.setGuildId(this.m_part.guildId);
//	            handler.setBlazon(this.m_part.blazon);
//	            handler.setName(this.m_part.guildName);
//	            handler.setLevel(this.m_part.level);
//	            handler.setNationId(this.m_part.nationId);
	        }
	    }
	    
	    private final class PlayerCharacterPartNationId extends CharacterInfoPart
	    {
	        private final CharacterSerializedNationId m_part;
	        
	        PlayerCharacterPartNationId(final CharacterSerializedNationId part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        public void updateToSerializedPart() {
	        }
	        
	        public void onDataChanged() {
//	            if (AbstractPlayerCharacter.this.getCitizenComportment().getNationId() != this.m_part.nationId) {
//	                final Nation newNation = NationManager.INSTANCE.getNationById(this.m_part.nationId);
//	                if (newNation != null) {
//	                    newNation.requestAddCitizen(AbstractPlayerCharacter.this);
//	                }
//	                else {
//	                    AbstractPlayerCharacter.m_logger.error((Object)("[NATION] On essaye d'ajouter le joueur " + AbstractPlayerCharacter.this.getId() + " \u00ef¿½ une nation inconnue du manager, nationId : " + this.m_part.nationId));
//	                }
//	            }
	        }
	    }
	    
	    private final class PlayerCharacterPartCharacterListNationId extends CharacterInfoPart
	    {
	        private final CharacterSerializedNationId m_part;
	        
	        PlayerCharacterPartCharacterListNationId(final CharacterSerializedNationId part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        public void updateToSerializedPart() {
	        	m_part.nationId = Nation.AMAKNA;
	        }
	        
	        public void onDataChanged() {
//	            LocalCharacterInfosManager.getInstance().addNationInformation(AbstractPlayerCharacter.this, this.m_part.nationId);
	        }
	    }
	    
	    private final class PlayerCharacterPartNationSynchro extends CharacterInfoPart
	    {
	        private final CharacterSerializedNationSynchro m_part;
	        
	        PlayerCharacterPartNationSynchro(final CharacterSerializedNationSynchro part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        public void updateToSerializedPart() {
	        }
	        
	        public void onDataChanged() {
//	            final ClientCitizenComportment comportment = (ClientCitizenComportment)AbstractPlayerCharacter.this.getCitizenComportment();
//	            comportment.setRank(NationRank.getById(this.m_part.rank));
//	            comportment.setJobs(NationJob.fromLong(this.m_part.jobs));
//	            comportment.setVoteDate(GameDate.fromLong(this.m_part.voteDate));
//	            comportment.setGovernmentOpinion(GovernmentOpinion.fromId(this.m_part.governmentOpinion));
//	            comportment.setCandidate(this.m_part.isCandidate);
//	            comportment.setPvpState(NationPvpState.getFromId(this.m_part.pvpState));
//	            comportment.setPvpDate(GameDate.fromLong(this.m_part.pvpDate));
//	            comportment.setPvpRank(NationPvpRanks.getById(this.m_part.pvpRank));
//	            comportment.updatePvp(false);
	        }
	    }
	    
	    private final class PlayerCharacterPartNationPvp extends CharacterInfoPart
	    {
	        private final CharacterSerializedNationPvp m_part;
	        
	        PlayerCharacterPartNationPvp(final CharacterSerializedNationPvp part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        public void updateToSerializedPart() {
	        	CitizenComportment comportment = getCitizenComportment();
//	        	m_part.pvpState =  comportment.getPvpState().getId();
//	        	m_part.pvpDate = comportment.getPvpDate().toLong();
//	        	m_part.pvpRank = comportment.getPvpRank().getId();
//	        	m_part.dailyPvpMoneyAmount = comportment.getDailyPvpMoneyAmount();
//	        	m_part.pvpMoneyAmount = comportment.getPvpMoneyAmount();
	        }
	        
	        public void onDataChanged() {
	        }
	    }
	    
	    private final class PlayerCharacterPartNationCitizenScore extends CharacterInfoPart
	    {
	        private final CharacterSerializedNationCitizenScore m_part;
//	        private final TIntArrayList m_citizenPointsNations;
	        
	        PlayerCharacterPartNationCitizenScore(final CharacterSerializedNationCitizenScore part) {
	            super();
//	            this.m_citizenPointsNations = new TIntArrayList();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        public void updateToSerializedPart() {
//	            throw new UnsupportedOperationException("[NATION] La Part NationEnemy ne devrait pas \u00ef¿½tre s\u00ef¿½rialis\u00ef¿½e par le client.");
	        }
	        
	        
	        public void onDataChanged() {
//	            final ClientCitizenComportment comportment = (ClientCitizenComportment)AbstractPlayerCharacter.this.getCitizenComportment();
//	            final ArrayList<CharacterSerializedNationCitizenScore.NationCitizenScoreInfo> nations = this.m_part.nationCitizenScores;
//	            this.m_citizenPointsNations.add(comportment.getHasCitizenScoreNations());
//	            for (int i = 0, size = nations.size(); i < size; ++i) {
//	                final CharacterSerializedNationCitizenScore.NationCitizenScoreInfo nationEnemy = nations.get(i);
//	                final int nationId = nationEnemy.nationId;
//	                final int value = nationEnemy.citizenScore;
//	                final int old = comportment.setCitizenScore(nationId, value);
//	                TroveUtils.removeFirstValue(this.m_citizenPointsNations, nationId);
//	                if (old != value) {
//	                    comportment.onCitizenScoreChanged(nationId, old);
//	                }
//	            }
//	            for (int i = 0, size = this.m_citizenPointsNations.size(); i < size; ++i) {
//	                final int nationId2 = this.m_citizenPointsNations.get(i);
//	                final int old2 = comportment.setCitizenScore(nationId2, 0);
//	                comportment.onCitizenScoreChanged(nationId2, old2);
//	            }
//	            this.m_citizenPointsNations.clear();
//	            final ArrayList<CharacterSerializedNationCitizenScore.OffendedNations> offendedNations = this.m_part.offendedNations;
//	            final TIntHash pastOffendedNations = new TIntHashSet(comportment.getOffendedNations().toArray());
//	            comportment.resetOffendedNations();
//	            for (int j = 0, n = offendedNations.size(); j < n; ++j) {
//	                final CharacterSerializedNationCitizenScore.OffendedNations offendedNation = offendedNations.get(j);
//	                final int nationId3 = offendedNation.offendedNationId;
//	                if (!pastOffendedNations.contains(nationId3)) {
//	                    this.notification(nationId3);
//	                }
//	                comportment.addOffendedNation(NationManager.INSTANCE.getNationById(nationId3));
//	            }
//	            comportment.updateAppearance();
	        }	        
	    }
	    
	    private final class PlayerCharacterPartPasseportInfo extends CharacterInfoPart
	    {
	        private final CharacterSerializedPasseportInfo m_part;
	        
	        PlayerCharacterPartPasseportInfo(final CharacterSerializedPasseportInfo part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        public void updateToSerializedPart() {
//	            throw new UnsupportedOperationException("[NATION] La part ne devrait pas \u00ef¿½tre s\u00ef¿½rialis\u00ef¿½e par le client.");
	        }
	        
	        
	        public void onDataChanged() {
//	            AbstractPlayerCharacter.this.getCitizenComportment().setPasseportActive(this.m_part.isPasseportActive);
	        }
	    }
	    
	    private final class PlayerCharacterPartRemoteAccountInformation extends CharacterInfoPart
	    {
	        private final CharacterSerializedRemoteAccountInformation m_part;
	        
	        PlayerCharacterPartRemoteAccountInformation(final CharacterSerializedRemoteAccountInformation part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        public void updateToSerializedPart() {
//	            throw new UnsupportedOperationException("Les informations de compte de sont pas s\u00ef¿½rialis\u00ef¿½s par le client");
	        }
	        
	        public void onDataChanged() {
//	            AbstractPlayerCharacter.this.m_accountInformationHandler.setSubscriptionLevel(SubscriptionLevel.fromId(this.m_part.subscriptionLevel));
//	            final LocalAbstractPlayerCharacter localPlayer = WakfuGameEntity.getInstance().getLocalPlayer();
//	            if (AbstractPlayerCharacter.this == localPlayer) {
//	                final LocalAccountInformations localAccount = WakfuGameEntity.getInstance().getLocalAccount();
//	                if (localAccount != null) {
//	                    localAccount.setSubscriptionLevel(this.m_part.subscriptionLevel);
//	                }
//	                AbstractPlayerCharacter.this.reloadSubscriptionState();
//	            }
//	            try {
//	                SubscriptionEmoteAndTitleLimitations.resetCurrentTitleIfNecessary(AbstractPlayerCharacter.this, AbstractPlayerCharacter.this.m_accountInformationHandler.getActiveSubscriptionLevel());
//	            }
//	            catch (Exception e) {
//	                AbstractPlayerCharacter.m_logger.error((Object)"Exception levee", (Throwable)e);
//	            }
//	            AbstractPlayerCharacter.this.m_accountInformationHandler.getAdditionalRights().clear();
//	            if (this.m_part.additionalRights == null) {
//	                return;
//	            }
//	            for (int i = 0; i < this.m_part.additionalRights.length; ++i) {
//	                final int right = this.m_part.additionalRights[i];
//	                AbstractPlayerCharacter.this.m_accountInformationHandler.addSubscriptionRight(SubscriptionRight.fromId(right));
//	            }
	        }
	    }
	    
	    private final class PlayerCharacterPartSocialStates extends CharacterInfoPart
	    {
	        private final CharacterSerializedSocialStates m_part;
	        
	        PlayerCharacterPartSocialStates(final CharacterSerializedSocialStates part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        public void updateToSerializedPart() {
//	            throw new UnsupportedOperationException("[SOCIAL] La Part SocialStates ne devrait pas \u00ef¿½tre s\u00ef¿½rialis\u00ef¿½e par le client.");
	        }
	        
	        public void onDataChanged() {
//	            AbstractPlayerCharacter.this.m_afkStateActive = this.m_part.afkState;
//	            AbstractPlayerCharacter.this.m_dndStateActive = this.m_part.dndState;
	        }
	    }
	    
	    private final class PlayerCharacterPartAchievements extends CharacterInfoPart
	    {
	        private final CharacterSerializedAchievements m_part;
	        
	        private PlayerCharacterPartAchievements(final CharacterSerializedAchievements part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        public void updateToSerializedPart() {
	        	
	        }
	        
	        public void onDataChanged() {
//	            final ClientAchievementsContext context = AchievementsModel.INSTANCE.createContext();
//	            context.unserialize(this.m_part.serializedAchievementsContext);
//	            context.setEventListener(new LocalAbstractPlayerCharacterAchievementsListener(AbstractPlayerCharacter.this.m_id, context));
//	            AchievementContextManager.INSTANCE.registerContext(AbstractPlayerCharacter.this.getId(), context);
//	            this.m_part.serializedAchievementsContext = null;
	        }
	    }
	    
	    private final class PlayerCharacterPartPet extends CharacterInfoPart
	    {
	        private final CharacterSerializedPet m_part;
	        
	        PlayerCharacterPartPet(final CharacterSerializedPet part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        public void updateToSerializedPart() {
//	            throw new UnsupportedOperationException("Pas de serialisation dans le client (Server->Client only)");
	        }
	        
	        public void onDataChanged() {
//	            if (this.m_part.mount != null) {
//	                AbstractPlayerCharacter.this.createMountMobileView(this.m_part.mount.definitionId, this.m_part.mount.colorRefItemId, this.m_part.mount.equippedRefItemId, this.m_part.mount.sleepRefItemId, this.m_part.mount.health);
//	            }
//	            else {
//	                AbstractPlayerCharacter.this.removeMountMobile();
//	            }
//	            if (this.m_part.pet != null) {
//	                AbstractPlayerCharacter.this.createPetMobileView(this.m_part.pet.definitionId, this.m_part.pet.colorRefItemId, this.m_part.pet.equippedRefItemId, this.m_part.pet.sleepRefItemId, this.m_part.pet.health);
//	            }
//	            else {
//	                AbstractPlayerCharacter.this.removePetMobile();
//	            }
	        }
	    }
	    
	    private final class PlayerCharacterPartVisibility extends CharacterInfoPart
	    {
	        private final CharacterSerializedVisibility m_part;
	        
	        PlayerCharacterPartVisibility(final CharacterSerializedVisibility part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        public void updateToSerializedPart() {
//	            throw new UnsupportedOperationException("Pas de serialisation dans le client (Server->Client only)");
	        }
	        
	        public void onDataChanged() {
//	            final LocalAbstractPlayerCharacter localPlayer = WakfuGameEntity.getInstance().getLocalPlayer();
//	            if (AbstractPlayerCharacter.this == localPlayer) {
//	                if (this.m_part.visible) {
//	                    AbstractPlayerCharacter.this.getActor().setAlpha(AbstractPlayerCharacter.this.getActor().getOriginalAlpha());
//	                }
//	                else {
//	                    AbstractPlayerCharacter.this.getActor().setDesiredAlpha(0.5f);
//	                }
//	            }
//	            else {
//	                AbstractPlayerCharacter.this.getActor().setVisible(this.m_part.visible);
//	            }
	        }
	    }
	    
	    private final class PlayerCharacterPartCharacteristics extends CharacterInfoPart
	    {
	        private final CharacterSerializedCharacteristics m_part;
	        
	        PlayerCharacterPartCharacteristics(final CharacterSerializedCharacteristics part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        public void updateToSerializedPart() {
	        	getCharacteristics().toRaw(m_part.characteristics);
	        }
	        
	        public void onDataChanged() {
//	            AbstractPlayerCharacter.this.getCharacteristics().fromRaw(this.m_part.characteristics);
	        }
	    }
	    

	    private final class LocalPlayerCharacterPartDimensionalBagForClient extends CharacterInfoPart
	    {
	        private final CharacterSerializedDimensionalBagForClient m_part;
	        
	        private LocalPlayerCharacterPartDimensionalBagForClient(final CharacterSerializedDimensionalBagForClient part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        public void updateToSerializedPart() {
//	            LocalPlayerCharacter.m_logger.error((Object)"Le sac dimensionnel ne devrait pas \u00eatre s\u00e9rialis\u00e9 par le client");
	        }
	        
	        public void onDataChanged() {
//	            if (LocalPlayerCharacter.this.m_ownedDimensionalBag != null) {
//	                LocalPlayerCharacter.this.m_ownedDimensionalBag.release();
//	            }
//	            LocalPlayerCharacter.this.m_ownedDimensionalBag = new DimensionalBagView();
//	            if (!LocalPlayerCharacter.this.m_ownedDimensionalBag.fromRaw(this.m_part.bag)) {
//	                LocalPlayerCharacter.m_logger.error((Object)"Erreur durant la r\u00e9cup\u00e9ration des donn\u00e9es du sac dimensionel du joueur.");
//	            }
	        }
	        
	        
	        public String toString() {
	            return "LocalPlayerCharacterPartDimensionalBagForClient{m_part=" + this.m_part + '}';
	        }
	    }
	    
	    private final class LocalPlayerCharacterPartShortcutInventories extends CharacterInfoPart
	    {
	        private final CharacterSerializedShortcutInventories m_part;
	        
	        private LocalPlayerCharacterPartShortcutInventories(final CharacterSerializedShortcutInventories part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        public void updateToSerializedPart() {
//	            LocalPlayerCharacter.m_logger.error((Object)"Le client ne devrait pas s?rialiser les racourcis");
	        }
	        
	        public void onDataChanged() {
//	            LocalPlayerCharacter.this.m_shortcutBarManager.onDataChanged(this.m_part);
	        }
	    }
	    
	    private final class LocalPlayerCharacterPartEmoteInventory extends CharacterInfoPart
	    {
	        private final CharacterSerializedEmoteInventory m_part;
	        
	        private LocalPlayerCharacterPartEmoteInventory(final CharacterSerializedEmoteInventory part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        
	        public void updateToSerializedPart() {
//	            throw new UnsupportedOperationException("Le client ne devrait pas s\u00e9rialiser les emotes");
	        }
	        
	        
	        public void onDataChanged() {
//	            LocalPlayerCharacter.this.m_emoteHandler.fromRaw(this.m_part);
	        }
	    }
	    
	    private final class LocalPlayerCharacterPartDimensionalBagViewsInventory extends CharacterInfoPart
	    {
	        private final CharacterSerializedDimensionalBagViewInventory m_part;
	        
	        private LocalPlayerCharacterPartDimensionalBagViewsInventory(final CharacterSerializedDimensionalBagViewInventory part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        
	        public void updateToSerializedPart() {
//	            throw new UnsupportedOperationException("Le client ne devrait pas s\u00e9rialiser les customs de havre-sac");
	        }
	        
	        
	        public void onDataChanged() {
//	            LocalPlayerCharacter.this.m_personalSpaceHandler.fromRaw(this.m_part);
	        }
	    }
	    
	    private final class LocalPlayerCharacterPartInventories extends CharacterInfoPart
	    {
	        private final CharacterSerializedInventories m_part;
	        
	        private LocalPlayerCharacterPartInventories(final CharacterSerializedInventories part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        
	        public void updateToSerializedPart() {
//	            throw new UnsupportedOperationException("Pas de serialisation cliente");
	        }
	        
	        
	        public void onDataChanged() {
//	            LocalPlayerCharacter.this.m_inventoryHandler.fromRaw(this.m_part.inventoryHandler);
	        }
	        
	        
	        public String toString() {
	            return "LocalPlayerCharacterPartInventories{m_part=" + this.m_part + "} ";
	        }
	    }
	    
	    private final class LocalPlayerCharacterPartGuildLocalInfo extends CharacterInfoPart
	    {
	        private final CharacterSerializedLocalGuildInfo m_part;
	        
	        private LocalPlayerCharacterPartGuildLocalInfo(final CharacterSerializedLocalGuildInfo part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        
	        public void updateToSerializedPart() {
	        	m_part.havenWorldId = m_havenWorldId;
//	            throw new UnsupportedOperationException("Pas de serialisation de part ici.");
	        }
	        
	        
	        public void onDataChanged() {
//	            ((GuildLocalInformationHandler)LocalPlayerCharacter.this.getGuildHandler()).setSerializedGuild(this.m_part.guild);
//	            ((GuildLocalInformationHandler)LocalPlayerCharacter.this.getGuildHandler()).setHavenWorldId(this.m_part.havenWorldId);
//	            ((GuildLocalInformationHandler)LocalPlayerCharacter.this.getGuildHandler()).setModerationBonusLearningFactor(this.m_part.moderationBonusLearningFactor);
//	            LocalPlayerCharacter.this.reloadItemEffects();
	        }
	    }
	    
	    private final class LocalPlayerCharacterPartLandMarkInventory extends CharacterInfoPart
	    {
	        private final CharacterSerializedLandMarkInventory m_part;
	        
	        private LocalPlayerCharacterPartLandMarkInventory(final CharacterSerializedLandMarkInventory part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        
	        public void updateToSerializedPart() {
//	            throw new UnsupportedOperationException("Le client ne devrait pas s\u00e9rialiser les landMarks");
	        }
	        
	        
	        public void onDataChanged() {
//	            LocalPlayerCharacter.this.m_mapHandler.fromRaw(this.m_part);
	        }
	    }
	    
	    private final class LocalPlayerCharacterPartDiscoveredItems extends CharacterInfoPart
	    {
	        private final CharacterSerializedDiscoveredItemsInventory m_part;
	        
	        private LocalPlayerCharacterPartDiscoveredItems(final CharacterSerializedDiscoveredItemsInventory part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        
	        public void updateToSerializedPart() {
//	            throw new UnsupportedOperationException("Le client ne devrait pas s\u00e9rialiser les items decouverts");
	        }
	        
	        
	        public void onDataChanged() {
//	            LocalPlayerCharacter.this.m_travelHandler.fromRaw(this.m_part);
//	            LocalPlayerCharacter.this.m_respawnPointHandler.fromRaw(this.m_part);
	        }
	    }
	    
	    private final class LocalPlayerCharacterPartBreedSpecific extends CharacterInfoPart
	    {
	        private final CharacterSerializedBreedSpecific m_part;
	        
	        private LocalPlayerCharacterPartBreedSpecific(final CharacterSerializedBreedSpecific part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        
	        public void updateToSerializedPart() {
//	            LocalPlayerCharacter.m_logger.error((Object)"Les breed specifics ne devraient pas ?tre s?rialis?s par le client.");
	        }
	        
	        
	        public void onDataChanged() {
//	            if (this.m_part.osaSpecific != null) {
//	                final Symbiot symbiot = new Symbiot();
//	                LocalPlayerCharacter.this.setSymbiot(symbiot);
//	                if (symbiot.fromRawSymbiot(this.m_part.osaSpecific.symbiot)) {
//	                    for (byte index = 0; index < symbiot.size(); ++index) {
//	                        final BasicInvocationCharacteristics charac = LocalPlayerCharacter.this.m_symbioticCharacter.getSymbiot().getCreatureParametersFromIndex(index);
//	                        if (charac != null && (charac.getName() == null || charac.getName().isEmpty())) {
//	                            final String name = WakfuTranslator.getInstance().getString(7, charac.getTypeId(), new Object[0]);
//	                            charac.setName(name);
//	                            final OsamodasSymbiotRenameCreatureMessage msg = new OsamodasSymbiotRenameCreatureMessage();
//	                            msg.setCreatureIndex(index);
//	                            msg.setCreatureName(name);
//	                            WakfuGameEntity.getInstance().getNetworkEntity().sendMessage(msg);
//	                        }
//	                    }
//	                    PropertiesProvider.getInstance().firePropertyValueChanged(SymbiotView.getInstance(), SymbiotView.FIELDS);
//	                    for (final CharacterInfoPropertyEventsHandler handler : LocalPlayerCharacter.this.m_characterInfoEventsHandler) {
//	                        handler.onSymbiotChanged(LocalPlayerCharacter.this);
//	                    }
//	                }
//	                else {
//	                    LocalPlayerCharacter.m_logger.error((Object)"Erreur lors de la r?cup?ration du symbiote d?s?rialis?");
//	                }
//	            }
	        }
	    }
	    
	    private final class LocalPlayerCharacterPartSpellInventory extends CharacterInfoPart
	    {
	        private final CharacterSerializedSpellInventory m_part;
	        
	        private LocalPlayerCharacterPartSpellInventory(final CharacterSerializedSpellInventory part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        
	        public void updateToSerializedPart() {
//	            LocalPlayerCharacter.m_logger.error((Object)"Les spells ne devraient pas ?tre s?rialis?s par le client.");
	        }
	        
	        
	        public void onDataChanged() {
//	            LocalPlayerCharacter.this.m_spellInventoryManager = new SpellInventoryManager(LocalPlayerCharacter.this);
//	            LocalPlayerCharacter.this.m_spellInventoryManager.getSpellInventory().fromRaw(this.m_part.spellInventory);
//	            LocalPlayerCharacter.this.registerLockedSpellId(this.m_part.lockedSpellId);
	        }
	    }
	    
	    private final class LocalPlayerCharacterPartSkillInventory extends CharacterInfoPart
	    {
	        private final CharacterSerializedSkillInventory m_part;
	        
	        private LocalPlayerCharacterPartSkillInventory(final CharacterSerializedSkillInventory part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        
	        public void updateToSerializedPart() {
//	            LocalPlayerCharacter.m_logger.error((Object)"Les skills ne devraient pas ?tre s?rialis?s par le client.");
	        }
	        
	        
	        public void onDataChanged() {
//	            LocalPlayerCharacter.this.m_skillInventory.fromRaw(this.m_part.skillInventory);
	        }
	    }
	    
	    private final class LocalPlayerCharacterPartCraft extends CharacterInfoPart
	    {
	        private final CharacterSerializedCraft m_part;
	        
	        private LocalPlayerCharacterPartCraft(final CharacterSerializedCraft part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        
	        public void updateToSerializedPart() {
//	            throw new UnsupportedOperationException("Pas de s\u00e9rialisation dans le client");
	        }
	        
	        
	        public void onDataChanged() {
//	            LocalPlayerCharacter.this.m_craftHandler.fromRaw(this.m_part);
	        }
	    }
	    
	    private final class LocalPlayerCharacterPartBags extends CharacterInfoPart
	    {
	        private final CharacterSerializedBags m_part;
	        
	        private LocalPlayerCharacterPartBags(final CharacterSerializedBags part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        
	        public void updateToSerializedPart() {
//	            LocalPlayerCharacter.m_logger.error((Object)"Les bags ne devraient pas \u00eatre s\u00e9rialis\u00e9s par le client.");
	        }
	        
	        
	        public void onDataChanged() {
//	            for (final RawBagContainer.Bag bag : this.m_part.bagContainer.bags) {
//	                final RawBag rawBag = bag.bag;
//	                AbstractBag refBag = LocalPlayerCharacter.this.m_bags.get(rawBag.uniqueId);
//	                if (refBag == null) {
//	                    refBag = new Bag(0L, 0, BagInventoryContentChecker.INSTANCE, (short)0, LocalPlayerCharacter.this.m_bags);
//	                    if (!refBag.fromRaw(rawBag)) {
//	                        LocalPlayerCharacter.m_logger.error((Object)("Erreur lors de la r\u00e9cup\u00e9ration du bag uniqueId=" + rawBag.uniqueId + ", on ignore les bags restants"));
//	                        return;
//	                    }
//	                    LocalPlayerCharacter.this.m_bags.addContainer(refBag);
//	                    refBag.addObserver(ClientEventLocalPlayerInventoryListener.INSTANCE);
//	                }
//	                else {
//	                    refBag.destroyAll();
//	                    refBag.fromRaw(rawBag);
//	                }
//	            }
	        }
	    }
	    
	    private final class LocalPlayerCharacterPartEquipmentInventory extends CharacterInfoPart
	    {
	        private final CharacterSerializedEquipmentInventory m_part;
	        
	        private LocalPlayerCharacterPartEquipmentInventory(final CharacterSerializedEquipmentInventory part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        
	        public void updateToSerializedPart() {
//	            LocalPlayerCharacter.m_logger.error((Object)"L'\u00e9quipement ne devrait pas etre s\u00e9rialis\u00e9 par le client.");
	        }
	        
	        
	        public void onDataChanged() {
//	            LocalPlayerCharacter.this.m_equipmentInventory.addObserver(LocalPlayerCharacter.this.getActor());
//	            LocalPlayerCharacter.this.beginRefreshDisplayEquipment();
//	            LocalPlayerCharacter.this.m_equipmentInventory.fromRaw(this.m_part.equipment);
//	            LocalPlayerCharacter.this.m_equipmentInventory.addObserver(LocalPlayerCharacter.this);
//	            LocalPlayerCharacter.this.m_equipmentInventory.addObserver(ClientEventLocalPlayerInventoryListener.INSTANCE);
//	            for (final Item item : LocalPlayerCharacter.this.m_equipmentInventory) {
//	                if (item.getReferenceItem().getItemType().getLinkedPositions() != null && item.isActive()) {
//	                    for (final EquipmentPosition pos : item.getReferenceItem().getItemType().getLinkedPositions()) {
//	                        final Item placeHolder = item.getInactiveCopy();
//	                        try {
//	                            ((ArrayInventoryWithoutCheck<Item, R>)LocalPlayerCharacter.this.m_equipmentInventory).addAt(placeHolder, pos.m_id);
//	                        }
//	                        catch (Exception e) {
//	                            LocalPlayerCharacter.m_logger.fatal((Object)"On a s\u00e9rialis\u00e9 un inventaire incoh\u00e9rent.");
//	                        }
//	                    }
//	                }
//	            }
//	            LocalPlayerCharacter.this.m_cosmeticsInventoryView.refreshSelected();
//	            LocalPlayerCharacter.this.m_shortcutBarManager.setLeftAndRightHand(((ArrayInventoryWithoutCheck<Item, R>)LocalPlayerCharacter.this.m_equipmentInventory).getFromPosition(EquipmentPosition.FIRST_WEAPON.m_id), ((ArrayInventoryWithoutCheck<Item, R>)LocalPlayerCharacter.this.m_equipmentInventory).getFromPosition(EquipmentPosition.SECOND_WEAPON.getId()));
//	            LocalPlayerCharacter.this.endRefreshDisplayEquipment();
	        }
	    }
	    
	    private static final class LocalPlayerCharacterPartChallenges extends CharacterInfoPart
	    {
	        private CharacterSerializedChallenges m_part;
	        
	        private LocalPlayerCharacterPartChallenges(final CharacterSerializedChallenges part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        
	        public void updateToSerializedPart() {
//	            LocalPlayerCharacter.m_logger.error((Object)"Les challenges ne devraient pas ?tre s?rialis?s par le client.");
	        }
	        
	        
	        public void onDataChanged() {
//	            ChallengeManager.getInstance().fromRawScenarioManager(this.m_part.challenges);
	        }
	    }
	    
	    private final class LocalPlayerCharacterPartTitle extends CharacterInfoPart
	    {
	        private final CharacterSerializedTitle m_part;
	        
	        private LocalPlayerCharacterPartTitle(final CharacterSerializedTitle part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        
	        public void updateToSerializedPart() {
	        	
	        }
	        
	        
	        public void onDataChanged() {
//	            final int size = this.m_part.availableTitles.size();
//	            final short[] availableTitles = new short[size];
//	            for (int i = 0; i < size; ++i) {
//	                availableTitles[i] = this.m_part.availableTitles.get(i).availableTitle;
//	            }
//	            LocalPlayerCharacter.this.setAvailableTitles(availableTitles);
	        }
	    }
	    
	    private final class LocalPlayerCharacterPartAptitudeInventory extends CharacterInfoPart
	    {
	        private final CharacterSerializedAptitudeInventory m_part;
	        
	        private LocalPlayerCharacterPartAptitudeInventory(final CharacterSerializedAptitudeInventory part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        
	        public void updateToSerializedPart() {
//	            LocalPlayerCharacter.m_logger.error((Object)"Les aptitudes ne devraient pas \u00eatre s\u00e9rialis\u00e9es par le client.");
	        }
	        
	        
	        public void onDataChanged() {
//	            LocalPlayerCharacter.this.m_aptitudeInventory.fromRaw(this.m_part.aptitudeInventory);
//	            try {
//	                LocalPlayerCharacter.this.reloadAptitudeEffects(LocalPlayerCharacter.this.getAppropriateContext());
//	            }
//	            catch (Exception e) {
//	                LocalPlayerCharacter.m_logger.error((Object)"Exception levee", (Throwable)e);
//	            }
	        }
	    }
	    
	    private final class LocalPlayerCharacterPartAptitudeBonusInventory extends CharacterInfoPart
	    {
	        private final CharacterSerializedAptitudeBonusInventory m_part;
	        
	        private LocalPlayerCharacterPartAptitudeBonusInventory(final CharacterSerializedAptitudeBonusInventory part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        
	        public void updateToSerializedPart() {
//	            LocalPlayerCharacter.m_logger.error((Object)"Les aptitudes ne devraient pas \u00eatre s\u00e9rialis\u00e9es par le client.");
	        }
	        
	        
	        public void onDataChanged() {
//	            if (this.m_part.optional == null) {
//	                return;
//	            }
//	            LocalPlayerCharacter.this.m_aptitudeBonusInventory.fromRaw(this.m_part.optional.aptitudeInventory);
//	            LocalPlayerCharacter.this.reloadNewAptitudeEffects(LocalPlayerCharacter.this.m_ownContext);
//	            AptitudesView.INSTANCE.reset(LocalPlayerCharacter.this.m_aptitudeBonusInventory);
//	            PropertiesProvider.getInstance().firePropertyValueChanged(AptitudesView.INSTANCE, AptitudesView.FIELDS);
	        }
	    }
	    
	    private final class LocalPlayerCharacterPartLocks extends CharacterInfoPart
	    {
	        private final CharacterSerializedLocksForClient m_part;
	        
	        private LocalPlayerCharacterPartLocks(final CharacterSerializedLocksForClient part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        
	        public void updateToSerializedPart() {
//	            LocalPlayerCharacter.m_logger.error((Object)"Les Locks ne sont pas serialis\u00e9s par le client");
	        }
	        
	        
	        public void onDataChanged() {
//	            LocalPlayerCharacter.this.m_locks = new LockContext();
//	            LockManager.INSTANCE.initializeContext(LocalPlayerCharacter.this.m_locks, !LocalPlayerCharacter.this.hasSubscriptionRight(SubscriptionRight.NO_DUNGEON_DAILY_LIMITATION));
//	            final ArrayList<CharacterSerializedLocksForClient.Locks> locks = this.m_part.content;
//	            for (int i = 0, size = locks.size(); i < size; ++i) {
//	                final CharacterSerializedLocksForClient.Locks lock = locks.get(i);
//	                LocalPlayerCharacter.this.m_locks.setLockDate(lock.lockId, GameDate.fromLong(lock.lockDate), GameDate.fromLong(lock.unlockDate));
//	                LocalPlayerCharacter.this.m_locks.setCurrentLockValue(lock.lockId, lock.currentLockValue);
//	                LocalPlayerCharacter.this.m_locks.setCurrentLockValueDate(lock.lockId, GameDate.fromLong(lock.currentLockValueLastModification));
//	            }
	        }
	    }
	    
	    private final class LocalPlayerCharacterPartAntiAddiction extends CharacterInfoPart
	    {
	        private final CharacterSerializedAntiAddiction m_part;
	        
	        private LocalPlayerCharacterPartAntiAddiction(final CharacterSerializedAntiAddiction part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        
	        public void updateToSerializedPart() {
//	            throw new UnsupportedOperationException("Pas de modification de part ici.");
	        }
	        
	        
	        public void onDataChanged() {
//	            final CharacterSerializedAntiAddiction.AddictionData addictionData = this.m_part.addictionData;
//	            if (addictionData == null) {
//	                LocalPlayerCharacter.this.m_antiAddictionDataHandler.setEnabled(false);
//	            }
//	            else {
//	                final AntiAddictionLevel previousLevel = AntiAddictionHelper.getCurrentLevel(LocalPlayerCharacter.this.m_antiAddictionDataHandler.getLastConnectionDate(), LocalPlayerCharacter.this.m_antiAddictionDataHandler.getCurrentUsedQuota(), WakfuGameCalendar.getInstance().getDate());
//	                LocalPlayerCharacter.this.m_antiAddictionDataHandler.setEnabled(true);
//	                LocalPlayerCharacter.this.m_antiAddictionDataHandler.setLastConnectionDate(GameDate.fromLong(addictionData.lastConnectionDate));
//	                LocalPlayerCharacter.this.m_antiAddictionDataHandler.setCurrentUsedQuota(GameInterval.fromLong(addictionData.currentUsedQuota));
//	                final AntiAddictionLevel currentLevel = AntiAddictionHelper.getCurrentLevel(LocalPlayerCharacter.this.m_antiAddictionDataHandler.getLastConnectionDate(), LocalPlayerCharacter.this.m_antiAddictionDataHandler.getCurrentUsedQuota(), WakfuGameCalendar.getInstance().getDate());
//	                AntiAddictionClientHelper.sendNotification(currentLevel);
//	            }
	        }
	    }
	    
	    private final class PlayerCharacterPartNationPvpMoney extends CharacterInfoPart
	    {
	        private final CharacterSerializedNationPvpMoney m_part;
	        
	        PlayerCharacterPartNationPvpMoney(final CharacterSerializedNationPvpMoney part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        
	        public void updateToSerializedPart() {
//	            LocalPlayerCharacter.m_logger.error((Object)"[NATION] Pas de s?rialization de la nation PUBLIC dans le client pour l'instant", (Throwable)new UnsupportedOperationException());
	        }
	        
	        
	        public void onDataChanged() {
//	            final CitizenComportment comportment = LocalPlayerCharacter.this.getCitizenComportment();
//	            comportment.setPvpMoneyAmount(this.m_part.pvpMoneyAmount);
//	            comportment.setDailyPvpMoneyAmount(this.m_part.dailyPvpMoneyAmount);
	        }
	    }
	    
	    private final class PlayerCharacterPartPersonalEffects extends CharacterInfoPart
	    {
	        private final CharacterSerializedPersonalEffects m_part;
	        
	        private PlayerCharacterPartPersonalEffects(final CharacterSerializedPersonalEffects part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        
	        public void updateToSerializedPart() {
//	            throw new UnsupportedOperationException("Pas de modification de part ici.");
	        }
	        
	        
	        public void onDataChanged() {
//	            final TIntHashSet previousGuildEffects = LocalPlayerCharacter.this.m_guildEffects;
//	            final TIntHashSet previousHavenEffects = LocalPlayerCharacter.this.m_havenWorldEffects;
//	            final TIntHashSet previousAntiAddictionEffect = LocalPlayerCharacter.this.m_antiAddictionEffects;
//	            if (this.m_part.guildEffects != null) {
//	                LocalPlayerCharacter.this.m_guildEffects = new TIntHashSet(this.m_part.guildEffects);
//	            }
//	            else {
//	                LocalPlayerCharacter.this.m_guildEffects = null;
//	            }
//	            if (this.m_part.havenWorldEffects != null) {
//	                LocalPlayerCharacter.this.m_havenWorldEffects = new TIntHashSet(this.m_part.havenWorldEffects);
//	            }
//	            else {
//	                LocalPlayerCharacter.this.m_havenWorldEffects = null;
//	            }
//	            if (this.m_part.antiAddictionEffects != null) {
//	                LocalPlayerCharacter.this.m_antiAddictionEffects = new TIntHashSet(this.m_part.antiAddictionEffects);
//	            }
//	            else {
//	                LocalPlayerCharacter.this.m_antiAddictionEffects = null;
//	            }
//	            if (!LocalPlayerCharacter.this.isOnFight() && this.hasChanged(previousAntiAddictionEffect, LocalPlayerCharacter.this.m_antiAddictionEffects)) {
//	                LocalPlayerCharacter.this.reloadAntiAddictionBuffs();
//	            }
//	            if (!LocalPlayerCharacter.this.isOnFight() && this.hasChanged(previousGuildEffects, LocalPlayerCharacter.this.m_guildEffects)) {
//	                LocalPlayerCharacter.this.reloadGuildBuffs();
//	            }
//	            if (!LocalPlayerCharacter.this.isOnFight() && this.hasChanged(previousHavenEffects, LocalPlayerCharacter.this.m_havenWorldEffects)) {
//	                LocalPlayerCharacter.this.reloadHavenWorldBuffs();
//	            }
	        }
	    }
	    
	    private final class LocalPlayerCharacterPartAccountInformation extends CharacterInfoPart
	    {
	        private final CharacterSerializedAccountInformation m_part;
	        
	        private LocalPlayerCharacterPartAccountInformation(final CharacterSerializedAccountInformation part) {
	            super();
	            this.m_part = part;
	            this.m_part.getBinarPart().setDataSource(this);
	        }
	        
	        
	        public void updateToSerializedPart() {
//	            throw new UnsupportedOperationException("Les informations de compte de sont pas s\u00e9rialis\u00e9s par le client");
	        	m_accountInformationHandler.build(m_part);
	        }
	        
	        
	        public void onDataChanged() {
//	            LocalPlayerCharacter.this.getAccountInformationHandler().fromBuild(this.m_part);
//	            PropertiesProvider.getInstance().setPropertyValue("subscribedAccount", WakfuAccountPermissionContext.SUBSCRIBER.hasPermission(LocalPlayerCharacter.this));
//	            PropertiesProvider.getInstance().setPropertyValue("subscribedZoneAccount", WakfuAccountPermissionContext.SUBSCRIBER_ZONE.hasPermission(LocalPlayerCharacter.this));
//	            PropertiesProvider.getInstance().setPropertyValue("politicInteractionRight", LocalPlayerCharacter.this.hasSubscriptionRight(SubscriptionRight.POLITIC_INTERACTION));
//	            PropertiesProvider.getInstance().firePropertyValueChanged(LocalPlayerCharacter.this, "isInSubscriberZone");
	        }
	    }
	
}
