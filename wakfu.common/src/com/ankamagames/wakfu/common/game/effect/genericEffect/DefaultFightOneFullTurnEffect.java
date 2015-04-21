package com.ankamagames.wakfu.common.game.effect.genericEffect;

import com.ankamagames.wakfu.common.game.effect.*;
import com.ankamagames.framework.ai.targetfinder.aoe.*;

public class DefaultFightOneFullTurnEffect extends WakfuFightEffectImpl
{
    public static final DefaultFightOneFullTurnEffect m_instance;
    
    public static DefaultFightOneFullTurnEffect getInstance() {
        return DefaultFightOneFullTurnEffect.m_instance;
    }
    
    private DefaultFightOneFullTurnEffect() {
        this(WakfuStandardEffect.EMPTY_FLOAT_ARRAY, 100);
    }
    
    private DefaultFightOneFullTurnEffect(final float[] params, final int containerMaxLevel) {
        super(-4, -1, AreaOfEffectEnum.POINT.newInstance(null, (short)0), WakfuStandardEffect.EMPTY_INT_ARRAY, WakfuStandardEffect.EMPTY_INT_ARRAY, WakfuStandardEffect.EMPTY_INT_ARRAY, WakfuStandardEffect.EMPTY_INT_ARRAY, WakfuStandardEffect.EMPTY_INT_ARRAY, WakfuStandardEffect.EMPTY_INT_ARRAY, WakfuStandardEffect.EMPTY_INT_ARRAY, 0L, null, false, 1, 0.0f, true, true, 0, 0.0f, params, 100.0f, 0.0f, false, false, false, false, 0, containerMaxLevel, null, (short)(-1), 0.0f, (byte)(-1), false, true, true, false, null, false, false, false, false);
    }
    
    public static DefaultFightOneFullTurnEffect makeWithParams(final float[] params) {
        return new DefaultFightOneFullTurnEffect(params, 100);
    }
    
    public static DefaultFightOneFullTurnEffect makeWithParamsAndMaxLevel(final float[] params, final int containerMaxLevel) {
        return new DefaultFightOneFullTurnEffect(params, containerMaxLevel);
    }
    
    static {
        m_instance = new DefaultFightOneFullTurnEffect();
    }
}
