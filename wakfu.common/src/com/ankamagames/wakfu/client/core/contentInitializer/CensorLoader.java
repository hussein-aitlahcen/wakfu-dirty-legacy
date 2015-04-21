package com.ankamagames.wakfu.client.core.contentInitializer;

import com.ankamagames.wakfu.client.binaryStorage.*;
import com.ankamagames.framework.fileFormat.io.binaryStorage2.*;
import com.ankamagames.baseImpl.common.clientAndServer.utils.wordsModerator.*;
import com.ankamagames.wakfu.client.core.*;

public class CensorLoader implements ContentInitializer
{
    public static final CensorLoader INSTANCE;
    
    @Override
    public void init() throws Exception {
        if (!WordsModerator.CENSOR_ACTIVATED) {
            return;
        }
        BinaryDocumentManager.getInstance().foreach(new CensoredEntryBinaryData(), new LoadProcedure<CensoredEntryBinaryData>() {
            @Override
            public void load(final CensoredEntryBinaryData bs) {
                final CensoredLanguageEnum lang = CensoredLanguageEnum.getById((short)bs.getLanguage());
                final CensorTypeEnum censorType = CensorTypeEnum.getById((short)bs.getCensorType());
                final CensoredWordEntry censoredWordEntry = new CensoredWordEntry(bs.getId(), bs.getText(), lang, censorType, bs.isDeepSearch());
                WordsModerator.getInstance().addCensoredWordEntry(censoredWordEntry);
            }
        });
        WordsModerator.getInstance().init();
    }
    
    @Override
    public String getName() {
        return WakfuTranslator.getInstance().getString("contentLoader.censor");
    }
    
    static {
        INSTANCE = new CensorLoader();
    }
}
