package com.ankamagames.wakfu.common.game.nation.Laws.impl;

import com.ankamagames.wakfu.common.game.nation.Laws.impl.events.*;
import com.ankamagames.framework.ai.criteria.antlrcriteria.*;
import com.ankamagames.wakfu.common.datas.*;
import com.ankamagames.wakfu.common.game.nation.*;
import java.util.*;
import com.ankamagames.wakfu.common.game.nation.Laws.*;
import com.ankamagames.framework.external.*;

public class FightGovernmentLaw extends NationLaw<FightLawEvent>
{
    private static final NationLawListSet PARAMETERS;
    public static final NationLawModel<FightGovernmentLaw> MODEL;
    
    private FightGovernmentLaw(final long id, final int cost, final int lawPointCost, final boolean lawLocked, final Iterable<NationLawApplication> lawApplications) {
        super(id, cost, lawPointCost, lawLocked, lawApplications);
    }
    
    @Override
    public void initialize(final List<ParserObject> parameters) {
    }
    
    @Override
    public NationLawModelConstant getModel() {
        return NationLawModelConstant.FIGHT_GOVERNMENT;
    }
    
    @Override
    public boolean isTriggering(final FightLawEvent event) {
        if (!event.getFightModel().isPvp()) {
            return false;
        }
        final BasicCharacterInfo character = (BasicCharacterInfo)event.getCitizen();
        if (character == event.getDefender()) {
            return false;
        }
        final Nation travellingNation = ((BasicCharacterInfo)event.getCitizen()).getTravellingNation();
        if (character == event.getAttacker()) {
            final CitizenComportment comp = event.getDefender().getCitizenComportment();
            if (comp.getNation() != travellingNation) {
                return false;
            }
            if (comp.getRank() != null) {
                return true;
            }
        }
        final Collection opponents = event.getOpponents();
        for (final Object opponent : opponents) {
            final Citizen player = (Citizen)opponent;
            final CitizenComportment comp2 = player.getCitizenComportment();
            if (comp2.getNation() != travellingNation) {
                continue;
            }
            if (comp2.getRank() != null) {
                return true;
            }
        }
        return false;
    }
    
    static {
        PARAMETERS = new NationLawListSet(new ParameterList[] { new DefaultParameterList("Empty", new Parameter[0]) });
        MODEL = new NationLawModel<FightGovernmentLaw>() {
            @Override
            public FightGovernmentLaw createNewLaw(final long id, final int cost, final int lawPointCost, final boolean lawLocked, final Iterable<NationLawApplication> lawApplications) {
                return new FightGovernmentLaw(id, cost, lawPointCost, lawLocked, lawApplications);
            }
            
            @Override
            public NationLawListSet getParameters() {
                return FightGovernmentLaw.PARAMETERS;
            }
        };
    }
}
