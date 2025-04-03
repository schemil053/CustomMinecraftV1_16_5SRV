package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class InInteractEntityPacket extends Packet {
    private int entityID;
    private int type;
    private float x, y, z;
    private int hand;
    private boolean sneak;

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x0E;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        entityID = readVarInt(inputStream);
        type = readVarInt(inputStream);

        if(type == 2) {
            x = readFloat(inputStream);
            y = readFloat(inputStream);
            z = readFloat(inputStream);
        }

        if(type == 2 || type == 0) {
            hand = readVarInt(inputStream);
        }
        sneak = readBoolean(inputStream);

        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {

    }

    public static enum Hand {
        MAIN_HAND(0),
        OFF_HAND(1);

        public final int hand;

        Hand(int hand) {
            this.hand = hand;
        }
    }

    public static enum Type {
        INTERACT(0),
        ATTACK(1),
        INTERACT_AT(2);

        public final int type;

        Type(int type) {
            this.type = type;
        }
    }
}
