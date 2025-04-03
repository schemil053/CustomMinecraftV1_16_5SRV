package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class OutEntityRotationPacket extends Packet {
    private int id;
    private byte yaw, pitch;
    private boolean onGround;

    public OutEntityRotationPacket(int id, byte yaw, byte pitch, boolean onGround) {
        this.id = id;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    public OutEntityRotationPacket() {
    }

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x29;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        writeVarInt(id, outputStream);
        outputStream.write(yaw);
        outputStream.write(pitch);
        writeBoolean(onGround, outputStream);
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {

    }
}
