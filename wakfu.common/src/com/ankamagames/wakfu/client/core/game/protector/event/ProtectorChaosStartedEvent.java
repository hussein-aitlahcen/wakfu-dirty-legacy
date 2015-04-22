package com.ankamagames.wakfu.client.core.game.protector.event;

import com.ankamagames.wakfu.client.core.game.protector.*;
import com.ankamagames.wakfu.client.core.game.protector.snapshot.*;

public class ProtectorChaosStartedEvent extends ProtectorEvent
{
    private Territory m_territory;
    
    public Territory getTerritory() {
        return this.m_territory;
    }
    
    public void setTerritory(final Territory territory) {
        this.m_territory = territory;
    }
    
    @Override
    public ProtectorMood getProtectorMood() {
        return ProtectorMood.HANGRY;
    }
    
    @Override
    public String[] getParams() {
    	return null;
    }
}
