package de.emilschlampp.customMinecraftServer.net;

import de.emilschlampp.customMinecraftServer.packets.play.OutKeepAlivePacket;

public class AliveKeeperThread extends Thread {
    private final ServerThread serverThread;
    private long lastAlive;
    private long lastClearRem;
    public AliveKeeperThread(ServerThread serverThread) {
        super("PacketQueueManager");
        this.serverThread = serverThread;
    }

    public void run() {
        while (isAlive() && !isInterrupted()) {
            try {
                if (lastAlive + 5_000 < System.currentTimeMillis()) {
                    lastAlive = System.currentTimeMillis();
                    serverThread.broadcast(new OutKeepAlivePacket(lastAlive));
                }
                if (lastClearRem + 5_000 < System.currentTimeMillis()) {
                    lastClearRem = System.currentTimeMillis();
                    serverThread.removeInvalidConnections();
                }

                for (ServerConnectionThread connectionThread : serverThread.getServerConnectionThreadList()) {
                    if (connectionThread != null) {
                        connectionThread.writeQueue();
                    }
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}
