package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class InEntityActionPacket extends Packet {
    private int entityID;
    private int actionID;
    private int jumpBoost;

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
        this.entityID = readVarInt(inputStream);
        this.actionID = readVarInt(inputStream);
        this.jumpBoost = readVarInt(inputStream);
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        return null;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {

    }

    public static enum Action {
        START_SNEAKING(0),
        STOP_SNEAKING(1),
        LEAVE_BED(2),
        START_SPRINTING(3),
        STOP_SPRINTING(4),
        START_JUMP_WITH_HORSE(5),
        STOP_JUMP_WITH_HORSE(6),
        OPEN_HORSE_INVENTORY(7),
        START_ELYTRA_FLY(8)

        ;

        public final int id;
        Action(int id) {
            this.id = id;
        }
    }
}
