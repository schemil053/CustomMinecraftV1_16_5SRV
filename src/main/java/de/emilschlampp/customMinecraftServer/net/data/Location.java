package de.emilschlampp.customMinecraftServer.net.data;

public class Location {
    private double x, y, z;
    private float yaw, pitch;

    public Location(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public Location setYaw(float yaw) {
        this.yaw = yaw;
        return this;
    }

    public float getPitch() {
        return pitch;
    }

    public Location setPitch(float pitch) {
        this.pitch = pitch;
        return this;
    }

    public double getX() {
        return x;
    }

    public Location setX(double x) {
        this.x = x;
        return this;
    }

    public double getY() {
        return y;
    }

    public Location setY(double y) {
        this.y = y;
        return this;
    }

    public double getZ() {
        return z;
    }

    public Location setZ(double z) {
        this.z = z;
        return this;
    }
}
