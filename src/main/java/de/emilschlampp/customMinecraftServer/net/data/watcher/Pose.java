package de.emilschlampp.customMinecraftServer.net.data.watcher;

public enum Pose {
    STANDING(0),
    FALL_FLYING(1),
    SLEEPING(2),
    SWIMMING(3),
    SPIN_ATTACK(4),
    SNEAKING(5),
    DYING(6);

    public final int val;

    Pose(int val) {
        this.val = val;
    }
}
