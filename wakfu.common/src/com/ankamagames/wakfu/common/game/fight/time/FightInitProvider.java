package com.ankamagames.wakfu.common.game.fight.time;

import org.apache.log4j.*;
import com.ankamagames.wakfu.common.datas.*;
import com.ankamagames.wakfu.common.game.fight.*;
import com.ankamagames.wakfu.common.game.fighter.*;

class FightInitProvider implements InitProvider
{
    private static final Logger m_logger;
    private final FightersInformationProvider<? extends BasicCharacterInfo> m_fightersInformationProvider;
    
    FightInitProvider(final FightersInformationProvider<? extends BasicCharacterInfo> fightersInformationProvider) {
        super();
        this.m_fightersInformationProvider = fightersInformationProvider;
    }
    
    @Override
    public int getInit(final long fighterId) {
        final BasicFighter fighter = this.m_fightersInformationProvider.getFighterFromId(fighterId);
        if (fighter == null) {
            FightInitProvider.m_logger.error("[_FL_] fightId=" + this.m_fightersInformationProvider.getId() + " : Fighter inconnu " + fighterId + " on ne peut pas recuperer son init");
        }
        return (fighter == null) ? 0 : fighter.getCharacteristicValue(FighterCharacteristicType.INIT);
    }
    
    @Override
    public boolean isUnderHaste(final long fighterId) {
        final BasicFighter fighter = this.m_fightersInformationProvider.getFighterFromId(fighterId);
        return fighter != null && fighter.isActiveProperty(FightPropertyType.HASTE);
    }
    
    static {
        m_logger = Logger.getLogger(FightInitProvider.class);
    }
}
