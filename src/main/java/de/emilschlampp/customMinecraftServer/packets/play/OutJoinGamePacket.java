package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class OutJoinGamePacket extends Packet {
    public OutJoinGamePacket() {
    }

    private int entityID;
    private boolean hardcore;
    private byte gameMode;
    private byte prevGameMode;
    private int dimensionID;
    private String dimensionName;
    private byte[] dimensionData;
    private byte[] worldGameRules;
    private String spawnWorld;
    private long seed;
    private int maxPlayers;
    private int viewDistance;
    private boolean reducedDebugInfo;
    private boolean enabledRespawnScreen;
    private boolean isDebugWorld;
    private boolean isFlat;

    public OutJoinGamePacket(int entityID, boolean hardcore, byte gameMode, byte prevGameMode, int dimensionID, String dimensionName, byte[] dimensionData, byte[] worldGameRules, String spawnWorld, long seed, int maxPlayers, int viewDistance, boolean reducedDebugInfo, boolean enabledRespawnScreen, boolean isDebugWorld, boolean isFlat) {
        this.entityID = entityID;
        this.hardcore = hardcore;
        this.gameMode = gameMode;
        this.prevGameMode = prevGameMode;
        this.dimensionID = dimensionID;
        this.dimensionName = dimensionName;
        this.dimensionData = dimensionData;
        this.worldGameRules = worldGameRules;
        this.spawnWorld = spawnWorld;
        this.seed = seed;
        this.maxPlayers = maxPlayers;
        this.viewDistance = viewDistance;
        this.reducedDebugInfo = reducedDebugInfo;
        this.enabledRespawnScreen = enabledRespawnScreen;
        this.isDebugWorld = isDebugWorld;
        this.isFlat = isFlat;
    }

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x24;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        writeInt(entityID, outputStream);
        writeBoolean(hardcore, outputStream);
        outputStream.write(gameMode);
        outputStream.write(prevGameMode);

        writeVarInt(dimensionID, outputStream);
        writeVarString(dimensionName, outputStream);

        outputStream.write(dimensionData);
        outputStream.write(worldGameRules);

        writeVarString(spawnWorld, outputStream);
        writeLong(seed, outputStream);
        writeVarInt(maxPlayers, outputStream);
        writeVarInt(viewDistance, outputStream);

        writeBoolean(reducedDebugInfo, outputStream);
        writeBoolean(enabledRespawnScreen, outputStream);
        writeBoolean(isDebugWorld, outputStream);
        writeBoolean(isFlat, outputStream);

        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) {

    }
}
