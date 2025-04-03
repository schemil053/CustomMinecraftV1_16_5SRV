package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class OutAnimationPacket extends Packet {
    private int entityID;
    private byte animation;


    public OutAnimationPacket() {
    }

    public OutAnimationPacket(int entityID, byte animation) {
        this.entityID = entityID;
        this.animation = animation;
    }

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x05;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        writeVarInt(entityID, outputStream);
        outputStream.write(animation);
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {

    }

    public static enum Action {
        SWING_MAIN_ARM(0),
        TAKE_DAMAGE(1),
        LEAVE_BED(2),
        SWING_OFFHAND(3),
        CRITICAL_EFFECT(4),
        MAGIC_CRITICAL_EFFECT(5)
        ;
        public final byte action;
        Action(int action) {
            this.action = (byte) action;
        }
    }
}
