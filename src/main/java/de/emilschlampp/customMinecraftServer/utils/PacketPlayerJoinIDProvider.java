package de.emilschlampp.customMinecraftServer.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class PacketPlayerJoinIDProvider {
    private static final AtomicInteger currentID = new AtomicInteger(500);

    public static int nextID() {
        return currentID.getAndIncrement();
    }
}
