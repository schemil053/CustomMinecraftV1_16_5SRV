package de.emilschlampp.customMinecraftServer.command;

import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.packets.play.OutChangeGameStatePacket;
import de.emilschlampp.customMinecraftServer.utils.Command;

import java.util.List;

public class GameStateCommand extends Command {
    public GameStateCommand() {
        super("gamestate");
    }

    @Override
    public void run(ServerConnectionThread connectionThread, String[] args) {
        if(args.length != 2) {
            connectionThread.asPacketPlayer().sendMessage("/gamestate <reason> <action>");
            return;
        }
        try {
            connectionThread.send(new OutChangeGameStatePacket(Byte.valueOf(args[0]), Float.valueOf(args[1])));
            connectionThread.asPacketPlayer().sendMessage("OK");
        } catch (Throwable throwable) {
            connectionThread.asPacketPlayer().sendMessage("/gamestate <reason> <action>");
        }
    }

    @Override
    public List<String> tab(ServerConnectionThread connectionThread, String[] args) {
        return super.tab(connectionThread, args);
    }
}
