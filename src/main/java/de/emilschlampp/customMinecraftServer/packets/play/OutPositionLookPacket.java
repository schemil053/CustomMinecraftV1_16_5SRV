package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class OutPositionLookPacket extends Packet {
    private double x, y, z;
    private float yaw, pitch;
    private byte flags = 0x00;
    private byte teleportID = 0x55;

    public OutPositionLookPacket() {
    }

    public OutPositionLookPacket(double x, double y, double z, float yaw, float pitch, byte flags) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.flags = flags;
    }

    public OutPositionLookPacket(double x, double y, double z, float yaw, float pitch, byte flags, byte teleportID) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.flags = flags;
        this.teleportID = teleportID;
    }

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x34;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        writeDouble(x, outputStream);
        writeDouble(y, outputStream);
        writeDouble(z, outputStream);

        writeFloat(yaw, outputStream);
        writeFloat(pitch, outputStream);

        outputStream.write(flags);
        outputStream.write(teleportID);
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {

    }
}
