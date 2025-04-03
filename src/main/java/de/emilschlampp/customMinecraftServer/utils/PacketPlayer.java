package de.emilschlampp.customMinecraftServer.utils;

import de.emilschlampp.customMinecraftServer.net.NetUtils;
import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.net.ServerThread;
import de.emilschlampp.customMinecraftServer.net.data.BlockPosition;
import de.emilschlampp.customMinecraftServer.net.data.Location;
import de.emilschlampp.customMinecraftServer.packets.play.*;
import de.emilschlampp.customMinecraftServer.utils.event.impl.player.PlayerMoveEvent;
import de.emilschlampp.customMinecraftServer.utils.generated.BlockStateID;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PacketPlayer {
    public UUID uuid;
    public String name;
    public PacketPlayerProperty[] properties;
    public int gameMode;
    public int ping;
    public boolean displayName;
    public String displayNameString;
    public int entityID;

    public double x, y, z;
    public float yaw, pitch;
    public boolean onGround;
    public ServerConnectionThread serverConnectionThread;
    public final PlayerInventory inventory = new PlayerInventory(this);
    public final int joinID = PacketPlayerJoinIDProvider.nextID();

    public PacketPlayer(UUID uuid, String name, int entityID, PacketPlayerProperty[] properties, int gameMode, int ping, boolean displayName, String displayNameString, ServerConnectionThread serverConnectionThread) {
        this.uuid = uuid;
        this.name = name;
        this.entityID = entityID;
        this.properties = properties;
        this.gameMode = gameMode;
        this.ping = ping;
        this.displayName = displayName;
        this.displayNameString = displayNameString;
        this.serverConnectionThread = serverConnectionThread;
    }

    public void rebroadcastPosition() {
        serverConnectionThread.serverThread.broadcast(
                new OutEntityTeleportPacket(entityID,
                        x, y, z,
                        MathUtils.calcYawPitch(yaw), MathUtils.calcYawPitch(pitch), onGround),
                serverConnectionThread
        );

        serverConnectionThread.serverThread.broadcast(
                new OutEntityLookPacket(entityID, MathUtils.calcYawPitch(yaw)),
                serverConnectionThread
        );


        serverConnectionThread.serverThread.eventManager.call(new PlayerMoveEvent(this));
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;

        send(new OutChangeGameStatePacket(OutChangeGameStatePacket.Action.CHANGE_GAMEMODE.reason, gameMode));
        serverConnectionThread.serverThread.broadcast(
                new OutPlayerInfoActionPacket(OutPlayerInfoActionPacket.Action.UPDATE_GAMEMODE.id, new PacketPlayer[]{this})
        );
    }

    public void sendChunk(int x, int z) throws IOException {

        //TODO ChunkData
         byte[] biomes = new byte[1024];
        Arrays.fill(biomes, (byte) 127);
      /*   serverConnectionThread.send(new OutChunkDataPacket(
                x, z, true, 0x01, NetUtils.readAllBytes(getClass().getResourceAsStream("/chunk_heightmap.dat")),
                biomes, (short) 1, (byte) 8, 256, NetUtils.readAllBytes(getClass().getResourceAsStream("/chunk_palette.dat")),
                512, NetUtils.readAllBytes(getClass().getResourceAsStream("/chunk_content.dat"))
        ));

         */

        serverConnectionThread.send(new OutChunkDataPacket(
                x, z, true, 0x01, NetUtils.readAllBytes(getClass().getResourceAsStream("/chunk_heightmap.dat")),
                biomes, (short) 256, (byte) 8, 256, NetUtils.readAllBytes(getClass().getResourceAsStream("/chunk_palette.dat")),
                512, NetUtils.readAllBytes(getClass().getResourceAsStream("/chunk_content.dat"))
        ));




    /*  ChunkSection chunkSection = new ChunkSection();

        for(int Ax = 0; Ax<16; Ax++) {
            for(int Az = 0; Az<16; Az++) {
                for(int Ay = 0; Ay < 1; Ay++) {
                    chunkSection.setBlock(Ax, Ay, Az, BlockStateID.SPAWNER_ID);
                }
            }
        }

        for(int Ay = 0; Ay < 16; Ay++) {
            chunkSection.setBlock(0, Ay, 0, Ay);
        }

        chunkSection.setBlock(0, 1, 0, 0);

      //  System.out.println(NetUtils.readAllBytes(getClass().getResourceAsStream("/chunk_heightmap.dat")).length);
      //  System.out.println(chunk.generateHeightmap().length);

        byte[] biomes = new byte[1024];
        Arrays.fill(biomes, (byte) 127);
        chunkSection.generateChunkPalette();
        serverConnectionThread.send(new OutChunkDataPacket(
                x, z, true, 0x01, chunkSection.generateHeightmap(),
                biomes, Short.MAX_VALUE, (byte) 8,//8
                //256
                chunkSection.getR()
                , chunkSection.generateChunkPalette(),
                512, chunkSection.generateContent()
        ));

        System.out.println(chunkSection.getR());





        Chunk chunk = new Chunk(x, z);


        for(int Ax = 0; Ax<16; Ax++) {
            for(int Az = 0; Az<16; Az++) {
                for(int Ay = 0; Ay < 3; Ay++) {
                    chunk.setBlock(Ax, Ay, Az, BlockStateID.STONE_ID);
                }
            }
        }

        for(int Ay = 0; Ay < 256; Ay++) {
            chunk.setBlock(5, Ay, 0, Ay);
        }



        chunk.sendToPlayer(this);
*/

    }

    public void send(Packet packet) {
        serverConnectionThread.send(packet);
    }

    public void sendMessagePlain(String s) {
        serverConnectionThread.send(new OutChatMessagePacket(JSONUtil.simpleText(s)));
    }

    public void sendMessage(String s) {
        serverConnectionThread.send(new OutChatMessagePacket(JSONUtil.coloredText(s)));
    }

    public void sendMessageRaw(String s) {
        serverConnectionThread.send(new OutChatMessagePacket(s));
    }

    public BlockPosition getPosition() {
        return new BlockPosition(calcVal((int) Math.round(x)), calcVal((int) Math.round(y)), calcVal((int) Math.round(z)));
    }

    public Location getLocation() {
        return new Location(x, y, z, yaw, pitch);
    }

    private int calcVal(int v) {
        return v;
        //if(v < 0){
        //    return v-1;
        //}
    }

    public void kick(String reason) {
        serverConnectionThread.send(new OutDisconnectPacket(JSONUtil.coloredText(reason)));
        serverConnectionThread.markForClose();
    }

    public void kickRaw(String json) {
        serverConnectionThread.send(new OutDisconnectPacket(json));
        serverConnectionThread.markForClose();
    }

    public void kickPlain(String reasonPlain) {
        serverConnectionThread.send(new OutDisconnectPacket(JSONUtil.simpleText(reasonPlain)));
        serverConnectionThread.markForClose();
    }

    public static class PacketPlayerProperty {
        public String name;
        public String value;
        public boolean signed;
        public String signature;
    }

    public static PacketPlayer get(ServerConnectionThread serverConnectionThread) {
        return (PacketPlayer) serverConnectionThread.dataMap.get("packet-player");
    }

    public static List<PacketPlayer> getOnline(ServerConnectionThread serverConnectionThread) {
        return getOnline(serverConnectionThread.serverThread);
    }

    public static List<PacketPlayer> getOnline(ServerThread serverThread) {
        return serverThread.getServerConnectionThreadList().stream().filter(c -> {
            return c.state == 2;
        }).map(PacketPlayer::get).collect(Collectors.toList());
    }

    public static List<PacketPlayer> get(ServerThread server, String query) {
        if (query.endsWith("@a")) {
            return getOnline(server);
        }
        return getOnline(server).stream().filter(p -> p.name.equals(query)).collect(Collectors.toList());
    }

    public static PacketPlayer getFirst(ServerThread server, String name) {
        return getOnline(server).stream().filter(p -> p.name.equals(name)).findFirst().orElse(null);
    }
}
