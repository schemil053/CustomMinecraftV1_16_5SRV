package de.emilschlampp.customMinecraftServer.command;

import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.packets.play.OutChangeGameStatePacket;
import de.emilschlampp.customMinecraftServer.utils.Command;
import de.emilschlampp.customMinecraftServer.utils.PacketPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameModeCommand extends Command {
    public GameModeCommand() {
        super("gamemode");
    }

    @Override
    public List<String> tab(ServerConnectionThread connectionThread, String[] args) {
        if(args.length == 1) {
            return Arrays.asList("survival", "creative", "adventure", "spectator");
        }
        if(args.length == 2) {
            return null;
        }
        return new ArrayList<>();
    }

    @Override
    public void run(ServerConnectionThread connectionThread, String[] args) {
        if(args.length == 0) {
            connectionThread.asPacketPlayer().sendMessagePlain("/gamemode <survival/creative/adventure/spectator> <player>");
            return;
        }
        if(args.length == 1) {
            try {
                connectionThread.asPacketPlayer().setGameMode(OutChangeGameStatePacket.GameMode.valueOf(args[0].toUpperCase()).id);
            } catch (Throwable throwable) {
                connectionThread.asPacketPlayer().sendMessagePlain("/gamemode <survival/creative/adventure/spectator> <player>");
            }
        }
        if(args.length == 2) {
            List<PacketPlayer> t = PacketPlayer.get(connectionThread.serverThread, args[1]);
            if(t == null || t.isEmpty()) {
                connectionThread.asPacketPlayer().sendMessage("§cSpieler nicht gefunden!");
                return;
            }
            try {
                t.forEach(ta -> {
                    ta.setGameMode(OutChangeGameStatePacket.GameMode.valueOf(args[0].toUpperCase()).id);
                });
            } catch (Throwable throwable) {
                connectionThread.asPacketPlayer().sendMessagePlain("/gamemode <survival/creative/adventure/spectator> <player>");
            }
        }
    }
}
