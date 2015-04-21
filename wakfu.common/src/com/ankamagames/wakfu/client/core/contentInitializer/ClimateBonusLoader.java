package com.ankamagames.wakfu.client.core.contentInitializer;

import org.apache.log4j.*;
import com.ankamagames.wakfu.common.game.climate.*;
import com.ankamagames.framework.fileFormat.io.binaryStorage2.*;
import com.ankamagames.wakfu.client.binaryStorage.*;
import com.ankamagames.wakfu.client.core.*;

public class ClimateBonusLoader implements ContentInitializer
{
    protected static final Logger m_logger;
    
    @Override
    public void init() throws Exception {
        this.loadFromStorage();
    }
    
    private boolean loadFromStorage() throws Exception {
        BinaryDocumentManager.getInstance().foreach(new ClimateBonusBinaryData(), new LoadProcedure<ClimateBonusBinaryData>() {
            @Override
            public void load(final ClimateBonusBinaryData data) {
                final int id = data.getBuffId();
                final String criteria = data.getCriteria();
                final float temperature = data.getTemperatureDifference();
                final float wind = data.getWindDifference();
                final float precipitations = data.getRainDifference();
                final int duration = data.getDuration();
                final short price = data.getPrice();
                try {
                    //final SimpleCriterion criterion = CriteriaCompiler.compileBoolean(criteria);
                    ClimateBonusManager.INSTANCE.addBonus(id, temperature, wind, precipitations, /*criterion*/null, duration, price);
                }
                catch (Exception e) {
                    ClimateBonusLoader.m_logger.error("Exception lev\u00e9e lors de l'interpr\u00e9tation du crit\u00e8re : " + criteria);
                }
            }
        });
        BinaryDocumentManager.getInstance().foreach(new ClimateBonusListBinaryData(), new LoadProcedure<ClimateBonusListBinaryData>() {
            @Override
            public void load(final ClimateBonusListBinaryData data) {
                ClimateBonusManager.INSTANCE.addBonusList(data.getBuffListId(), data.getEntries());
            }
        });
        return true;
    }
    
    @Override
    public String getName() {
        return WakfuTranslator.getInstance().getString("contentLoader.climateBonus");
    }
    
    static {
        m_logger = Logger.getLogger(ClimateBonusLoader.class);
    }
}
