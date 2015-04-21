package com.ankamagames.wakfu.client.core.contentInitializer;

import com.ankamagames.wakfu.client.binaryStorage.*;
import com.ankamagames.wakfu.common.game.skill.*;
import com.ankamagames.framework.fileFormat.io.binaryStorage2.*;
import com.ankamagames.wakfu.client.core.*;

public class ActionVisualLoader implements ContentInitializer
{
    @Override
    public void init() throws Exception {
        BinaryDocumentManager.getInstance().foreach(new SkillActionBinaryData(), new LoadProcedure<SkillActionBinaryData>() {
            @Override
            public void load(final SkillActionBinaryData bs) {
                final int visualId = bs.getId();
                final String animLinkage = bs.getAnimLinkage();
                final ActionVisual av = new ActionVisual(visualId, animLinkage, bs.getMruGfxId(), bs.getMruKey().replace("desc.mru.", ""), bs.getAssociatedItems());
                ActionVisualManager.getInstance().add(av);
            }
        });
    }
    
    @Override
    public String getName() {
        return WakfuTranslator.getInstance().getString("contentLoader.ground");
    }
}
