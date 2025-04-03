package de.emilschlampp.customMinecraftServer.utils.data;

import java.util.Arrays;

public class IntData {
    private final int width;
    private final int height;

    private final int length;

    public int[] blocks;

    public IntData(int height, int width, int length, int[] blocks) {
        this.width = width;
        this.height = height;
        this.length = length;
        if(blocks.length == 0) {
            this.blocks = new int[this.width * this.width * this.height];
        } else {
            this.blocks = blocks;
        }
    }

    public IntData(int height, int width, int length) {
        this(height, width,length, new int[0]);
    }
//(x * width + z) * height + y
    //(y*height + z)*width + x
    //y * width * length + z * width + x
    public void setBlock(int x, int y, int z, int materialId) {
        blocks[y * width * length + z * width + x] = materialId;
    }

    public int getBlock(int x, int y, int z) {
        return blocks[y * width * length + z * width + x];
    }

    public void setBlocks(int x, int y1, int y2, int z, int materialId) {
        int xz = (x * width + z) * height;
        Arrays.fill(blocks, xz + y1, xz + y2, materialId);
    }

    public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, int materialId) {
        for (int x = x1; x < x2; x++) {
            for (int z = z1; z < z2; z++) {
                int xz = (x * width + z) * height;
                Arrays.fill(blocks, xz + y1, xz + y2, materialId);
            }
        }
    }


    public int setLayer(int blocky, int materialId) {
        setBlocks(0, width, blocky, blocky + 1, 0, width, materialId);
        return blocky + 1;
    }

    public int setLayer(int blocky, int height, int materialId) {
        setBlocks(0, width, blocky, blocky + height, 0, width, materialId);
        return blocky + height;
    }

    public int setLayer(int blocky, int height, int inset, int materialId) {
        setBlocks(inset, width - inset, blocky, blocky + height, inset, width - inset, materialId);
        return blocky + height;
    }

    private void setCircleBlocks(int cx, int cz, int x, int z, int y, int materialId) {
        setBlock(cx + x, y, cz + z, materialId);
        setBlock(cx + z, y, cz + x, materialId);
        setBlock(cx - z - 1, y, cz + x, materialId);
        setBlock(cx - x - 1, y, cz + z, materialId);
        setBlock(cx - x - 1, y, cz - z - 1, materialId);
        setBlock(cx - z - 1, y, cz - x - 1, materialId);
        setBlock(cx + z, y, cz - x - 1, materialId);
        setBlock(cx + x, y, cz - z - 1, materialId);
    }

    public void setCircle(int cx, int cz, int r, int y, int materialId) {
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
