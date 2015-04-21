package com.ankamagames.wakfu.client.core.contentInitializer;

import org.apache.log4j.*;
import com.ankamagames.wakfu.client.binaryStorage.*;
import gnu.trove.*;
import com.ankamagames.framework.fileFormat.io.binaryStorage2.*;
import com.ankamagames.wakfu.client.core.*;

public class TutorialLoader implements ContentInitializer
{
    protected static final Logger m_logger;
    public static final TutorialLoader INSTANCE;
    
    @Override
    public void init() throws Exception {
        this.loadFromStorage();
    }
    
    private boolean loadFromStorage() throws Exception {
        BinaryDocumentManager.getInstance().foreach(new TutorialBinaryData(), new LoadProcedure<TutorialBinaryData>() {
            @Override
            public void load(final TutorialBinaryData data) {
                final int tutorialId = data.getId();
                final TutorialBinaryData.Event[] eventIds = data.getEventIds();
                final TIntArrayList ids = new TIntArrayList();
                for (final TutorialBinaryData.Event e : eventIds) {
                    ids.add(e.getEventId());
                }
//                TutorialManager.INSTANCE.addTutorialPage(tutorialId, ids.toNativeArray());
            }
        });
        return true;
    }
    
    @Override
    public String getName() {
        return WakfuTranslator.getInstance().getString("contentLoader.scriptAction");
    }
    
    static {
        m_logger = Logger.getLogger(TutorialLoader.class);
        INSTANCE = new TutorialLoader();
    }
}
