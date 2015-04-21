package com.ankamagames.wakfu.client.core.contentInitializer;

import com.ankamagames.wakfu.client.core.*;
import com.ankamagames.wakfu.client.core.game.breed.AvatarBreedInfo;
import com.ankamagames.wakfu.client.core.game.breed.AvatarBreedInfoManager;
import com.ankamagames.wakfu.client.core.game.spell.SpellManager;
import com.ankamagames.wakfu.client.binaryStorage.*;
import com.ankamagames.wakfu.common.datas.Breed.*;
import com.ankamagames.framework.fileFormat.io.binaryStorage2.*;

public class SpellLoader implements ContentInitializer
{
    @Override
    public String getName() {
        return WakfuTranslator.getInstance().getString("contentLoader.spell");
    }
    
    @Override
    public void init() throws Exception {
        final SpellBinaryData bs = new SpellBinaryData();
        for (final AvatarBreedInfo breed : AvatarBreedInfoManager.getInstance().getBreedInfos()) {
            this.addSpells(bs, breed.getId());
        }
        this.addSpells(bs, AvatarBreed.COMMON.getBreedId());
    }
    
    private void addSpells(final SpellBinaryData bs, final int breedId) throws Exception {
        BinaryDocumentManager.getInstance().foreach(bs, "breed_id", breedId, new LoadProcedure<SpellBinaryData>() {
            @Override
            public void load(final SpellBinaryData data) {
                SpellManager.getInstance().addSpellFromBinaryForm(data);
            }
        });
    }
}
