package com.ankamagames.wakfu.client.core.contentInitializer;

import org.apache.log4j.*;
import com.ankamagames.framework.fileFormat.io.binaryStorage2.*;
import com.ankamagames.wakfu.client.binaryStorage.*;
import com.ankamagames.wakfu.client.core.*;
import com.ankamagames.wakfu.common.datas.Breed.*;
import com.ankamagames.wakfu.common.game.fighter.*;

public class AvatarBreedLoader implements ContentInitializer
{
    private static final Logger m_logger;
    
    @Override
    public void init() throws Exception {
        BinaryDocumentManager.getInstance().foreach(new AvatarBreedBinaryData(), new LoadProcedure<AvatarBreedBinaryData>() {
            @Override
            public void load(final AvatarBreedBinaryData bs) {
                final int breedId = bs.getId();
                final AvatarBreed breed = AvatarBreed.getBreedFromId(breedId);
                if (breed == null) {
                    AvatarBreedLoader.m_logger.error("Tentative d'initialiser une breed inconnue " + breedId);
                    return;
                }
                breed.setData(createAvatarBreedData(bs));
            }
        });
        BinaryDocumentManager.getInstance().foreach(new AvatarBreedColorsBinaryData(), new LoadProcedure<AvatarBreedColorsBinaryData>() {
            @Override
            public void load(final AvatarBreedColorsBinaryData bs) {
                final int breedId = bs.getId();
            }
        });
    }
        
    @Override
    public String getName() {
        return WakfuTranslator.getInstance().getString("contentLoader.ground");
    }
    
    private static AvatarBreedData createAvatarBreedData(final AvatarBreedBinaryData baseCharacs) {
        final int preferedArea = baseCharacs.getPreferedArea();
        final float[] characRatios = baseCharacs.getCharacRatios();
        final byte[] spellElements = baseCharacs.getSpellElements();
        final short spellInventoryVersion = AvatarBreedSpellVersion.getSpellVersion(baseCharacs.getId());
        final AvatarBreedData data = new AvatarBreedData(preferedArea, characRatios, spellElements, spellInventoryVersion);
        data.setBaseCharacteristic(FighterCharacteristicType.HP, baseCharacs.getBaseHp());
        data.setBaseCharacteristic(FighterCharacteristicType.AP, baseCharacs.getBaseAp());
        data.setBaseCharacteristic(FighterCharacteristicType.MP, baseCharacs.getBaseMp());
        data.setBaseCharacteristic(FighterCharacteristicType.INIT, baseCharacs.getBaseInit());
        data.setBaseCharacteristic(FighterCharacteristicType.FEROCITY, baseCharacs.getBaseFerocity());
        data.setBaseCharacteristic(FighterCharacteristicType.FUMBLE_RATE, baseCharacs.getBaseFumble());
        data.setBaseCharacteristic(FighterCharacteristicType.WISDOM, baseCharacs.getBaseWisdom());
        data.setBaseCharacteristic(FighterCharacteristicType.TACKLE, baseCharacs.getBaseTackle());
        data.setBaseCharacteristic(FighterCharacteristicType.DODGE, baseCharacs.getBaseDodge());
        data.setBaseCharacteristic(FighterCharacteristicType.PROSPECTION, baseCharacs.getBaseProspection());
        data.setBaseCharacteristic(FighterCharacteristicType.WP, baseCharacs.getBaseWp());
        data.setBaseCharacteristic(FighterCharacteristicType.KO_TIME_BEFORE_DEATH, baseCharacs.getTimerCountBeforeDeath());
        return data;
    }
    
    static {
        m_logger = Logger.getLogger(AvatarBreedLoader.class);
    }
}
