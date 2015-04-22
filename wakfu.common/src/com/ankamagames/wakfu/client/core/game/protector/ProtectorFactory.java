package com.ankamagames.wakfu.client.core.game.protector;

public final class ProtectorFactory implements com.ankamagames.wakfu.common.game.protector.ProtectorFactory<Protector>
{
    @Override
    public Protector createProtector(final int id) {
        final Protector protector = new Protector(id);
        return protector;
    }
}
