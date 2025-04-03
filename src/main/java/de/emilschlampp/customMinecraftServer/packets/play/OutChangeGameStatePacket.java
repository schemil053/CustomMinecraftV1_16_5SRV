package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class OutChangeGameStatePacket extends Packet {
    private byte reason;
    private float value;

    public OutChangeGameStatePacket(byte reason, float value) {
        this.reason = reason;
        this.value = value;
    }

    public OutChangeGameStatePacket() {
    }

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x1D;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        reason = (byte) inputStream.read();
        value = readFloat(inputStream);
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        outputStream.write(reason);
        writeFloat(value, outputStream);
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {

    }

    public static enum Action {
        NO_RESPAWN_BLOCK(0), //Sends message 'block.minecraft.spawn.not_valid'(You have no home bed or charged respawn anchor, or it was obstructed) to the client.
        END_RAIN(1),
        BEGIN_RAIN2(2),
        CHANGE_GAMEMODE(3), //0: Survival, 1: Creative, 2: Adventure, 3: Spectator
        WIN_GAME(4), //0: Just respawn player. 1: Roll the credits and respawn player. Note that 1 is only sent by notchian server when player has not yet achieved advancement "The end?", else 0 is sent.
        DEMO_EVENT(5), //0: Show welcome to demo screen 101: Tell movement controls 102: Tell jump control 103: Tell inventory control 104: Tell that the demo is over and print a message about how to take a screenshot.
        ARROW_HIT_PLAYER(6), //Sent when any player is struck by an arrow.
        RAIN_LEVEL_CHANGE(7), //Skycolor and lighning change, val > 20 can cause hud color change
        THUNDER_LEVEL_CHANGE(8), //Skycolor and lighning change (but no rain), val > 20 can cause hud color change
        PUFFERFISH_STING(9),
        ELDER_GUARDIAN_APPEARANCE(10),
        RESPAWN_SCREEN(11) //0: Enable respawn screen, 1: Immediately respawn (sent when the doImmediateRespawn gamerule changes).
        ;
        public final byte reason;

        Action(int reason) {
            this.reason = (byte) reason;
        }
    }

    public static enum GameMode {
        SURVIVAL(0),
        CREATIVE(1),
        ADVENTURE(2),
        SPECTATOR(3);
        public float value;
        public int id;
        GameMode(int v) {
            this.value = v;
            this.id = v;
        }
    }
}
