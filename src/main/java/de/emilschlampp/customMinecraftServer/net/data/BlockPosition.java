package de.emilschlampp.customMinecraftServer.net.data;

public class BlockPosition {
    private int x, y, z;

    public BlockPosition(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockPosition() {
    }

    public int getX() {
        return x;
    }

    public BlockPosition setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public BlockPosition setY(int y) {
        this.y = y;
        return this;
    }

    public int getZ() {
        return z;
    }

    public BlockPosition setZ(int z) {
        this.z = z;
        return this;
    }

    public int getChunkX() {
        return getX()/16;
    }

    public int getChunkZ() {
        return getZ()/16;
    }
}
