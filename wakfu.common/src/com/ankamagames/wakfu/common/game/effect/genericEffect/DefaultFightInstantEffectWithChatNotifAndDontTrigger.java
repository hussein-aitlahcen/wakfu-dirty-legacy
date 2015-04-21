package com.ankamagames.wakfu.common.game.effect.genericEffect;

import com.ankamagames.wakfu.common.game.effect.*;
import com.ankamagames.framework.ai.targetfinder.aoe.*;

public class DefaultFightInstantEffectWithChatNotifAndDontTrigger extends WakfuFightEffectImpl
{
    private static final DefaultFightInstantEffectWithChatNotifAndDontTrigger m_instance;
    
    public static DefaultFightInstantEffectWithChatNotifAndDontTrigger getInstance() {
        return DefaultFightInstantEffectWithChatNotifAndDontTrigger.m_instance;
    }
    
    private DefaultFightInstantEffectWithChatNotifAndDontTrigger() {
        super(-11, -1, AreaOfEffectEnum.POINT.newInstance(null, (short)0), WakfuStandardEffect.EMPTY_INT_ARRAY, WakfuStandardEffect.EMPTY_INT_ARRAY, WakfuStandardEffect.EMPTY_INT_ARRAY, WakfuStandardEffect.EMPTY_INT_ARRAY, WakfuStandardEffect.EMPTY_INT_ARRAY, WakfuStandardEffect.EMPTY_INT_ARRAY, WakfuStandardEffect.EMPTY_INT_ARRAY, 0L, null, false, 0, 0.0f, false, false, 0, 0.0f, WakfuStandardEffect.EMPTY_FLOAT_ARRAY, 100.0f, 0.0f, false, false, false, false, 0, Integer.MAX_VALUE, null, (short)(-1), 0.0f, (byte)(-1), true, true, true, false, null, false, false, false, false);
    }
    
    static {
        m_instance = new DefaultFightInstantEffectWithChatNotifAndDontTrigger();
    }
}
