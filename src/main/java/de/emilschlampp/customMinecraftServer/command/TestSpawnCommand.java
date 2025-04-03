package de.emilschlampp.customMinecraftServer.command;

import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.net.data.Slot;
import de.emilschlampp.customMinecraftServer.net.data.watcher.DataWatcher;
import de.emilschlampp.customMinecraftServer.packets.play.OutEntityMetadataPacket;
import de.emilschlampp.customMinecraftServer.packets.play.OutSpawnEntityPacket;
import de.emilschlampp.customMinecraftServer.utils.Command;
import de.emilschlampp.customMinecraftServer.utils.EntityIDProvider;
import de.emilschlampp.customMinecraftServer.utils.MathUtils;
import de.emilschlampp.customMinecraftServer.utils.generated.ItemSlotID;

import java.util.UUID;

public class TestSpawnCommand extends Command {
    public TestSpawnCommand() {
        super("testspawn");
    }

    @Override
    public void run(ServerConnectionThread connectionThread, String[] args) {
        int reg = connectionThread.serverThread.registryProvider.getEntityRegistry().lookup("minecraft:item");

        int id = EntityIDProvider.nextID();

        connectionThread.send(new OutSpawnEntityPacket(
                id,
                UUID.randomUUID(),
                reg,
                connectionThread.asPacketPlayer().x,
                connectionThread.asPacketPlayer().y,
                connectionThread.asPacketPlayer().z,
                MathUtils.calcYawPitch(connectionThread.asPacketPlayer().yaw),
                MathUtils.calcYawPitch(connectionThread.asPacketPlayer().pitch),
                2,
                (short) 0, (short) 0, (short) 0
        ));

        DataWatcher watcher = new DataWatcher();

        watcher.put(7, new Slot(ItemSlotID.ACACIA_TRAPDOOR_ID));

        connectionThread.send(new OutEntityMetadataPacket(id, watcher));
    }
}
