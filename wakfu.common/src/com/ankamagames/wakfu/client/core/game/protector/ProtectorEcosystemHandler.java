package com.ankamagames.wakfu.client.core.game.protector;

import com.ankamagames.wakfu.common.game.protector.*;
import com.ankamagames.wakfu.common.datas.*;

public class ProtectorEcosystemHandler extends AbstractProtectorEcosystemHandler
{    
    public ProtectorEcosystemHandler(final ProtectorBase protector) {
        super(protector);
    }
    
    public void requestProtectMonsterFamily(final int monsterFamilyId) {
    }
    
    public void requestProtectResourceFamily(final int resourceFamilyId) {
    }
    
    public void requestUnprotectMonsterFamily(final int monsterFamilyId) {
    }
    
    public void requestUnprotectResourceFamily(final int resourceFamilyId) {
    }
    
    public void requestReintroduceMonsterFamily(final int monsterFamilyId) {
    }
    
    public void requestReintroduceResourceFamily(final int monsterFamilyId) {
    }
    
    @Override
    public boolean reintroduceMonsterFamily(final BasicCharacterInfo user, final int monsterFamilyId) {
        return true;
    }
    
    @Override
    public boolean reintroduceResourceFamily(final BasicCharacterInfo user, final int resourceFamilyId) {
        return true;
    }
    
    @Override
    public boolean canMonsterFamilyBeReintroduced(final int monsterFamilyId) {
        return false;
    }
    
    @Override
    public boolean canResourceFamilyBeReintroduced(final int resourceFamilyId) {
        return false;
    }
}
