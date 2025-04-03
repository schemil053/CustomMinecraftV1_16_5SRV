package de.emilschlampp.customMinecraftServer.utils.event.impl.player;

import de.emilschlampp.customMinecraftServer.net.data.Location;
import de.emilschlampp.customMinecraftServer.utils.PacketPlayer;
import de.emilschlampp.customMinecraftServer.utils.event.impl.PlayerEvent;

public class PlayerMoveWithOldEvent extends PlayerEvent {
    private final Location old;

    public PlayerMoveWithOldEvent(PacketPlayer player, Location old) {
        super(player);
        this.old = old;
    }

    public Location getOld() {
        return old;
    }
}
