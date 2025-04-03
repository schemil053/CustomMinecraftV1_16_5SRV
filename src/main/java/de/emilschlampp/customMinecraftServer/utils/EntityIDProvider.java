package de.emilschlampp.customMinecraftServer.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class EntityIDProvider {
    private static final AtomicInteger currentID = new AtomicInteger();

    public static int nextID() {
        return currentID.getAndIncrement();
    }
}
