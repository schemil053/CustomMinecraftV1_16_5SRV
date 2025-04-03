package de.emilschlampp.customMinecraftServer.net;

import de.emilschlampp.customMinecraftServer.utils.*;
import de.emilschlampp.customMinecraftServer.utils.event.EventManager;
import de.emilschlampp.customMinecraftServer.utils.generated.BlockStateID;
import de.emilschlampp.customMinecraftServer.utils.generated.EntityID;
import de.emilschlampp.customMinecraftServer.utils.generated.ItemSlotID;
import de.emilschlampp.customMinecraftServer.utils.generated.ParticleID;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerThread extends Thread {
    private int port;
    private final List<ServerConnectionThread> serverConnectionThreadList = new ArrayList<>();
    private final AliveKeeperThread aliveKeeperThread = new AliveKeeperThread(this);
    public final RegistryProvider registryProvider = new RegistryProvider("1-16-5");
    public final EventManager eventManager = new EventManager();

    public ServerThread(int port) {
        super("ServerManager");
        this.port = port;

        CommandRegistry.init();

        registryProvider.readAll();

        ItemSlotID.init(registryProvider);
        BlockStateID.init(registryProvider);
        EntityID.init(registryProvider);
        ParticleID.init(registryProvider);

        eventManager.register(new DefaultServerLogicListener());
    }


    @Override
    public void run() {
        aliveKeeperThread.start();
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (serverSocket.isBound() && !Thread.currentThread().isInterrupted()) {
                Socket socket = serverSocket.accept();

                if(NoDOS.checkDDOS(socket)) {
                    socket.close();
                    continue;
                }

                serverConnectionThreadList.removeIf(connectionThread -> {
                   return !connectionThread.isAlive();
                });

                try {
                    ServerConnectionThread connectionThread = new ServerConnectionThread(socket, this);
                    serverConnectionThreadList.add(connectionThread);
                    connectionThread.start();
                } catch (Throwable ignored) {

                }

            }
        } catch (Throwable throwable) {

        }
    }

    public void broadcast(Packet packet, ServerConnectionThread... exclude) {
        List<ServerConnectionThread> e = Arrays.asList(exclude);
        for (ServerConnectionThread connectionThread : getServerConnectionThreadList()) {
            if (connectionThread != null) {
                if(!e.contains(connectionThread)) {
                    connectionThread.send(packet);
                }
            }
        }
    }

    public int getPort() {
        return port;
    }

    public List<ServerConnectionThread> getServerConnectionThreadList() {
        return new ArrayList<>(serverConnectionThreadList);
    }

    public void removeInvalidConnections() {
        synchronized (serverConnectionThreadList) {
            serverConnectionThreadList.removeIf(connectionThread -> {
                return !connectionThread.isAlive();
            });
        }
    }

    public List<ServerConnectionThread> getServerConnectionThreadListMutable() {
        return serverConnectionThreadList;
    }
}
