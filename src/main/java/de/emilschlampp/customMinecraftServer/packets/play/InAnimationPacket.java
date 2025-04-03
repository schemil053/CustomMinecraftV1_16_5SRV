package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.utils.PacketPlayer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.OutputStream;

public class InAnimationPacket extends Packet {
    private int handEnum;

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x2C;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        handEnum = readVarInt(inputStream);
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {
        if(handEnum == Hand.MAIN_HAND.hand) {
            connectionThread.serverThread.broadcast(new OutAnimationPacket(
                    PacketPlayer.get(connectionThread).entityID,
                    OutAnimationPacket.Action.SWING_MAIN_ARM.action
            ), connectionThread);
            return;
        }
        if(handEnum == Hand.OFF_HAND.hand) {
            connectionThread.serverThread.broadcast(new OutAnimationPacket(
                    PacketPlayer.get(connectionThread).entityID,
                    OutAnimationPacket.Action.SWING_OFFHAND.action
            ), connectionThread);
            return;
        }
    }

    public static enum Hand {
        MAIN_HAND(0),
        OFF_HAND(1);

        public final int hand;

        Hand(int hand) {
            this.hand = hand;
        }
    }
}
