package de.emilschlampp.customMinecraftServer.net.data.watcher;

import com.sun.org.apache.bcel.internal.generic.DUP;
import de.emilschlampp.customMinecraftServer.net.NetUtils;
import de.emilschlampp.customMinecraftServer.net.data.Slot;
import de.emilschlampp.customMinecraftServer.utils.generated.ParticleID;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Particle {
    private int id;

    private int state; //minecraft:block, id 3 | minecraft:falling_dust, id 23

    private float red, green, blue, scale; //minecraft:dust, id 14

    private Slot slot; //minecraft:item, id 32


    public Particle(int id) {
        this.id = id;
    }

    public Particle() {
    }

    public Particle(int id, int state) {
        this.id = id;
        this.state = state;
    }

    public Particle(int id, float red, float green, float blue, float scale) {
        this.id = id;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.scale = scale;
    }

    public Particle(int id, Slot slot) {
        this.id = id;
        this.slot = slot;
    }

    public int getId() {
        return id;
    }

    public Particle setId(int id) {
        this.id = id;
        return this;
    }

    public int getState() {
        return state;
    }

    public Particle setState(int state) {
        this.state = state;
        return this;
    }

    public float getRed() {
        return red;
    }

    public Particle setRed(float red) {
        this.red = red;
        return this;
    }

    public float getGreen() {
        return green;
    }

    public Particle setGreen(float green) {
        this.green = green;
        return this;
    }

    public float getBlue() {
        return blue;
    }

    public Particle setBlue(float blue) {
        this.blue = blue;
        return this;
    }

    public float getScale() {
        return scale;
    }

    public Particle setScale(float scale) {
        this.scale = scale;
        return this;
    }

    public Slot getSlot() {
        return slot;
    }

    public Particle setSlot(Slot slot) {
        this.slot = slot;
        return this;
    }

    public void writeData(OutputStream outputStream) throws IOException {
        if(id == ParticleID.BLOCK_ID || id == ParticleID.FALLING_DUST_ID) {
            NetUtils.writeVarInt(state, outputStream);
        }

        if(id == ParticleID.DUST_ID) {
            NetUtils.writeFloat(red, outputStream);
            NetUtils.writeFloat(green, outputStream);
            NetUtils.writeFloat(blue, outputStream);
            NetUtils.writeFloat(scale, outputStream);
        }

        if(id == ParticleID.ITEM_ID) {
            NetUtils.writeSlot(slot, outputStream);
        }
    }

    public void write(OutputStream outputStream) throws IOException {
        NetUtils.writeVarInt(id, outputStream);

        writeData(outputStream);
    }

    public static Particle read(InputStream inputStream) throws IOException {
        Particle particle = new Particle();

        particle.setId(NetUtils.readVarInt(inputStream));

        readData(inputStream, particle);

        return particle;
    }

    public static void readData(InputStream inputStream, Particle particle) throws IOException {
        if(particle.id == ParticleID.BLOCK_ID || particle.id == ParticleID.FALLING_DUST_ID) {
            particle.setState(NetUtils.readVarInt(inputStream));
        }

        if(particle.id == ParticleID.DUST_ID) {
            particle.setRed(NetUtils.readFloat(inputStream));
            particle.setGreen(NetUtils.readFloat(inputStream));
            particle.setBlue(NetUtils.readFloat(inputStream));
            particle.setScale(NetUtils.readFloat(inputStream));
        }

        if(particle.id == ParticleID.ITEM_ID) {
            particle.setSlot(NetUtils.readSlot(inputStream));
        }
    }
}
