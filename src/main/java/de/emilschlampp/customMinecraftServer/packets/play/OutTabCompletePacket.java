package de.emilschlampp.customMinecraftServer.packets.play;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.net.data.CommandMatch;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class OutTabCompletePacket extends Packet {
    private int id, start, lenght;
    private CommandMatch[] matches;

    public OutTabCompletePacket(int id, int start, int lenght, CommandMatch[] matches) {
        this.id = id;
        this.start = start;
        this.lenght = lenght;
        this.matches = matches;
    }

    public OutTabCompletePacket() {
    }

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x0F;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        writeVarInt(id, outputStream);
        writeVarInt(start, outputStream);
        writeVarInt(lenght, outputStream);
        writeVarInt(matches.length, outputStream);
        for (CommandMatch match : matches) {
            writeCommandMatch(match, outputStream);
        }
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {

    }
}
