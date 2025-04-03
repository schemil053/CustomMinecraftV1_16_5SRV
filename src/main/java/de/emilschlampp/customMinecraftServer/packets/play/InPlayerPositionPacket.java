package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.net.data.Location;
import de.emilschlampp.customMinecraftServer.utils.PacketPlayer;
import de.emilschlampp.customMinecraftServer.utils.event.impl.player.PlayerMoveEvent;
import de.emilschlampp.customMinecraftServer.utils.event.impl.player.PlayerMoveWithOldEvent;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class InPlayerPositionPacket extends Packet {
    private double x, y, z;
    private boolean onGround;

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x12;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        this.x = readDouble(inputStream);
        this.y = readDouble(inputStream);
        this.z = readDouble(inputStream);
        this.onGround = readBoolean(inputStream);
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {
        boolean changed =
                PacketPlayer.get(connectionThread).x != x ||
                        PacketPlayer.get(connectionThread).y != y ||
                        PacketPlayer.get(connectionThread).z != z ||
                        PacketPlayer.get(connectionThread).onGround != onGround;

        if(changed) {
            Location old = new Location(PacketPlayer.get(connectionThread).x, PacketPlayer.get(connectionThread).y, PacketPlayer.get(connectionThread).z,
                    PacketPlayer.get(connectionThread).yaw,
                    PacketPlayer.get(connectionThread).pitch);

            PacketPlayer.get(connectionThread).x = x;
            PacketPlayer.get(connectionThread).y = y;
            PacketPlayer.get(connectionThread).z = z;

            PacketPlayer.get(connectionThread).onGround = onGround;

            PacketPlayer.get(connectionThread).rebroadcastPosition();
            connectionThread.serverThread.eventManager.call(new PlayerMoveWithOldEvent(connectionThread.asPacketPlayer(),
                    old));
        }
    }
}
