package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.net.data.Location;
import de.emilschlampp.customMinecraftServer.utils.PacketPlayer;
import de.emilschlampp.customMinecraftServer.utils.event.impl.player.PlayerMoveWithOldEvent;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class InPlayerMovementPacket extends Packet {
    private boolean onGround;

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x15;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        this.onGround = readBoolean(inputStream);
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {
        boolean onG = PacketPlayer.get(connectionThread).onGround;

        PacketPlayer.get(connectionThread).onGround = onGround;

        if(PacketPlayer.get(connectionThread).onGround != onG) {
            PacketPlayer.get(connectionThread).rebroadcastPosition();
        }
    }
}
