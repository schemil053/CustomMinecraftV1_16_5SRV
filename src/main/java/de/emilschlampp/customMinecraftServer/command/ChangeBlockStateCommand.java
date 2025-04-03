package de.emilschlampp.customMinecraftServer.command;

import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.packets.play.OutBlockChangePacket;
import de.emilschlampp.customMinecraftServer.utils.Command;

public class ChangeBlockStateCommand extends Command {
    public ChangeBlockStateCommand() {
        super("changeblockstate");
    }

    @Override
    public void run(ServerConnectionThread connectionThread, String[] args) {
        if(args.length == 0) {
            connectionThread.asPacketPlayer().sendMessage("§a/changeblockstate <ID>");
            return;
        }

        connectionThread.send(new OutBlockChangePacket(connectionThread.asPacketPlayer().getPosition(),
                Integer.parseInt(args[0])));
    }
}
