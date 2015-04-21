package com.ankamagames.wakfu.client.core.contentInitializer;

import com.ankamagames.wakfu.client.binaryStorage.*;
import com.ankamagames.framework.fileFormat.io.binaryStorage2.*;
import com.ankamagames.wakfu.client.core.*;

public class NationColorLoader implements ContentInitializer
{
    @Override
    public void init() throws Exception {
        BinaryDocumentManager.getInstance().foreach(new NationColorsBinaryData(), new LoadProcedure<NationColorsBinaryData>() {
            @Override
            public void load(final NationColorsBinaryData data) {
//                NationDisplayer.getInstance().putNationColor(data.getId(), data.getColor());
            }
        });
    }
    
    @Override
    public String getName() {
        return WakfuTranslator.getInstance().getString("contentLoader.nationColor");
    }
}
