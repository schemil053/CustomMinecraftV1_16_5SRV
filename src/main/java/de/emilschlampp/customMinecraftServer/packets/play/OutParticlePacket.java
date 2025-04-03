package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.net.data.watcher.Particle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class OutParticlePacket extends Packet {
    private Particle particle;
    private boolean longDistance;
    private double x, y, z;
    private float ox, oy, oz;
    private float data;
    private int count;

    public OutParticlePacket() {
    }

    public OutParticlePacket(Particle particle, boolean longDistance, double x, double y, double z, float ox, float oy, float oz, float data, int count) {
        this.particle = particle;
        this.longDistance = longDistance;
        this.x = x;
        this.y = y;
        this.z = z;
        this.ox = ox;
        this.oy = oy;
        this.oz = oz;
        this.data = data;
        this.count = count;
    }

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x22;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        writeInt(particle.getId(), outputStream);
        writeBoolean(longDistance, outputStream);
        writeDouble(x, outputStream);
        writeDouble(y, outputStream);
        writeDouble(z, outputStream);
        writeFloat(ox, outputStream);
        writeFloat(oy, outputStream);
        writeFloat(oz, outputStream);
        writeFloat(data, outputStream);
        writeInt(count, outputStream);
        particle.writeData(outputStream);
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {

    }
}
