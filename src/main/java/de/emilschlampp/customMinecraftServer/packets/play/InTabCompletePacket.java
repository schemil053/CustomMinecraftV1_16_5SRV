package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.net.data.CommandMatch;
import de.emilschlampp.customMinecraftServer.utils.CommandRegistry;
import de.emilschlampp.customMinecraftServer.utils.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InTabCompletePacket extends Packet {
    private int transaction;
    private String text;

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x06;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        transaction = readVarInt(inputStream);
        text = readVarString(inputStream);
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        writeVarInt(transaction, outputStream);
        writeVarString(text, outputStream);
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {
        String[] sp = text.substring(1).split(" ");
        if(text.endsWith(" ")) {
            sp = StringUtils.concat(sp, new String[]{" "});
        }
        List<String> an = CommandRegistry.tab(connectionThread, sp);
        if (an.isEmpty()) {
            return;
        }
        AtomicInteger maxLen = new AtomicInteger();
        for (String s : an) {
            if (maxLen.get() < s.length()) {
                maxLen.set(s.length());
            }
        }
        connectionThread.send(new OutTabCompletePacket(transaction, text.lastIndexOf(" ")+1, maxLen.get()+text.lastIndexOf(" "), an.stream().map(CommandMatch::new).toArray(CommandMatch[]::new)));
    }
}
