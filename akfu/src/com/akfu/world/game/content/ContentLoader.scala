package com.akfu.world.game.content

import com.ankamagames.wakfu.client.core.contentInitializer._

object ContentLoader {
  
  
  def registerContentInitializer(initializer: ContentInitializer) {
    initializer init
  }
  
  def initialize() {
        this.registerContentInitializer(new AvatarBreedLoader());
        this.registerContentInitializer(new DialogLoader());
        this.registerContentInitializer(new SpellLoader());
        this.registerContentInitializer(new SkillLoader());
        this.registerContentInitializer(AptitudeLoader.getInstance());
        this.registerContentInitializer(AptitudeBonusLoader.INSTANCE);
        this.registerContentInitializer(AptitudeCategoryModelLoader.INSTANCE);
        this.registerContentInitializer(NationRankLoader.getInstance());
        this.registerContentInitializer(new CraftLoader());
        this.registerContentInitializer(ItemLoader.getInstance());
        this.registerContentInitializer(new ResourceLoader());
        this.registerContentInitializer(TravelsLoader.getInstance());
        this.registerContentInitializer(new TreasureLoader());
        this.registerContentInitializer(new GemAndPowderLoader());
        this.registerContentInitializer(new InteractiveElementLoader());
        this.registerContentInitializer(IEParametersLoader.getInstance());
        this.registerContentInitializer(InteractiveElementTemplatesLoader.getInstance());
        this.registerContentInitializer(new CharacGainPerLevelLoader());
        this.registerContentInitializer(BonusDistributionTableLoader.getInstance());
        this.registerContentInitializer(new LockLoader());
        this.registerContentInitializer(new DungeonLoader());
        this.registerContentInitializer(new MonsterFamilyLoader());
        this.registerContentInitializer(new MonsterFamilyRelationShipLoader());
        this.registerContentInitializer(new MonsterFamilyPestLoader());
        this.registerContentInitializer(new TimelineBuffListLoader());
        this.registerContentInitializer(CitizenRankLoader.getInstance());
        this.registerContentInitializer(NationLawsLoader.getInstance());
        this.registerContentInitializer(new ProtectorBuffLoader());
        this.registerContentInitializer(new ClimateBonusLoader());
        this.registerContentInitializer(new ProtectorEcosystemProtectionLoader());
        this.registerContentInitializer(PetLoader.INSTANCE);
        this.registerContentInitializer(TutorialLoader.INSTANCE);
        this.registerContentInitializer(AchievementsLoader.INSTANCE);
        this.registerContentInitializer(AlmanachEntryLoader.INSTANCE);
        this.registerContentInitializer(AlmanachDateLoader.INSTANCE);
        this.registerContentInitializer(CensorLoader.INSTANCE);
        this.registerContentInitializer(new NationColorLoader());
        this.registerContentInitializer(new GroundTypeLoader());
        this.registerContentInitializer(new GuildBonusLoader());
        this.registerContentInitializer(new GuildLevelLoader());
        this.registerContentInitializer(new HavenWorldAuctionDefinitionLoader());
        this.registerContentInitializer(new HavenBagModelViewLoader());
        this.registerContentInitializer(new ActionVisualLoader());
        this.registerContentInitializer(new FightChallengeLoader());
        this.registerContentInitializer(new FightChallengeMonsterLoader());
        this.registerContentInitializer(new SecretLoader());
        this.registerContentInitializer(new KrosmozFigureLoader());
        this.registerContentInitializer(new InstanceInteractionLevelLoader());
  }
}