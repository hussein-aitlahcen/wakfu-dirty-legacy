package com.ankamagames.wakfu.common.game.nation.Laws;

import com.ankamagames.framework.external.*;
import com.ankamagames.wakfu.common.game.nation.Laws.impl.*;

public enum NationLawModelConstant implements ExportableEnum, Parameterized
{
    USE_INTERACTIVE_ELEMENTS(1, UseInteractiveElementsLaw.MODEL, NationLawEventType.USE_INTERACTIVE_ELEMENT), 
    PUT_COLLECTOR_MONEY(2, PutCollectorMoneyLaw.MODEL, NationLawEventType.PUT_COLLECTOR_CONTENT), 
    ACT_AGAINST_PROTECTOR(3, ActAgainstProtectorLaw.MODEL, NationLawEventType.PROTECTOR_WISH_ACTION), 
    ACT_FOLLOWING_PROTECTOR(4, ActFollowingProtectorLaw.MODEL, NationLawEventType.PROTECTOR_WISH_ACTION), 
    PROPOSE_DUEL(5, ProposeDuelLaw.MODEL, NationLawEventType.PROPOSE_DUEL), 
    REFUSE_DUEL(6, RefuseDuelLaw.MODEL, NationLawEventType.ANSWER_DUEL), 
    FIGHT_GOVERNMENT(7, FightGovernmentLaw.MODEL, NationLawEventType.FIGHT), 
    FIGHT_GOOD_CITIZEN(8, FightGoodCitizenLaw.MODEL, NationLawEventType.FIGHT), 
    FIGHT_GOOD_FOREIGNER(9, FightGoodForeignerLaw.MODEL, NationLawEventType.FIGHT), 
    JOIN_FIGHT_OUTLAW(10, JoinFightOutlawLaw.MODEL, NationLawEventType.JOIN_FIGHT), 
    JOIN_FIGHT_NOT_GROUPED(11, JoinFightNotGroupedLaw.MODEL, NationLawEventType.JOIN_FIGHT), 
    WON_FIGHT_PROTECTOR(12, WonFightProtectorLaw.MODEL, NationLawEventType.WON_FIGHT), 
    LOSE_FIGHT_INITIATED(13, LoseFightInitiatedLaw.MODEL, NationLawEventType.LOSE_FIGHT), 
    LOSE_FIGHT_DUEL(14, LoseFightDuelLaw.MODEL, NationLawEventType.LOSE_FIGHT), 
    KILL_OUTLAW(15, KillOutlawLaw.MODEL, NationLawEventType.KILL), 
    KILL_TEAMMATE(16, KillTeammateLaw.MODEL, NationLawEventType.KILL), 
    FIGHT_ECO(17, FightMonsterLaw.MODEL, NationLawEventType.FIGHT), 
    FIGHT_ECO_PROTECTED(18, FightProtectedLaw.MODEL, NationLawEventType.FIGHT), 
    COLLECT_ECO(19, CollectLaw.MODEL, NationLawEventType.COLLECT), 
    COLLECT_ECO_PROTECTED(20, CollectProtectedLaw.MODEL, NationLawEventType.COLLECT), 
    PROPOSE_CITIZEN_EXCHANGE(21, ProposeCitizenExchangeLaw.MODEL, NationLawEventType.PROPOSE_EXCHANGE), 
    PROPOSE_FOREIGNER_EXCHANGE(22, ProposeForeignerExchangeLaw.MODEL, NationLawEventType.PROPOSE_EXCHANGE), 
    PROPOSE_OUTLAW_EXCHANGE(23, ProposeOutlawExchangeLaw.MODEL, NationLawEventType.PROPOSE_EXCHANGE), 
    REGISTER_CANDIDATE_EXCEPT_GOVERNOR(24, RegisterCandidateExceptGovernorLaw.MODEL, NationLawEventType.REGISTER_CANDIDATE), 
    VOTE_AGAINST_GOVERNOR(25, VoteAgainstGovernorLaw.MODEL, NationLawEventType.VOTE), 
    VOTE_FOR_GOVERNOR(26, VoteForGovernorLaw.MODEL, NationLawEventType.VOTE);
    
    public final int id;
    public final NationLawModel<? extends NationLaw> model;
    public final NationLawEventType listenedEventType;
    
    private NationLawModelConstant(final int idx, final NationLawModel<? extends NationLaw> model, final NationLawEventType eventType) {
        this.id = idx;
        this.model = model;
        this.listenedEventType = eventType;
    }
    
    public static NationLawModelConstant fromId(final int id) {
        final NationLawModelConstant[] values = values();
        for (int i = 0; i < values.length; ++i) {
            final NationLawModelConstant value = values[i];
            if (value.id == id) {
                return value;
            }
        }
        return null;
    }
    
    @Override
    public String getEnumId() {
        return String.valueOf(this.id);
    }
    
    @Override
    public String getEnumLabel() {
        return this.toString();
    }
    
    @Override
    public String getEnumComment() {
        return this.toString();
    }
    
    @Override
    public ParameterListSet getParametersListSet() {
        return this.model.getParameters();
    }
}
