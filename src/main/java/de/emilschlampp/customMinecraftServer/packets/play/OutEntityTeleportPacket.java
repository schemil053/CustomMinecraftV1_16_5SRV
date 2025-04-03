package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class OutEntityTeleportPacket extends Packet {
    private int entityID;
    private double x, y, z;
    private byte yaw, pitch;
    private boolean onGround;

    public OutEntityTeleportPacket() {
    }

    public OutEntityTeleportPacket(int entityID, double x, double y, double z, byte yaw, byte pitch, boolean onGround) {
        this.entityID = entityID;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x56;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        writeVarInt(entityID, outputStream);
        writeDouble(x, outputStream);
        writeDouble(y, outputStream);
        writeDouble(z, outputStream);
        outputStream.write(yaw);
        outputStream.write(pitch);
        writeBoolean(onGround, outputStream);
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {

    }
}
