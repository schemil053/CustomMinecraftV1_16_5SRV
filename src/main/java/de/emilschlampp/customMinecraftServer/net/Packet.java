package de.emilschlampp.customMinecraftServer.net;

import java.io.*;

public abstract class Packet extends NetUtils {
    public abstract int getState();
    public abstract int getID();
    public abstract Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable;
    public abstract Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable;
    public abstract void handle(ServerConnectionThread connectionThread) throws Throwable;
    public boolean isClientbound() {
        return getClass().getSimpleName().startsWith("Out");
    }

    public Packet newInstance() {
        try {
            return getClass().newInstance();
        } catch (InstantiationException e) {

        } catch (IllegalAccessException e) {

        }
        throw new RuntimeException();
    }
}
