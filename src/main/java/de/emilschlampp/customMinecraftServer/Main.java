package de.emilschlampp.customMinecraftServer;

import de.emilschlampp.customMinecraftServer.net.ServerThread;

public class Main {
    public static void main(String[] args) {
        new ServerThread(25565).start();
    }
}
