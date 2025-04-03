package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.utils.PacketPlayer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class OutPlayerInfoActionPacket extends Packet {
    private int action;
    private PacketPlayer[] players;

    public OutPlayerInfoActionPacket(int action, PacketPlayer[] players) {
        this.action = action;
        this.players = players;
    }

    public OutPlayerInfoActionPacket() {
    }

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x32;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        writeVarInt(action, outputStream);
        writeVarInt(players.length, outputStream);

        for (PacketPlayer player : players) {
            writeVarUUID(player.uuid, outputStream);

            if(action == 0) {
                writeVarString(player.name, outputStream);

                writeVarInt(player.properties.length, outputStream);
                for (PacketPlayer.PacketPlayerProperty property : player.properties) {
                    writeVarString(property.name, outputStream);
                    writeVarString(property.value, outputStream);
                    writeBoolean(property.signed, outputStream);
                    if (property.signed) {
                        writeVarString(property.signature, outputStream);
                    }
                }

                writeVarInt(player.gameMode, outputStream);
                writeVarInt(player.ping, outputStream);
                writeBoolean(player.displayName, outputStream);
                if (player.displayName) {
                    writeVarString(player.displayNameString, outputStream);
                }
            }

            if(action == 1) {
                writeVarInt(player.gameMode, outputStream);
            }

            if(action == 2) {
                writeVarInt(player.ping, outputStream);
            }

            if(action == 3) {
                writeBoolean(player.displayName, outputStream);
                if (player.displayName) {
                    writeVarString(player.displayNameString, outputStream);
                }
            }
        }

        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {

    }

    public static enum Action {
        ADD_PLAYER(0),
        UPDATE_GAMEMODE(1),
        UPDATE_LATENCY(2),
        UPDATE_DISPLAY_NAME(3),
        REMOVE_PLAYER(4)

        ;

        public final int id;
        Action(int id) {
            this.id = id;
        }
    }
}
