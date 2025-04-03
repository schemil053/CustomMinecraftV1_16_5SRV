package de.emilschlampp.customMinecraftServer.net.data;

public class Slot {
    private boolean present;
    private int id;
    private byte count;
    private byte[] nbt;

    public Slot(boolean present, int id, byte count, byte[] nbt) {
        this.present = present;
        this.id = id;
        this.count = count;
        this.nbt = nbt;
    }

    public Slot(int id) {
        this(true, id, (byte) 1, new byte[0]);
    }

    public Slot() {

    }

    public boolean isPresent() {
        return present;
    }

    public Slot setPresent(boolean present) {
        this.present = present;
        return this;
    }

    public int getId() {
        return id;
    }

    public Slot setId(int id) {
        this.id = id;
        return this;
    }

    public byte getCount() {
        return count;
    }

    public Slot setCount(byte count) {
        this.count = count;
        return this;
    }

    public byte[] getNbt() {
        return nbt;
    }

    public Slot setNbt(byte[] nbt) {
        this.nbt = nbt;
        return this;
    }
}
