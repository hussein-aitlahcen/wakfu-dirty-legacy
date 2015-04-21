package com.ankamagames.wakfu.client.core.contentInitializer;

import org.apache.log4j.*;
import com.ankamagames.wakfu.client.core.*;
import com.ankamagames.wakfu.client.binaryStorage.*;
import com.ankamagames.framework.fileFormat.io.binaryStorage2.*;

public final class InteractiveElementLoader implements ContentInitializer
{
    private static final Logger m_logger;
    
    @Override
    public String getName() {
        return WakfuTranslator.getInstance().getString("contentLoader.interactiveElement");
    }
    
    @Override
    public void init() throws Exception {
        try {
            BinaryDocumentManager.getInstance().foreach(new InteractiveElementModelBinaryData(), new LoadProcedure<InteractiveElementModelBinaryData>() {
                @Override
                public void load(final InteractiveElementModelBinaryData ibs) {
                    final int viewModelId = ibs.getId();
                    final short viewTypeId = ibs.getType();
                    final int gfx = ibs.getGfx();
                    final int color = ibs.getColor();
                    final byte height = ibs.getHeight();
                    final int particleId = ibs.getParticleId();
                    final int particleOffsetZ = ibs.getParticleOffsetZ();
//                    InteractiveElementLoader.this.m_config.setViewProperties(viewModelId, viewTypeId, gfx, height, color, particleId, particleOffsetZ);
                    if (InteractiveElementLoader.m_logger.isTraceEnabled()) {
                        InteractiveElementLoader.m_logger.trace("Loaded view id=" + viewModelId + " type=" + viewTypeId + " gfx=" + gfx + " color=" + color + " height=" + height);
                    }
                }
            });
        }
        catch (Exception e) {
            InteractiveElementLoader.m_logger.error("Erreur lors de la lecture du fichier de vues d'\u00e9l\u00e9ments interactifs", e);
        }
//        ((InteractiveElementFactory<T, WakfuClientInteractiveElementFactoryConfiguration>)WakfuClientInteractiveElementFactory.getInstance()).configure(this.m_config);
    }
    
    static {
        m_logger = Logger.getLogger(InteractiveElementLoader.class);
    }
}
