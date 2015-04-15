package com.akfu.common.configuration

import com.akfu.common.util.ValueType
import scala.collection.mutable.ListBuffer

sealed abstract class SystemConfigurationType(
    val id: Int,
    val key: String,
    val default: String, 
    val shareWithClient: Boolean, 
    val valueType: ValueType) {
  
  def this(
    id: Int,
    key: String,
    default: String, 
    valueType: ValueType) {
    this(id, key, default, false, valueType)
  }
  
  SystemConfigurationType.VALUES += this
}

object SystemConfigurationType {
  
    var VALUES = ListBuffer[SystemConfigurationType]()
    
    object MONITORED_PROPERTIES extends SystemConfigurationType(1, "monitoredproperties.enable", "true", false, ValueType.BOOLEAN) 
    object CALENDAR_DELTA extends SystemConfigurationType(2, "calendar.delta", "0", true, ValueType.NUMBER) 
    object CALENDAR_TZ extends SystemConfigurationType(3, "calendar.timezone", "UTC", true, ValueType.STRING) 
    object CENSORSHIP_ENABLE extends SystemConfigurationType(4, "censor.enable", "false", ValueType.BOOLEAN) 
    object SERVER_LANGUAGE extends SystemConfigurationType(5, "serverLanguage", "", true, ValueType.STRINGLIST) 
    object CLIENT_CAN_DISABLE_PROFANITY_FILTER extends SystemConfigurationType(6, "clientCanDisableProfanityFilter", "true", true, ValueType.BOOLEAN) 
    object PLAYER_LEVEL_CAP extends SystemConfigurationType(7, "playerLevelCap", "-1", true, ValueType.NUMBER) 
    object AUTHORIZED_CHARACTER_CLASS extends SystemConfigurationType(8, "authorizedCharacterClass", "", true, ValueType.NUMBERLIST) 
    object WORLD_INSTANCES_FORBIDDEN extends SystemConfigurationType(9, "worldInstances.forbidden", "", true, ValueType.NUMBERLIST) 
    object KROSMOZ_GAMES_ENABLE extends SystemConfigurationType(10, "krosmozGames.enable", "true", true, ValueType.BOOLEAN) 
    object SHOP_INGAME_INTERACTIONS_ENABLE extends SystemConfigurationType(11, "shopInGameInteractions.enable", "false", true, ValueType.BOOLEAN) 
    object CONTACT_MODERATOR_ENABLE extends SystemConfigurationType(12, "contactModerator.enable", "true", true, ValueType.BOOLEAN) 
    object DISPLAY_SUBSCRIPTION_END_POPUP extends SystemConfigurationType(13, "display.subscription.end.popup.enable", "true", true, ValueType.BOOLEAN) 
    object PARTNER extends SystemConfigurationType(14, "partner", "", false, ValueType.STRING) 
    object SHOP_ENABLED extends SystemConfigurationType(15, "shop.enable", "true", true, ValueType.BOOLEAN) 
    object SOAP_AUTHENTICATION_URL extends SystemConfigurationType(16, "soap.authenticationUrl", "", true, ValueType.STRING) 
    object SOAP_ACCOUNT_URL extends SystemConfigurationType(17, "soap.accountUrl", "", true, ValueType.STRING) 
    object SOAP_SHOP_URL extends SystemConfigurationType(18, "soap.shopUrl", "", true, ValueType.STRING) 
    object METRICS_REPORTER_ENABLE extends SystemConfigurationType(19, "metrics.reporter.enable", "false", false, ValueType.BOOLEAN) 
    object PLATFORM_NAME extends SystemConfigurationType(101, "platform.name", "", false, ValueType.STRING) 
    object PLATFORM_COMMUNITY extends SystemConfigurationType(102, "platform.community", "fr", false, ValueType.STRING) 
    object GAME_ID extends SystemConfigurationType(201, "game.id", "3", false, ValueType.NUMBER) 
    object EXPO_MODE_ENABLE extends SystemConfigurationType(202, "expomode.enable", "false", ValueType.BOOLEAN) 
    object ADMIN_RIGHTS_FORCE_ALL extends SystemConfigurationType(203, "adminrights.forceAll", "false", ValueType.BOOLEAN) 
    object SERVER_LOCK_ENABLE extends SystemConfigurationType(204, "serverlock.enable", "false", ValueType.BOOLEAN) 
    object SUBSCRIPTION_REQUIRED extends SystemConfigurationType(206, "subscription.required", "", ValueType.NUMBERLIST) 
    object SUBSCRIPTION_FORCE extends SystemConfigurationType(207, "subscription.force", "", ValueType.NUMBER) 
    object COMMUNITY_CHECK_ENABLE extends SystemConfigurationType(208, "community.check.enabled", "false", ValueType.BOOLEAN) 
    object COMMUNITY_REQUIRED extends SystemConfigurationType(209, "community.required", "", ValueType.NUMBERLIST) 
    object COMMUNITY_FORBIDDEN extends SystemConfigurationType(210, "community.forbidden", "", ValueType.NUMBERLIST) 
    object ANTI_ADDICTION_ENABLE extends SystemConfigurationType(211, "antiAddiction.enable", "false", true, ValueType.BOOLEAN) 
    object ANTI_ADDICTION_FORCED_ACCOUNTS extends SystemConfigurationType(212, "antiAddiction.force.accounts", "", ValueType.NUMBERLIST) 
    object SUBSCRIPTION_FORCED_DURATION_IN_SECOND extends SystemConfigurationType(215, "subscription.forced.duration.in.second", "-1", ValueType.NUMBER) 
    object SUBSCRIPTION_CHECK_SERVER_LIST extends SystemConfigurationType(217, "subscription.check.server.list", "", ValueType.NUMBERLIST) 
    object SUBSCRIPTION_CHECK_GAME_ID extends SystemConfigurationType(221, "subscription.check.game.id", "", ValueType.NUMBER) 
    object QUEUE_ACTIVATED extends SystemConfigurationType(218, "queue.activated", "true", ValueType.BOOLEAN) 
    object QUEUE_PLAYER_LIMIT extends SystemConfigurationType(219, "queue.player.limit", "0", ValueType.NUMBER) 
    object AUTHORIZED_PARTNERS extends SystemConfigurationType(220, "partners.authorized", "default;steam", ValueType.STRINGLIST) 
    object INSTANCE_STATIC_DISTRIBUTION extends SystemConfigurationType(301, "instances.staticDistribution", "true", false, ValueType.BOOLEAN) 
    object ASK_FOR_SECRET_QUESTION_ON_CHARACTER_DELETION extends SystemConfigurationType(302, "askSecretQuestionToDelete", "false", ValueType.BOOLEAN) 
    object PREBOOST_CHARACTER_ENABLE extends SystemConfigurationType(401, "preboostCharacter.enable", "false", ValueType.BOOLEAN) 
    object ACHIEVEMENTS_FORBIDDEN extends SystemConfigurationType(402, "achievements.forbidden", "", ValueType.NUMBERLIST) 
    object INTERACTIVE_ELEMENTS_FORBIDDEN extends SystemConfigurationType(403, "interactiveElements.forbidden", "", ValueType.NUMBERLIST) 
    object FIGHT_CHALLENGE_ENABLE extends SystemConfigurationType(404, "fightChallenge.enable", "true", true, ValueType.BOOLEAN) 
    object DUNGEON_DAILY_LOCK_BYPASS extends SystemConfigurationType(405, "dungeonDailyLockBypass", "", true, ValueType.NUMBERLIST) 
    object COMPANIONS_ENABLE extends SystemConfigurationType(406, "companions.enable", "true", true, ValueType.BOOLEAN) 
    object BETA_MODE extends SystemConfigurationType(407, "beta.mode", "false", true, ValueType.BOOLEAN) 
    object SUBSCRIPTION_DEFAULT_VALUE extends SystemConfigurationType(408, "subscription.defaultValue", "0", false, ValueType.NUMBER) 
    object SUBSCRIPTION_DATE_TIMEZONE extends SystemConfigurationType(409, "subscription.dateTimezone", "Europe/Paris", false, ValueType.STRING) 
    object HAVEN_WORLDS_ENABLE extends SystemConfigurationType(410, "havenWorld.enable", "true", true, ValueType.BOOLEAN) 
    object FREE_COMPANION_ENABLE extends SystemConfigurationType(411, "freeCompanion.enable", "true", false, ValueType.BOOLEAN) 
    object REROLL_XP_BONUS_ENABLE extends SystemConfigurationType(412, "rerollXpBonus.enable", "true", true, ValueType.BOOLEAN) 
    object SHOP_KEY extends SystemConfigurationType(413, "shop.key", "WAKFU_INGAME", true, ValueType.STRING) 
    object INSTANCES_NEEDING_ACCESS_RIGHTS extends SystemConfigurationType(414, "instancesNeedingAccessRights", "", true, ValueType.NUMBERLIST) 
    object INSTANCES_NEEDING_INTERACTION_RIGHTS extends SystemConfigurationType(415, "instancesNeedingInteractionRights", "", true, ValueType.NUMBERLIST) 
    object COLLECT_FIGHT_ENABLED extends SystemConfigurationType(416, "collectFightEnabled", "false", false, ValueType.BOOLEAN) 
    object FIGHT_PREMIUM_DISPLAY extends SystemConfigurationType(417, "fightPremiumEnabled", "false", true, ValueType.BOOLEAN) 
    object TIMER_FOR_FIRST_COLLECT extends SystemConfigurationType(418, "timerForFirstCollect", "false", true, ValueType.BOOLEAN) 
    object FORCE_BIND_ON_PICKUP extends SystemConfigurationType(419, "forceBindOnPickup.enable", "false", true, ValueType.BOOLEAN) 
    object SERVER_ID extends SystemConfigurationType(420, "server.id", "", true, ValueType.NUMBER) 
    object SHOP_ENABLE_KROSZ extends SystemConfigurationType(421, "shop.krosz.enable", "true", true, ValueType.BOOLEAN) 
    object FIGHT_REWORK_ENABLED extends SystemConfigurationType(422, "fight.rework.enabled", "true", true, ValueType.BOOLEAN) 
    object ITEM_TRACKER_LOG_LEVEL extends SystemConfigurationType(423, "itemTracker.logLevel", "10000", false, ValueType.NUMBER) 
    object RECO_IN_FIGHT_ENABLED extends SystemConfigurationType(424, "reco.in.fight.enabled", "false", true, ValueType.BOOLEAN) 
    object NEW_HP_LOSS_FORMULA extends SystemConfigurationType(425, "new.hpLoss.formula", "true", true, ValueType.BOOLEAN) 
    object STEAM_ENABLED extends SystemConfigurationType(426, "steam.enabled", "false", false, ValueType.BOOLEAN) 
    object VAULT_ENABLED extends SystemConfigurationType(427, "vault.enabled", "true", true, ValueType.BOOLEAN) 
    object ZAAP_FREE extends SystemConfigurationType(428, "zaap.free", "false", true, ValueType.BOOLEAN) 
    object NEW_APTITUDE_ENABLED extends SystemConfigurationType(429, "newAptitude.enabled", "true", true, ValueType.BOOLEAN) 
    object PANDA_NEW_BARREL extends SystemConfigurationType(430, "pandaNewBarrel.enabled", "true", true, ValueType.BOOLEAN) 
    object HEROES_ENABLED extends SystemConfigurationType(431, "heroes.enabled", "false", true, ValueType.BOOLEAN) 
    object VERSION_CHECK extends SystemConfigurationType(500, "version.check", "true", false, ValueType.BOOLEAN) 
    object PROXY_LIST extends SystemConfigurationType(600, "proxy.list", "proxies.json", false, ValueType.STRING) 
    object ADMIN_LIST extends SystemConfigurationType(601, "admin.list", "admins.json", false, ValueType.STRING) 
    object HEROES_FORCE_ADD_TO_PARTY extends SystemConfigurationType(602, "heroes.forceAddToParty", "false", true, ValueType.BOOLEAN);
}