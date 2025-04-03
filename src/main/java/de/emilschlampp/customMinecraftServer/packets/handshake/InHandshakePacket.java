package de.emilschlampp.customMinecraftServer.packets.handshake;

import de.emilschlampp.customMinecraftServer.net.NetUtils;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.net.Packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class InHandshakePacket extends Packet {
    private int version, action;
    private short port;
    private String serverIP;

    @Override
    public int getState() {
        return 0;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        version = NetUtils.readVarInt(dataInputStream);
        serverIP = NetUtils.readVarString(dataInputStream);
        port = dataInputStream.readShort();
        action = NetUtils.readVarInt(dataInputStream);

        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) {
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) {
        System.out.println("VERSION: "+version+"| IP: "+serverIP+" | PORT: "+port+" | ACT: "+action);

        connectionThread.dataMap.put("protocol-version", version);

        if(action == 2) {
            connectionThread.state = 1;
        }
        if(action == 1) {
            connectionThread.state = 3;
        }
    }
}
