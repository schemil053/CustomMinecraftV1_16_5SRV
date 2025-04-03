package de.emilschlampp.customMinecraftServer.utils.event.impl;

import de.emilschlampp.customMinecraftServer.utils.PacketPlayer;
import de.emilschlampp.customMinecraftServer.utils.event.SEvent;

public abstract class PlayerEvent extends SEvent {
    private final PacketPlayer player;
    public PlayerEvent(PacketPlayer player) {
        this.player = player;
    }

    public PacketPlayer getPlayer() {
        return player;
    }
}
