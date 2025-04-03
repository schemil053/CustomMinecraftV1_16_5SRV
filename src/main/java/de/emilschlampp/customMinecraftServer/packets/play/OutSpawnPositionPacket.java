package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.net.data.BlockPosition;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class OutSpawnPositionPacket extends Packet {
    private BlockPosition position;

    public OutSpawnPositionPacket(BlockPosition position) {
        this.position = position;
    }

    public OutSpawnPositionPacket() {
    }

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x42;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        position = readPosition(inputStream);
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        writePosition(position, outputStream);
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {

    }
}
