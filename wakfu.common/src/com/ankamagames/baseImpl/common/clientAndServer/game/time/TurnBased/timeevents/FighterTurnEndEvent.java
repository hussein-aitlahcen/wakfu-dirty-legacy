package com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.timeevents;

import org.apache.log4j.*;
import com.ankamagames.framework.kernel.core.common.*;

public class FighterTurnEndEvent extends FighterTurnEvent implements Releasable
{
    private static final MonitoredPool m_staticPool;
    private static Logger m_logger;
    
    @Override
    public void sendTo(final TimeEventHandler handler) {
        handler.handleFighterTurnEndEvent(this);
    }
    
    public static FighterTurnEndEvent checkOut(final long fighterId) {
        FighterTurnEndEvent event;
        try {
            event = (FighterTurnEndEvent)FighterTurnEndEvent.m_staticPool.borrowObject();
            event.m_pool = FighterTurnEndEvent.m_staticPool;
        }
        catch (Exception e) {
            FighterTurnEndEvent.m_logger.warn("Erreur au checkOut d'un " + FighterTurnEndEvent.class.getSimpleName());
            event = new FighterTurnEndEvent();
        }
        event.setFighterId(fighterId);
        return event;
    }
    
    @Override
    public void release() {
        if (this.m_pool != null) {
            try {
                this.m_pool.returnObject(this);
            }
            catch (Exception e) {
                FighterTurnEndEvent.m_logger.warn("Erreur au release d'un " + FighterTurnEndEvent.class.getSimpleName());
            }
            this.m_pool = null;
        }
        else {
            FighterTurnEndEvent.m_logger.error("Double release de " + this.getClass().toString());
        }
    }
    
    static {
        m_staticPool = new MonitoredPool(new ObjectFactory<FighterTurnEndEvent>() {
            @Override
            public FighterTurnEndEvent makeObject() {
                return new FighterTurnEndEvent();
            }
        });
        FighterTurnEndEvent.m_logger = Logger.getLogger(FighterTurnEndEvent.class);
    }
}
