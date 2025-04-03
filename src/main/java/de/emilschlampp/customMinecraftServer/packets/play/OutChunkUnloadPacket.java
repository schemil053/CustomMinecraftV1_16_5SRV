package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class OutChunkUnloadPacket extends Packet {
    private int x, z;

    public OutChunkUnloadPacket(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public OutChunkUnloadPacket() {
    }

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x1C;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        this.x = readInt(inputStream);
        this.z = readInt(inputStream);
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        writeInt(x, outputStream);
        writeInt(z, outputStream);
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {

    }
}
