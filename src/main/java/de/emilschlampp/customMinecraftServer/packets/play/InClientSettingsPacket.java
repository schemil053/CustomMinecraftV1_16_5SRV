package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class InClientSettingsPacket extends Packet {
    private String locale;
    private byte viewDistance;
    private int chatMode;
    private boolean chatColors;
    private byte skinParts;
    private int mainHand;

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x05;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        locale = readVarString(inputStream);
        viewDistance = (byte) inputStream.read();
        chatMode = readVarInt(inputStream);
        chatColors = readBoolean(inputStream);
        skinParts = (byte) inputStream.read();
        mainHand = readVarInt(inputStream);
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        return null;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {

    }

    public static enum ChatMode {
        ENABLED(0),
        COMMANDS_ONLY(1),
        HIDDEN(2);

        public final int chatMode;

        ChatMode(int chatMode) {
            this.chatMode = chatMode;
        }
    }

    public static enum Hand {
        MAIN_HAND(0),
        OFF_HAND(1);

        public final int hand;

        Hand(int hand) {
            this.hand = hand;
        }
    }
}
