package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.utils.CommandRegistry;
import de.emilschlampp.customMinecraftServer.utils.JSONUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.util.UUID;

public class InChatMessagePacket extends Packet {
    private String message;

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 3;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        message = readVarString(inputStream);
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {
        System.out.println(message);
        if(message.startsWith("/")) {
            String[] sp = message.substring(1).split(" ");
            CommandRegistry.exec(connectionThread, sp);
            return;
        }
        connectionThread.serverThread.broadcast(new OutChatMessagePacket((byte) 0,
                "{\"text\": \""+JSONUtil.escape("<"+connectionThread.dataMap.get("name")+"> "+message)+"\", \"bold\": \"false\"}",
                (UUID) connectionThread.dataMap.get("uuid")));
    }
}
