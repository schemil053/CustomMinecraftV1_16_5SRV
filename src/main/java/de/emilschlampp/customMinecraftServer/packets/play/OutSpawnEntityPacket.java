package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.util.UUID;

public class OutSpawnEntityPacket extends Packet {
    private int entityID;
    private UUID uuid;
    private int type;
    private double x, y, z;
    private byte yaw, pitch;
    private short velocityX, velocityY, velocityZ;
    private int data;

    public OutSpawnEntityPacket(int entityID, UUID uuid, int type, double x, double y, double z, byte yaw, byte pitch, int data, short velocityX, short velocityY, short velocityZ) {
        this.entityID = entityID;
        this.uuid = uuid;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.data = data;
    }

    public OutSpawnEntityPacket() {
    }

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x00;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        entityID = readVarInt(inputStream);
        uuid = readVarUUID(inputStream);
        type = readVarInt(inputStream);

        x = readDouble(inputStream);
        y = readDouble(inputStream);
        z = readDouble(inputStream);
        yaw = (byte) inputStream.read();
        pitch = (byte) inputStream.read();
        data = readInt(inputStream);
        velocityX = (short) readShort(inputStream);
        velocityY = (short) readShort(inputStream);
        velocityZ = (short) readShort(inputStream);
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        writeVarInt(entityID, outputStream);
        writeVarUUID(uuid, outputStream);
        writeVarInt(type, outputStream);

        writeDouble(x, outputStream);
        writeDouble(y, outputStream);
        writeDouble(z, outputStream);

        outputStream.write(yaw);
        outputStream.write(pitch);

        writeInt(data, outputStream);
        writeShort(velocityX, outputStream);
        writeShort(velocityY, outputStream);
        writeShort(velocityZ, outputStream);
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {

    }
}
