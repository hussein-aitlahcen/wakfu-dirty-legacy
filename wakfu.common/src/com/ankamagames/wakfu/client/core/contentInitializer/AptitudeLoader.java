package com.ankamagames.wakfu.client.core.contentInitializer;

import org.apache.log4j.*;

import com.ankamagames.wakfu.client.binaryStorage.*;
import com.ankamagames.wakfu.common.game.effect.*;
import com.ankamagames.framework.fileFormat.io.binaryStorage2.*;
import com.ankamagames.wakfu.common.game.fighter.*;
import com.ankamagames.wakfu.common.game.aptitude.*;
import com.ankamagames.wakfu.client.core.*;
import com.ankamagames.wakfu.client.core.effect.manager.EffectManager;

public class AptitudeLoader implements ContentInitializer
{
    private static final Logger m_logger;
    private static final AptitudeLoader m_instance;
    
    public static AptitudeLoader getInstance() {
        return AptitudeLoader.m_instance;
    }
    
    @Override
    public void init() throws Exception {
        BinaryDocumentManager.getInstance().foreach(new AptitudeBinaryData(), new LoadProcedure<AptitudeBinaryData>() {
            @Override
            public void load(final AptitudeBinaryData bs) {
                final ReferenceAptitude refAptitude = AptitudeLoader.this.createReferenceAptitude(bs);
                ReferenceAptitudeManager.getInstance().registerReferenceAptitude(refAptitude);
                for (final int effectId : bs.getEffectIds()) {
                    final WakfuEffect effect = EffectManager.getInstance().loadAndAddEffect(effectId);
                    if (effect != null) {
                        refAptitude.addEffect(effect);
                    }
                    else {
                        AptitudeLoader.m_logger.error((Object)("Probl\u00e8me de chargmeent de ReferenceAptitude " + bs.getId()));
                    }
                }
            }
        });
    }
    
    private ReferenceAptitude createReferenceAptitude(final AptitudeBinaryData bs) {
        final short breedId = bs.getBreedId();
        final int[] breedIds = AptitudeHelper.getBreedId(bs.getId(), breedId);
        final FighterCharacteristicType characteristicType = FighterCharacteristicType.getCharacteristicTypeFromId(bs.getCharacteristicId());
        final AptitudeType type = AptitudeHelper.getAptitudeType(breedIds, characteristicType);
        return new ReferenceAptitude((short)bs.getId(), bs.getUiId(), breedIds, characteristicType, bs.getLinkedSpellId(), bs.getAptitudeGfxId(), bs.getSpellGfxId(), bs.getMaxLevel(), bs.getConstantCost(), bs.getVariableCost(), bs.getLevelUnlock(), type);
    }
    
    @Override
    public String getName() {
        return WakfuTranslator.getInstance().getString("contentLoader.aptitudes");
    }
    
    static {
        m_logger = Logger.getLogger((Class)AptitudeLoader.class);
        m_instance = new AptitudeLoader();
    }
}
