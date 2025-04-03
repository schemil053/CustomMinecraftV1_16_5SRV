package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.net.data.*;
import de.emilschlampp.customMinecraftServer.net.data.watcher.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.util.Map;

public class OutEntityMetadataPacket extends Packet {
    private int entityID;
    private Map<Integer, Object> data;

    public OutEntityMetadataPacket(int entityID, Map<Integer, Object> data) {
        this.entityID = entityID;
        this.data = data;
    }

    public OutEntityMetadataPacket(int entityID, DataWatcher watcher) {
        this.entityID = entityID;
        this.data = watcher.getData();
    }

    public OutEntityMetadataPacket() {
    }

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x44;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        writeVarInt(entityID, outputStream);

        data.forEach((k, v) -> {
            outputStream.write(k.byteValue());
            try {
                if(v instanceof Byte) {
                    writeVarInt(DataWatcherType.TYPE_BYTE, outputStream);
                    outputStream.write((Byte) v);
                    return;
                }
                if (v instanceof Integer) {
                    writeVarInt(DataWatcherType.TYPE_VARINT, outputStream);
                    writeVarInt((Integer) v, outputStream);
                    return;
                }
                if(v instanceof Float) {
                    writeVarInt(DataWatcherType.TYPE_FLOAT, outputStream);
                    writeFloat((Float) v, outputStream);
                    return;
                }
                if(v instanceof String) {
                    writeVarInt(DataWatcherType.TYPE_STRING, outputStream);
                    writeVarString((String) v, outputStream);
                    return;
                }
                if(v instanceof Chat) {
                    writeVarInt(DataWatcherType.TYPE_CHAT, outputStream);
                    ((Chat) v).write(outputStream);
                    return;
                }
                if(v instanceof OptChat) {
                    writeVarInt(DataWatcherType.TYPE_OPTCHAT, outputStream);
                    ((OptChat) v).write(outputStream);
                    return;
                }
                if(v instanceof Slot) {
                    writeVarInt(DataWatcherType.TYPE_SLOT, outputStream);
                    writeSlot((Slot) v, outputStream);
                    return;
                }
                if(v instanceof Boolean) {
                    writeVarInt(DataWatcherType.TYPE_BOOLEAN, outputStream);
                    writeBoolean((Boolean) v, outputStream);
                    return;
                }
                if(v instanceof Rotation) {
                    writeVarInt(DataWatcherType.TYPE_ROTATION, outputStream);
                    ((Rotation) v).write(outputStream);
                    return;
                }
                if(v instanceof BlockPosition) {
                    writeVarInt(DataWatcherType.TYPE_POSITION, outputStream);
                    writePosition((BlockPosition) v, outputStream);
                    return;
                }
                if(v instanceof OptPosition) {
                    writeVarInt(DataWatcherType.TYPE_OPTPOSITION, outputStream);
                    ((OptPosition) v).write(outputStream);
                    return;
                }
                if(v instanceof Direction) {
                    writeVarInt(DataWatcherType.TYPE_DIRECTION, outputStream);
                    writeVarInt(((Direction) v).val, outputStream);
                    return;
                }
                if(v instanceof OptUUID) {
                    writeVarInt(DataWatcherType.TYPE_OPTUUID, outputStream);
                    ((OptUUID) v).write(outputStream);
                    return;
                }
                if(v instanceof OptBlockID) {
                    writeVarInt(DataWatcherType.TYPE_OPTBLOCKID, outputStream);
                    ((OptBlockID) v).write(outputStream);
                    return;
                }
                if(v instanceof NBT) {
                    writeVarInt(DataWatcherType.TYPE_NBT, outputStream);
                    outputStream.write(((NBT) v).getVal());
                    return;
                }
                if(v instanceof Particle) {
                    writeVarInt(DataWatcherType.TYPE_PARTICLE, outputStream);
                    ((Particle) v).write(outputStream);
                    return;
                }
                if(v instanceof VillagerData) {
                    writeVarInt(DataWatcherType.TYPE_VILLAGERDATA, outputStream);
                    ((VillagerData) v).write(outputStream);
                    return;
                }
                if(v instanceof OptVarInt) {
                    writeVarInt(DataWatcherType.TYPE_OPTVARINT, outputStream);
                    ((OptVarInt) v).write(outputStream);
                    return;
                }
                if(v instanceof Pose) {
                    writeVarInt(DataWatcherType.TYPE_POSE, outputStream);
                    writeVarInt(((Pose) v).val, outputStream);
                    return;
                }
            } catch (Throwable t) {
                throw new RuntimeException();
            }
        });

        outputStream.write(0xFF);
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {

    }
}
