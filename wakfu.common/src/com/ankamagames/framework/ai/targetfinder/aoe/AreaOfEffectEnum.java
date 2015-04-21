package com.ankamagames.framework.ai.targetfinder.aoe;

import org.apache.log4j.*;
import com.ankamagames.framework.external.*;
import com.ankamagames.framework.kernel.utils.*;
import java.util.*;

public enum AreaOfEffectEnum implements ExportableEnum, Parameterized
{
    POINT((short)1, PointAOE.class, PointAOE.PARAMETERS_LIST_SET, (short)1), 
    CIRCLE((short)2, CircleAOE.class, CircleAOE.PARAMETERS_LIST_SET, (short)8), 
    CROSS((short)3, CrossAOE.class, CrossAOE.PARAMETERS_LIST_SET, (short)4), 
    T((short)4, TAOE.class, TAOE.PARAMETERS_LIST_SET, (short)2), 
    RING((short)5, RingAOE.class, RingAOE.PARAMETERS_LIST_SET, (short)7), 
    RECTANGLE((short)6, RectangleAOE.class, RectangleAOE.PARAMETERS_LIST_SET, (short)5), 
    RECT_RING((short)7, RectRingAOE.class, RectRingAOE.PARAMETERS_LIST_SET, (short)6), 
    FREE_POINTS_FORM((short)8, PointsAOE.class, PointsAOE.PARAMETERS_LIST_SET, (short)3), 
    TI((short)9, TAOEI.class, TAOEI.PARAMETERS_LIST_SET, (short)2), 
    DIRECTED_RECTANGLE((short)10, DirectedRectangleAOE.class, DirectedRectangleAOE.PARAMETERS_LIST_SET, (short)5), 
    EMPTY((short)32767, EmptyAOE.class, EmptyAOE.PARAMETERS_LIST_SET, (short)0);
    
    private static final Logger m_logger;
    private short m_index;
    private Class<? extends AreaOfEffect> m_class;
    private ParameterListSet m_parameters;
    private short m_weighting;
    
    private AreaOfEffectEnum(final short index, final Class<? extends AreaOfEffect> c, final ParameterListSet parameterListSet, final short weighting) {
        this.m_index = index;
        this.m_class = c;
        this.m_parameters = parameterListSet;
        this.m_weighting = weighting;
    }
    
    public short getIndex() {
        return this.m_index;
    }
    
    public AreaOfEffect newInstance(final int[] params, final short areaOrderingMethod) throws IllegalArgumentException {
        try {
            final AreaOfEffect aoe = this.m_class.newInstance();
            aoe.initialize(params);
            aoe.setOrderingMethod(areaOrderingMethod);
            return aoe;
        }
        catch (InstantiationException e) {
            AreaOfEffectEnum.m_logger.error(ExceptionFormatter.toString(e));
        }
        catch (IllegalAccessException e2) {
            AreaOfEffectEnum.m_logger.error(ExceptionFormatter.toString(e2));
        }
        return null;
    }
    
    public static AreaOfEffect newInstance(final int aoeId, final int[] params, final short areaOrderingMethod) throws IllegalArgumentException {
        for (final AreaOfEffectEnum aoe : values()) {
            if (aoe.getIndex() == aoeId) {
                return aoe.newInstance(params, areaOrderingMethod);
            }
        }
        return null;
    }
    
    public static AreaOfEffect getBiggestAreaOfEffect(final ArrayList<AreaOfEffect> aoeList) {
        AreaOfEffect aoe = null;
        for (final AreaOfEffect iAoe : aoeList) {
            if (aoe == null) {
                aoe = iAoe;
            }
            else {
                if (aoe.getType().getWeighting() >= iAoe.getType().getWeighting()) {
                    continue;
                }
                aoe = iAoe;
            }
        }
        return aoe;
    }
    
    public short getWeighting() {
        return this.m_weighting;
    }
    
    public void setWeighting(final short weighting) {
        this.m_weighting = weighting;
    }
    
    @Override
    public String getEnumId() {
        return Short.valueOf(this.m_index).toString();
    }
    
    @Override
    public String getEnumLabel() {
        return this.toString();
    }
    
    @Override
    public ParameterListSet getParametersListSet() {
        return this.m_parameters;
    }
    
    @Override
    public String getEnumComment() {
        return null;
    }
    
    static {
        m_logger = Logger.getLogger(AreaOfEffectEnum.class);
    }
}
