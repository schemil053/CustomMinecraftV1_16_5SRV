package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.util.UUID;

public class OutChatMessagePacket extends Packet {
    private byte position;
    private String encodedJson;
    private UUID sender;

    public OutChatMessagePacket() {
    }

    public OutChatMessagePacket(byte position, String encodedJson, UUID sender) {
        this.position = position;
        this.encodedJson = encodedJson;
        this.sender = sender;
    }

    public OutChatMessagePacket(String encodedJson) {
        this.encodedJson = encodedJson;
        this.position = 1;
        this.sender = null;
    }

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
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        writeVarString(encodedJson, outputStream);
        outputStream.write(position);
        if(sender != null) {
            writeVarUUID(sender, outputStream);
        } else {
            writeLong(0, outputStream);
            writeLong(0, outputStream);
        }
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {

    }

    public static enum Position {
        CHAT_BOX(0),
        SYSTEM(1),
        GAME_INFO(2)
        ;
        public final byte position;

        Position(int pos) {
            position = (byte) pos;
        }
    }
}
