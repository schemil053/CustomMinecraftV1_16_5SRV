package de.emilschlampp.customMinecraftServer.packets.status;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class OutResponsePacket extends Packet {
    private String response;

    public OutResponsePacket(String response) {
        this.response = response;
    }

    public OutResponsePacket() {
    }

    @Override
    public int getState() {
        return 3;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        response = readVarString(inputStream);
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        writeVarString(response, outputStream);
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {

    }
}
