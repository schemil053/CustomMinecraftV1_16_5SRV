package de.emilschlampp.customMinecraftServer.utils;

import de.emilschlampp.customMinecraftServer.net.data.Slot;
import de.emilschlampp.customMinecraftServer.packets.play.OutSetSlotPacket;

import java.util.HashMap;
import java.util.Map;

public class PlayerInventory {
    private final Map<Short, Slot> slotDataMap = new HashMap<>();
    private final PacketPlayer owner;
    private int hotBarChoosen;

    public PlayerInventory(PacketPlayer owner) {
        this.owner = owner;
    }

    public void setItem(short slotID, Slot slot) {
        if(slot != null) {
            slotDataMap.put(slotID, slot);
        } else {
            slotDataMap.remove(slotID);
        }
        resend();
    }

    public void clear() {
        slotDataMap.clear();
        resend();
    }

    public void resend() {
        for(short i = 0; i<45; i++) {
            owner.send(new OutSetSlotPacket((byte) 0, i, slotDataMap.getOrDefault(i, new Slot().setPresent(false))));
        }
    }

    public Slot get(short slot) {
        return slotDataMap.get(slot);
    }

    public int getHotBarChoosen() {
        return hotBarChoosen;
    }

    public PlayerInventory setHotBarChoosen(int hotBarChoosen) {
        this.hotBarChoosen = hotBarChoosen;
        return this;
    }

    public Slot getHotbar(int hotBarChoosen) {
        return get(HotBarSlot.valueOf("HOTBAR_"+hotBarChoosen).slotNum);
    }

    public void setHotbar(int hotBarChoosen, Slot slot) {
        setItem(HotBarSlot.valueOf("HOTBAR_"+hotBarChoosen).slotNum, slot);
    }

    public Slot getChoosen() {
        return getHotbar(getHotBarChoosen());
    }

    public void setChoosen(Slot slot) {
        setHotbar(getHotBarChoosen(), slot);
    }

    public static enum HotBarSlot {
        HOTBAR_0(36),
        HOTBAR_1(37),
        HOTBAR_2(38),
        HOTBAR_3(39),
        HOTBAR_4(40),
        HOTBAR_5(41),
        HOTBAR_6(42),
        HOTBAR_7(43),
        HOTBAR_8(44)

        ;


        public final int slotID;
        public final short slotNum;

        HotBarSlot(int slotID) {
            this.slotID = slotID;
            this.slotNum = (short) slotID;
        }
    }
}
