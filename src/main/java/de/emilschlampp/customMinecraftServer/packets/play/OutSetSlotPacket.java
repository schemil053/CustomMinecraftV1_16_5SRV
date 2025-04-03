package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.net.data.Slot;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class OutSetSlotPacket extends Packet {
    private byte windowID;
    private short slotID;
    private Slot slot;

    public OutSetSlotPacket(byte windowID, short slotID, Slot slot) {
        this.windowID = windowID;
        this.slotID = slotID;
        this.slot = slot;
    }

    public OutSetSlotPacket() {
    }

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x15;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        windowID = (byte) inputStream.read();
        slotID = (short) readShort(inputStream);
        slot = readSlot(inputStream);
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        outputStream.write(windowID);

        writeShort(slotID, outputStream);
        writeSlot(slot, outputStream);

        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {

    }
}
