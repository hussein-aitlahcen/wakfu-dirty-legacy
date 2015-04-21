package com.ankamagames.wakfu.client.core.contentInitializer;

import com.ankamagames.wakfu.client.binaryStorage.*;
import com.ankamagames.framework.fileFormat.io.binaryStorage2.*;

public class SecretLoader implements ContentInitializer
{
    @Override
    public void init() throws Exception {
        this.execute();
    }
    
    public void execute() throws Exception {
        BinaryDocumentManager.getInstance().foreach(new SecretBinaryData(), new LoadProcedure<SecretBinaryData>() {
            @Override
            public void load(final SecretBinaryData bs) {
                final int id = bs.getId();
                final short level = bs.getLevel();
                final short itemId = bs.getItemId();
//                final SecretData secretData = new SecretData(id, level, itemId);
//                SecretManager.INSTANCE.registerSecret(secretData);
            }
        });
    }
    
    @Override
    public String getName() {
        return "";
    }
}
