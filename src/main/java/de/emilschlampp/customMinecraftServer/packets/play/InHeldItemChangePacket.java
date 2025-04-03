package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.utils.PacketPlayer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class InHeldItemChangePacket extends Packet {
    private short slot;
    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x25;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        this.slot = (short) readShort(inputStream);
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        return null;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {
        System.out.println(slot+"|SEL");
        PacketPlayer.get(connectionThread).inventory.setHotBarChoosen(slot);
    }
}
