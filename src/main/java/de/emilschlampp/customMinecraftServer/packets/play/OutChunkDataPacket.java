package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class OutChunkDataPacket extends Packet {
    private int x, z;
    private boolean fullChunk;
    private int sections;
    private byte[] heightMaps;
    private byte[] biomes;

    private short airBlocks;
    private byte bitsPerBlock;
    private int paletteLength;
    private byte[] palette;
    private int chunkDataLength;
    private byte[] chunkData;

    public OutChunkDataPacket() {
    }

    public OutChunkDataPacket(int x, int z, boolean fullChunk, int sections, byte[] heightMaps, byte[] biomes, short airBlocks, byte bitsPerBlock, int paletteLength, byte[] palette, int chunkDataLength, byte[] chunkData) {
        this.x = x;
        this.z = z;
        this.fullChunk = fullChunk;
        this.sections = sections;
        this.heightMaps = heightMaps;
        this.biomes = biomes;
        this.airBlocks = airBlocks;
        this.bitsPerBlock = bitsPerBlock;
        this.paletteLength = paletteLength;
        this.palette = palette;
        this.chunkDataLength = chunkDataLength;
        this.chunkData = chunkData;
    }

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x20;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        writeInt(x, outputStream);
        writeInt(z, outputStream);

        writeBoolean(fullChunk, outputStream);
        writeVarInt(sections, outputStream);

        outputStream.write(heightMaps);
        if(fullChunk) {
            writeVarInt(biomes.length, outputStream);
            outputStream.write(biomes);
        }

        ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
        writeShort(airBlocks, outputStream1);
        outputStream1.write(bitsPerBlock);
        writeVarInt(paletteLength, outputStream1);
        outputStream1.write(palette);
        writeVarInt(chunkDataLength, outputStream1);
        outputStream1.write(chunkData);

        writeVarInt(outputStream1.size(), outputStream);
        outputStream.write(outputStream1.toByteArray());


        writeVarInt(0, outputStream); //TODO Tile-Entities
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {

    }
}
