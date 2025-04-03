package de.emilschlampp.customMinecraftServer.net.data.watcher;

import de.emilschlampp.customMinecraftServer.net.NetUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Rotation {
    private float x, y, z;

    public Rotation(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Rotation() {
    }

    public float getX() {
        return x;
    }

    public Rotation setX(float x) {
        this.x = x;
        return this;
    }

    public float getY() {
        return y;
    }

    public Rotation setY(float y) {
        this.y = y;
        return this;
    }

    public float getZ() {
        return z;
    }

    public Rotation setZ(float z) {
        this.z = z;
        return this;
    }

    public void write(OutputStream outputStream) throws IOException {
        NetUtils.writeFloat(x, outputStream);
        NetUtils.writeFloat(y, outputStream);
        NetUtils.writeFloat(z, outputStream);
    }

    public static Rotation read(InputStream inputStream) throws IOException {
        Rotation rotation = new Rotation();

        rotation.setX(NetUtils.readFloat(inputStream));
        rotation.setY(NetUtils.readFloat(inputStream));
        rotation.setZ(NetUtils.readFloat(inputStream));

        return rotation;
    }
}
