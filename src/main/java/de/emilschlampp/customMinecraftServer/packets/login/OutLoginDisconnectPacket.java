package de.emilschlampp.customMinecraftServer.packets.login;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class OutLoginDisconnectPacket extends Packet {
    private String reason;

    public OutLoginDisconnectPacket(String reason) {
        this.reason = reason;
    }

    public OutLoginDisconnectPacket() {
    }

    @Override
    public int getState() {
        return 1;
    }

    @Override
    public int getID() {
        return 0x00;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        reason = readVarString(inputStream);
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        writeVarString(reason, outputStream);
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {

    }
}
