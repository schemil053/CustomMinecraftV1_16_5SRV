package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.net.data.BlockPosition;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class InPlayerBlockPlacementPacket extends Packet {
    private int hand;
    private BlockPosition location;
    private byte face;
    private float cursorX, cursorY, cursorZ;
    private boolean insideBlock;

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x2E;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        this.hand = readVarInt(inputStream);
        this.location = readPosition(inputStream);
        this.face = (byte) readVarInt(inputStream);
        this.cursorX = readFloat(inputStream);
        this.cursorY = readFloat(inputStream);
        this.cursorZ = readFloat(inputStream);
        this.insideBlock = readBoolean(inputStream);
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

    public static enum Face {
        BOTTOM(0, 0, -1, 0),
        TOP(1, 0, 1, 0),
        NORTH(1, 0, 0, -1),
        SOUTH(1, 0, 0, 1),
        WEST(1, -1, 0, 0),
        EAST(1,1, 0, 0),
        ;

        public final byte face;
        public final int offx, offy, offz;

        Face(int face, int offx, int offy, int offz) {
            this.face = (byte) face;
            this.offx = offx;
            this.offy = offy;
            this.offz = offz;
        }
    }
}
