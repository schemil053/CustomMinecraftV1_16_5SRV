package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.net.data.Location;
import de.emilschlampp.customMinecraftServer.utils.PacketPlayer;
import de.emilschlampp.customMinecraftServer.utils.event.impl.player.PlayerMoveWithOldEvent;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class InPlayerRotationPacket extends Packet {
    private float yaw, pitch;
    private boolean onGround;

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x14;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        this.yaw = readFloat(inputStream);
        this.pitch = readFloat(inputStream);
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
                PacketPlayer.get(connectionThread).yaw != yaw ||
                        PacketPlayer.get(connectionThread).pitch != pitch ||
                        PacketPlayer.get(connectionThread).onGround != onGround;

        if(changed) {
            Location old = new Location(PacketPlayer.get(connectionThread).x, PacketPlayer.get(connectionThread).y, PacketPlayer.get(connectionThread).z,
                    PacketPlayer.get(connectionThread).yaw,
                    PacketPlayer.get(connectionThread).pitch);

            PacketPlayer.get(connectionThread).yaw = yaw;
            PacketPlayer.get(connectionThread).pitch = pitch;
            PacketPlayer.get(connectionThread).onGround = onGround;
            PacketPlayer.get(connectionThread).rebroadcastPosition();
            connectionThread.serverThread.eventManager.call(new PlayerMoveWithOldEvent(connectionThread.asPacketPlayer(),
                    old));
        }
    }
}
