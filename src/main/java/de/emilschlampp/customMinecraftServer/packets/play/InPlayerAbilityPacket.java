package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class InPlayerAbilityPacket extends Packet {
    private byte ability;

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x1A;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        ability = (byte) inputStream.read();

        boolean fly = ((ability & 2) > 1); //& ist das gegenteil von |, #BITSHIFTING

      //  System.out.println(fly+"|"+ability);
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {

    }
}
