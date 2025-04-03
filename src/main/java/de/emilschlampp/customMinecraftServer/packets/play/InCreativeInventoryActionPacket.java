package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.net.data.Slot;
import de.emilschlampp.customMinecraftServer.utils.PacketPlayer;
import de.emilschlampp.customMinecraftServer.utils.entity.Entity;
import de.emilschlampp.customMinecraftServer.utils.entity.ItemEntity;
import de.emilschlampp.customMinecraftServer.utils.generated.ItemSlotID;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class InCreativeInventoryActionPacket extends Packet {
    private short slotID;
    private Slot slot;

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x28;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        slotID = (short) readShort(inputStream);
        slot = readSlot(inputStream);

        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {
        System.out.println(connectionThread.serverThread.registryProvider.getItemRegistry().lookup(slot.getId())+"|"+slotID+"|CREATIVE");

        if(PacketPlayer.get(connectionThread).gameMode != 1) {
            PacketPlayer.get(connectionThread).kick("Illegal Action");
            return;
        }

        if(slotID == 45) {
           /* for(int i = 0; i<45; i++) {
                connectionThread.send(new OutSetSlotPacket((byte) 0, (short) i, new Slot(true, ItemSlotID.AIR_ID, (byte) 0, new byte[0])));
            }
            */
            PacketPlayer.get(connectionThread).inventory.clear();
        } else {
            if(slotID < 0) {
                if(slotID == -1) {
                    Entity entity = new ItemEntity(PacketPlayer.get(connectionThread).getLocation(), slot);
                    entity.spawn(PacketPlayer.get(connectionThread));
                }
            } else {
                PacketPlayer.get(connectionThread).inventory.setItem(slotID, slot);
            }
        }
    }
}
