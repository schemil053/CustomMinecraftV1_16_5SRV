package de.emilschlampp.customMinecraftServer.net.data.watcher;

public enum Direction {
    DOWN(0),
    UP(1),
    NORTH(2),
    SOUTH(3),
    WEST(4),
    EAST(5);

    public final int val;

    Direction(int val) {
        this.val = val;
    }
}
