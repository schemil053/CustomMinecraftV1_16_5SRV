package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.net.data.BlockPosition;
import de.emilschlampp.customMinecraftServer.net.data.Slot;
import de.emilschlampp.customMinecraftServer.utils.PacketPlayer;
import de.emilschlampp.customMinecraftServer.utils.entity.Entity;
import de.emilschlampp.customMinecraftServer.utils.entity.ItemEntity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class InPlayerDiggingPacket extends Packet {
    private int status;
    private BlockPosition location;
    private byte face;

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x1B;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        status = readVarInt(inputStream);
        location = readPosition(inputStream);
        face = (byte) inputStream.read();
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {
        if(status == Status.DROP.status) {
            Slot slot = PacketPlayer.get(connectionThread).inventory.getChoosen();
            if(slot == null) {
                return;
            }

            slot = new Slot(slot.isPresent(), slot.getId(), slot.getCount(), slot.getNbt());
            Slot drop = new Slot(slot.isPresent(), slot.getId(), (byte) 1, slot.getNbt());

            Entity entity = new ItemEntity(PacketPlayer.get(connectionThread).getLocation(), drop);
            entity.spawn(PacketPlayer.get(connectionThread));

            slot.setCount((byte) (slot.getCount()-1));
            if(slot.getCount() <= 0) {
                PacketPlayer.get(connectionThread).inventory.setChoosen(null);
            } else {
                PacketPlayer.get(connectionThread).inventory.setChoosen(slot);
            }
        }
    }

    public static enum Status {
        START_DIGGING(0),
        CANCEL_DIGGING(1),
        FINISH_DIGGING(2),
        DROP_STACK(3),
        DROP(4),
        SHOOT(5),
        SWAP_ITEM(6)
        ;

        public final int status;

        Status(int status) {
            this.status = status;
        }
    }

    public static enum Face {
        BOTTOM(0, 0, -1, 0),
        TOP(1, 0, 1, 0),
        NORTH(1, 0, 0, -1),
        SOUTH(1, 0, 0, 1),
        WEST(1, -1, 0, 0),
        EAST(1,1, 0, 0),
        ;

        public final byte face;
        public final int offx, offy, offz;

        Face(int face, int offx, int offy, int offz) {
            this.face = (byte) face;
            this.offx = offx;
            this.offy = offy;
            this.offz = offz;
        }
    }
}
