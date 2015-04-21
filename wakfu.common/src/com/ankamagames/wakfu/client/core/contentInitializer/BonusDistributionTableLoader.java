package com.ankamagames.wakfu.client.core.contentInitializer;

import org.apache.log4j.*;
import com.ankamagames.wakfu.client.core.*;

public final class BonusDistributionTableLoader implements ContentInitializer
{
    private static final Logger m_logger;
    private static final BonusDistributionTableLoader m_instance;
    
    public static BonusDistributionTableLoader getInstance() {
        return BonusDistributionTableLoader.m_instance;
    }
    
    @Override
    public String getName() {
        return WakfuTranslator.getInstance().getString("contentLoader.bonusPointDistributionTable");
    }
    
    @Override
    public void init() throws Exception {
    }
    
    static {
        m_logger = Logger.getLogger(BonusDistributionTableLoader.class);
        m_instance = new BonusDistributionTableLoader();
    }
}
