package de.emilschlampp.customMinecraftServer.utils;

import de.emilschlampp.customMinecraftServer.packets.play.OutChunkDataPacket;

import java.util.Arrays;

public class Chunk {
    private ChunkSection[] sections;
    private int x, z;

    public Chunk(int x, int z) {
        this.x = x;
        this.z = z;
        sections = new ChunkSection[16];

        for(int y = 0; y<sections.length; y++) {
            sections[y] = new ChunkSection();
        }
    }

    public void sendToPlayer(PacketPlayer player) {
        for(int y = 0; y<sections.length; y++) {
            sendToPlayer(player, y);
        }
    }

    public void setBlock(int x, int y, int z, int blockID) {
        int section = y/16;
        int relPos = y-(section*16);

        System.out.println("SEC:"+section+"|REL:"+relPos+"|M:"+y);

        sections[section].setBlock(x, relPos, z, blockID);
    }

    public int getBlock(int x, int y, int z) {
        int section = y/16;
        int relPos = y-(section*16);

        return sections[section].getBlock(x, relPos, z);
    }

    public void sendToPlayer(PacketPlayer player, int section) {
        try {
        //    System.out.println(section);
            ChunkSection chunkSection = sections[section];
            byte[] biomes = new byte[1024];
            Arrays.fill(biomes, (byte) 127);
            chunkSection.generateChunkPalette();
            player.send(new OutChunkDataPacket(
                    x, z, section == 0, section, chunkSection.generateHeightmap(),
                    biomes, //Short.MAX_VALUE
                    chunkSection.calcNonAir()
                    , (byte) 8,
                    chunkSection.getR()
                    , chunkSection.generateChunkPalette(),
                    512, chunkSection.generateContent()
            ));
        } catch (Throwable throwable) {

        }
    }
}
