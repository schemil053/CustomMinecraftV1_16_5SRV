package de.emilschlampp.customMinecraftServer.net.data;

public class NBT {
    private byte[] val;

    public NBT(byte[] val) {
        this.val = val;
    }

    public NBT() {
    }

    public byte[] getVal() {
        return val;
    }

    public NBT setVal(byte[] val) {
        this.val = val;
        return this;
    }
}
