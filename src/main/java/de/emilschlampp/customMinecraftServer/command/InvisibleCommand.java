package de.emilschlampp.customMinecraftServer.command;

import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.net.data.watcher.DataWatcher;
import de.emilschlampp.customMinecraftServer.packets.play.OutEntityMetadataPacket;
import de.emilschlampp.customMinecraftServer.utils.Command;

import java.util.Arrays;
import java.util.List;

public class InvisibleCommand extends Command {
    public InvisibleCommand() {
        super("invisible");
    }

    @Override
    public void run(ServerConnectionThread connectionThread, String[] args) {
        boolean b = false;
        if(args.length == 0) {
            b = true;
        } else {
            b = args[0].equals("true");
        }

        DataWatcher watcher = new DataWatcher();

        if(b) {
            watcher.put(0, new Byte((byte) 0x20));
        } else {
            watcher.put(0, new Byte((byte) 0));
        }

        connectionThread.send(new OutEntityMetadataPacket(connectionThread.asPacketPlayer().entityID, watcher));


    }

    @Override
    public List<String> tab(ServerConnectionThread connectionThread, String[] args) {
        return Arrays.asList("true", "false");
    }
}
