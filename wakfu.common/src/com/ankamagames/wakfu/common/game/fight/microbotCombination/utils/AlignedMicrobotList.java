package com.ankamagames.wakfu.common.game.fight.microbotCombination.utils;

import com.ankamagames.framework.kernel.core.common.collections.*;
import com.ankamagames.wakfu.common.game.effectArea.*;
import org.apache.log4j.*;

public class AlignedMicrobotList extends SortedList<AbstractFakeFighterEffectArea>
{
    public static final Logger m_logger;
    private static final MicrobotPositionComparator staticComparator;
    
    public AlignedMicrobotList() {
        super(AlignedMicrobotList.staticComparator);
    }
    
    static {
        m_logger = Logger.getLogger(AlignedMicrobotList.class);
        staticComparator = new MicrobotPositionComparator();
    }
}
