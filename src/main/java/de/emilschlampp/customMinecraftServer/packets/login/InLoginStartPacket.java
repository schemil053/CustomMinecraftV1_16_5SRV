package de.emilschlampp.customMinecraftServer.packets.login;

import de.emilschlampp.customMinecraftServer.net.NetUtils;
import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.net.data.BlockPosition;
import de.emilschlampp.customMinecraftServer.net.data.Node;
import de.emilschlampp.customMinecraftServer.packets.play.*;
import de.emilschlampp.customMinecraftServer.utils.CommandRegistry;
import de.emilschlampp.customMinecraftServer.utils.EntityIDProvider;
import de.emilschlampp.customMinecraftServer.utils.JSONUtil;
import de.emilschlampp.customMinecraftServer.utils.PacketPlayer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class InLoginStartPacket extends Packet {
    private String userName;

    @Override
    public int getState() {
        return 1;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        userName = NetUtils.readVarString(dataInputStream);
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {
        if(connectionThread.dataMap.containsKey("protocol-version")) {
            if(!Objects.equals(connectionThread.dataMap.get("protocol-version"), 754)) {
                connectionThread.send(new OutLoginDisconnectPacket(JSONUtil.simpleText("Use protocol 754! (1.16.5)")));
                connectionThread.markForClose();
                return;
            }
        }

        System.out.println(userName);

        UUID uuid = UUID.randomUUID();

        int gm = OutChangeGameStatePacket.GameMode.CREATIVE.id;

        connectionThread.send(new OutLoginSuccessPacket(uuid, userName));
        connectionThread.state = 2;

        int entityID = EntityIDProvider.nextID();//connectionThread.serverThread.getServerConnectionThreadList().size()-1;

        PacketPlayer current = new PacketPlayer(
                uuid, userName, entityID, new PacketPlayer.PacketPlayerProperty[0], gm, 1, false, "", connectionThread
        );

        connectionThread.dataMap.put("name", userName);
        connectionThread.dataMap.put("uuid", uuid);
        connectionThread.dataMap.put("packet-player", current);
        connectionThread.dataMap.put("entity-id", entityID);

        current.y = 1;

        connectionThread.send(new OutJoinGamePacket(
                entityID, false, (byte) gm, (byte) 3, 1, "minecraft:overworld",
                readAllBytes(getClass().getResourceAsStream("/dimension_settings.dat")),
                readAllBytes(getClass().getResourceAsStream("/dimension_gameRules.dat")),
                "minecraft:overworld", 0, 10, 12, false, true, false, true

        ));

        connectionThread.send(new OutPositionLookPacket(0D, 1D, 0D, 0F, 0F, (byte) 0x00));
        connectionThread.send(new OutServerDifficultyPacket((byte) 0, false));
        connectionThread.send(new OutSpawnPositionPacket(new BlockPosition(0, 0, 0)));


        //TODO: ChunkData
        for (int x = -2; x < 2; x++) {
            for (int y = -2; y < 2; y++) {
                current.sendChunk(x, y);
            }
        }

        List<PacketPlayer> packetPlayerList = new ArrayList<>();
        for (ServerConnectionThread serverConnectionThread : connectionThread.serverThread.getServerConnectionThreadList()) {
            if (!serverConnectionThread.equals(connectionThread)) {
                if (serverConnectionThread.dataMap.containsKey("packet-player")) {
                    packetPlayerList.add((PacketPlayer) serverConnectionThread.dataMap.get("packet-player"));
                }
            }
        }
        connectionThread.send(new OutPlayerInfoActionPacket(0, packetPlayerList.toArray(new PacketPlayer[0])));

        for (PacketPlayer packetPlayer : packetPlayerList) {
            connectionThread.send(
                    new OutSpawnPlayerPacket(packetPlayer.entityID,
                            packetPlayer.uuid, packetPlayer.x, packetPlayer.y, packetPlayer.z, (byte) 0, (byte) 0)
            );
        }


        connectionThread.serverThread.broadcast(new OutPlayerInfoActionPacket(
                0,
                new PacketPlayer[]{
                        (PacketPlayer) connectionThread.dataMap.get("packet-player")
                }
        ));

        connectionThread.serverThread.broadcast(new OutSpawnPlayerPacket(entityID,
                uuid, 0, 1, 0, (byte) 0, (byte) 0), connectionThread);


        connectionThread.send(CommandRegistry.getDeclarations());

        connectionThread.send(new OutEntityStatusPacket(entityID, OutEntityStatusPacket.Status.PLAYER_OP_PERM_LEVEL4.status));
    }
}
