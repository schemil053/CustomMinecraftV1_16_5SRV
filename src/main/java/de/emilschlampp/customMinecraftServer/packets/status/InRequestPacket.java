package de.emilschlampp.customMinecraftServer.packets.status;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.utils.PacketPlayer;
import de.emilschlampp.customMinecraftServer.utils.json.JsonArray;
import de.emilschlampp.customMinecraftServer.utils.json.JsonObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.util.List;

public class InRequestPacket extends Packet {
    @Override
    public int getState() {
        return 3;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {
        JsonObject main = new JsonObject();

        JsonObject version = new JsonObject();
        version.put("name", "1.16.5");
        version.put("protocol", 754);

        main.put("version", version);

        List<PacketPlayer> online = PacketPlayer.getOnline(connectionThread);

        JsonObject players = new JsonObject();
        players.put("max", online.size()+1);
        players.put("online", online.size());

        JsonArray sample = new JsonArray();
        for (PacketPlayer packetPlayer : online) {
            JsonObject object = new JsonObject();
            object.put("name", packetPlayer.name);
            object.put("id", packetPlayer.uuid.toString());
            sample.add(object);
        }

        players.put("sample", sample);

        main.put("players", players);

        JsonObject description = new JsonObject();
        description.put("text", "SchemilMCServer V0.0.1");

        main.put("description", description);


        connectionThread.send(new OutResponsePacket(
                main.toString()
        ));
    }
}
