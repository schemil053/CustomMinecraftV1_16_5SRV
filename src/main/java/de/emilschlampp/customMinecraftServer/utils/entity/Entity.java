package de.emilschlampp.customMinecraftServer.utils.entity;

import de.emilschlampp.customMinecraftServer.net.data.Location;
import de.emilschlampp.customMinecraftServer.net.data.watcher.DataWatcher;
import de.emilschlampp.customMinecraftServer.packets.play.OutDestroyEntityPacket;
import de.emilschlampp.customMinecraftServer.packets.play.OutEntityMetadataPacket;
import de.emilschlampp.customMinecraftServer.packets.play.OutSpawnEntityPacket;
import de.emilschlampp.customMinecraftServer.utils.EntityIDProvider;
import de.emilschlampp.customMinecraftServer.utils.MathUtils;
import de.emilschlampp.customMinecraftServer.utils.PacketPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Entity {
    private Location location;
    private final DataWatcher watcher;
    private final int type;
    private final int entityID;
    private final UUID uuid;
    private final int data;

    private final List<Integer> visiblePlayers = new ArrayList<>();

    public Entity(Location location, int type, int data) {
        this.location = location;
        watcher = new DataWatcher();
        this.type = type;
        this.uuid = UUID.randomUUID();
        this.entityID = EntityIDProvider.nextID();
        this.data = data;
    }

    public void spawn(PacketPlayer player) {
        if(visiblePlayers.contains(player.joinID)) {
            despawn(player);
        }
        visiblePlayers.add(player.joinID);
        player.send(new OutSpawnEntityPacket(
                entityID,
                uuid,
                type,
                location.getX(),
                location.getY(),
                location.getZ(),
                MathUtils.calcYawPitch(location.getYaw()),
                MathUtils.calcYawPitch(location.getPitch()),
                data,
                (short) 0, (short) 0, (short) 0 // TODO: 26.08.2023 Velocity
        ));
        updateMeta(player);
    }

    public void despawn(PacketPlayer player) {
        while (visiblePlayers.remove((Integer) player.joinID));

        player.send(new OutDestroyEntityPacket(new int[] {
                entityID
        }));
    }

    public void updateMeta(PacketPlayer player) {
        player.send(new OutEntityMetadataPacket(entityID, watcher));
    }

    public Location getLocation() {
        return location;
    }

    public DataWatcher getWatcher() {
        return watcher;
    }

    public int getType() {
        return type;
    }

    public int getEntityID() {
        return entityID;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getData() {
        return data;
    }

    public Entity setLocation(Location location) {
        this.location = location;
        return this;
    }

    public List<Integer> getVisiblePlayers() {
        return visiblePlayers;
    }
}
