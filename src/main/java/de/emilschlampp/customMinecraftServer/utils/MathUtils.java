package de.emilschlampp.customMinecraftServer.utils;

public class MathUtils {
    public static byte calcYawPitch(float yaw) {
        return (byte) (yaw * 256 / 360);
    }
}
