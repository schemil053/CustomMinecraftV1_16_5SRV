package de.emilschlampp.customMinecraftServer.utils;

import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;

import java.util.ArrayList;
import java.util.List;

public class Command {
    private final String name;

    public Command(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    public void run(ServerConnectionThread connectionThread, String[] args) {

    }

    public List<String> tab(ServerConnectionThread connectionThread, String[] args) {
        return new ArrayList<>();
    }
}
