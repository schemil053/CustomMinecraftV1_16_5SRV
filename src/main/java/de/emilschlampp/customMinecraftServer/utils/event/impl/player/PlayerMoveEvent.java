package de.emilschlampp.customMinecraftServer.utils.event.impl.player;

import de.emilschlampp.customMinecraftServer.utils.PacketPlayer;
import de.emilschlampp.customMinecraftServer.utils.event.impl.PlayerEvent;

public class PlayerMoveEvent extends PlayerEvent {
    public PlayerMoveEvent(PacketPlayer player) {
        super(player);
    }
}
