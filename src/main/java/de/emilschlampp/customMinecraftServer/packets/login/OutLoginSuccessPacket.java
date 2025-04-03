package de.emilschlampp.customMinecraftServer.packets.login;

import de.emilschlampp.customMinecraftServer.net.NetUtils;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.net.Packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.UUID;

public class OutLoginSuccessPacket extends Packet {
    public OutLoginSuccessPacket() {

    }

    public OutLoginSuccessPacket(UUID uuid, String userName) {
        this.uuid = uuid;
        this.userName = userName;
    }

    private UUID uuid;
    private String userName;

    @Override
    public int getState() {
        return 1;
    }

    @Override
    public int getID() {
        return 0x02;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
     //   outputStream.write(new byte[16]);
        NetUtils.writeVarUUID(uuid, outputStream);
        NetUtils.writeVarString(userName, outputStream);
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) {

    }
}
