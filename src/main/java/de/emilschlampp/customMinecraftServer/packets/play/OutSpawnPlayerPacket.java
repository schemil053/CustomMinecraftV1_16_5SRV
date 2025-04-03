package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.util.UUID;

public class OutSpawnPlayerPacket extends Packet {

    private int id;
    private UUID uuid;
    private double x,y, z;

    private byte yaw, pitch;

    public OutSpawnPlayerPacket() {
    }

    public OutSpawnPlayerPacket(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch) {
        this.id = id;
        this.uuid = uuid;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x04;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        writeVarInt(id, outputStream);
        writeVarUUID(uuid, outputStream);
        writeDouble(x, outputStream);
        writeDouble(y, outputStream);
        writeDouble(z, outputStream);
        outputStream.write(yaw);
        outputStream.write(pitch);
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {

    }
}
