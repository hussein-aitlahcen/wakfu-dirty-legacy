package com.ankamagames.wakfu.client.core.contentInitializer;

import org.apache.log4j.*;
import com.ankamagames.wakfu.client.binaryStorage.*;
import com.ankamagames.wakfu.common.game.nation.government.*;
import com.ankamagames.framework.fileFormat.io.binaryStorage2.*;
import com.ankamagames.wakfu.client.core.*;

public class NationRankLoader implements ContentInitializer
{
    private static final Logger m_logger;
    private static final NationRankLoader m_instance;
    
    public static NationRankLoader getInstance() {
        return NationRankLoader.m_instance;
    }
    
    @Override
    public void init() throws Exception {
        BinaryDocumentManager.getInstance().foreach(new NationRankBinaryData(), new LoadProcedure<NationRankBinaryData>() {
            @Override
            public void load(final NationRankBinaryData bs) {
                final NationRank rank = NationRank.getById(bs.getId());
                if (rank == null) {
                    NationRankLoader.m_logger.error("NationRank inconnu lors du chargement des binary storage : " + bs.getId());
                    return;
                }
                final float pdcLossFactor = bs.getCitizenPointLossFactor();
                final String criteria = bs.getCriteria();
                final int citizenScoreLine = bs.getCitizenScoreLine();
                rank.setPdcLossFactor(pdcLossFactor);
                rank.setCitizenScoreLine(citizenScoreLine);
            }
        });
    }
    
    @Override
    public String getName() {
        return WakfuTranslator.getInstance().getString("contentLoader.nation.ranks");
    }
    
    static {
        m_logger = Logger.getLogger(NationRankLoader.class);
        m_instance = new NationRankLoader();
    }
}
