package de.emilschlampp.customMinecraftServer.utils.data;

import java.util.Arrays;

public class ByteData {
    private final int width;
    private final int height;

    private final int length;

    public byte[] blocks;

    public ByteData(int height, int width, int length, byte[] blocks) {
        this.width = width;
        this.height = height;
        this.length = length;
        if(blocks.length == 0) {
            this.blocks = new byte[this.width * this.width * this.height];
        } else {
            this.blocks = blocks;
        }
    }

    public ByteData(int height, int width, int length) {
        this(height, width,length, new byte[0]);
    }
//(x * width + z) * height + y
    //(y*height + z)*width + x
    //y * width * length + z * width + x
    public void setBlock(int x, int y, int z, byte materialId) {
        blocks[y * width * length + z * width + x] = materialId;
    }

    public byte getBlock(int x, int y, int z) {
        return blocks[y * width * length + z * width + x];
    }

    public void setBlocks(int x, int y1, int y2, int z, byte materialId) {
        int xz = (x * width + z) * height;
        Arrays.fill(blocks, xz + y1, xz + y2, materialId);
    }

    public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, byte materialId) {
        for (int x = x1; x < x2; x++) {
            for (int z = z1; z < z2; z++) {
                int xz = (x * width + z) * height;
                Arrays.fill(blocks, xz + y1, xz + y2, materialId);
            }
        }
    }


    public int setLayer(int blocky, byte materialId) {
        setBlocks(0, width, blocky, blocky + 1, 0, width, materialId);
        return blocky + 1;
    }

    public int setLayer(int blocky, int height, byte materialId) {
        setBlocks(0, width, blocky, blocky + height, 0, width, materialId);
        return blocky + height;
    }

    public int setLayer(int blocky, int height, int inset, byte materialId) {
        setBlocks(inset, width - inset, blocky, blocky + height, inset, width - inset, materialId);
        return blocky + height;
    }

    private void setCircleBlocks(int cx, int cz, int x, int z, int y, byte materialId) {
        setBlock(cx + x, y, cz + z, materialId);
        setBlock(cx + z, y, cz + x, materialId);
        setBlock(cx - z - 1, y, cz + x, materialId);
        setBlock(cx - x - 1, y, cz + z, materialId);
        setBlock(cx - x - 1, y, cz - z - 1, materialId);
        setBlock(cx - z - 1, y, cz - x - 1, materialId);
        setBlock(cx + z, y, cz - x - 1, materialId);
        setBlock(cx + x, y, cz - z - 1, materialId);
    }

    public void setCircle(int cx, int cz, int r, int y, byte materialId) {
        int x = r;
        int z = 0;
        int xChange = 1 - 2 * r;
        int zChange = 1;
        int rError = 0;

        while (x >= z) {
            setCircleBlocks(cx, cz, x, z, y, materialId);
            z++;
            rError += zChange;
            zChange += 2;
            if (2 * rError + xChange > 0) {
                x--;
                rError += xChange;
                xChange += 2;
            }
        }
    }
}
