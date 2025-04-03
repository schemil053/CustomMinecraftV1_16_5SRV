package de.emilschlampp.customMinecraftServer.utils;

import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.packets.play.OutDestroyEntityPacket;
import de.emilschlampp.customMinecraftServer.packets.play.OutPlayerInfoActionPacket;

public class DisconnectHandler implements Handler<ServerConnectionThread> {

    @Override
    public void handle(ServerConnectionThread connectionThread) {
        if(connectionThread.state != 2) {
            return;
        }

        connectionThread.serverThread.broadcast(new OutDestroyEntityPacket(
                new int[]{PacketPlayer.get(connectionThread).entityID}
        ));

        connectionThread.serverThread.broadcast(
                new OutPlayerInfoActionPacket(OutPlayerInfoActionPacket.Action.REMOVE_PLAYER.id, new PacketPlayer[]{
                        PacketPlayer.get(connectionThread)
                })
        );

        connectionThread.serverThread.getServerConnectionThreadListMutable().removeIf(connectionThreadS -> {
            if(connectionThreadS == null) {
                return true;
            }
            return !connectionThreadS.isAlive() || connectionThreadS.equals(connectionThread);
        });
    }
}
