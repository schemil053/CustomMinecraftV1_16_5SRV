package de.emilschlampp.customMinecraftServer.utils;

import de.emilschlampp.customMinecraftServer.net.NetUtils;
import de.emilschlampp.customMinecraftServer.utils.nbt.CompoundTag;
import de.emilschlampp.customMinecraftServer.utils.nbt.LongArrayTag;
import de.emilschlampp.customMinecraftServer.utils.nbt.NamedTag;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ChunkSection {
    private final int CHUNK_SIZE = 16;
    private final int CHUNK_HEIGHT = 16;
    private int[][][] blockData;
    private Map<Integer, Integer> blockPalette;
    private int nextPaletteIndex = 0;
    public int r = 0;

    public ChunkSection() {
        blockData = new int[CHUNK_SIZE][CHUNK_HEIGHT][CHUNK_SIZE];
        blockPalette = new HashMap<>();
        addToPalette(0);
    }

    public void setBlock(int x, int y, int z, int blockID) {
        blockData[x][y][z] = addToPalette(blockID);
        //   System.out.println(addToPalette(blockID)+"|B"+blockID);
    }

    public int getBlock(int x, int y, int z) {
        return blockData[x][y][z];
    }

    private int addToPalette(int blockID) {
        if (!blockPalette.containsKey(blockID)) {
            blockPalette.put(blockID, nextPaletteIndex);
            nextPaletteIndex++;
        }
        return blockPalette.get(blockID);
    }

    public short calcNonAir() {
        int i = 0;

        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    if(getBlock(x, y, z) != 0) {
                        i++;
                    }
                }
            }
        }
        return (short) i;
    }

  /*  public long[] generateMotionBlockingArray() {
        int h = 256; //36
        long[] motionBlockingArray = new long[h];

        for (int y = 0; y < h; y++) {
            BitSet bitmask = new BitSet(64);
            for (int z = 0; z < CHUNK_SIZE; z++) {
                int blockID = getBlock(0, y, z);
                boolean isBlocking = isBlockBlocking(blockID);
                if (isBlocking) {
                    bitmask.set(z);
                }
            }

            long value = 0;
            for (int i = 0; i < CHUNK_SIZE; i++) {
                if (bitmask.get(i)) {
                    value |= (1L << i);
                }
            }

            motionBlockingArray[y] = value;
        }

        return motionBlockingArray;
    }

   */

    public long[] generateMotionBlockingArray() {
        // FIXME: 26.08.2023 Fix
        int h = 16; //36;
        long[] motionBlockingArray = new long[h];

        for (int y = 0; y < h; y++) {
            BitSet bitmask = new BitSet(64);
            for (int z = 0; z < CHUNK_SIZE; z++) {
                int blockID = getBlock(0, y, z);
                boolean isBlocking = isBlockBlocking(blockID);
                if (isBlocking) {
                    bitmask.set(z);
                }
            }

            long value = 0;
            for (int i = 0; i < CHUNK_SIZE; i++) {
                if (bitmask.get(i)) {
                    value |= (1L << i);
                }
            }

            motionBlockingArray[y] = value;
        }
        return motionBlockingArray;
        //  return StringUtils.concatL(motionBlockingArray, new long[5]);
    }

    public byte[] generateHeightmap() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream outputStreamA = new DataOutputStream(outputStream);

   /*     try {
            outputStreamA.writeUTF();

        } catch (Throwable throwable) {

        }*/

        LongArrayTag t =
                new LongArrayTag(
                        //          generateMotionBlockingArray()
                      /*  new long[] {
                                1371773531765642314L,
                                1389823183635651148L,
                                1371738278539598925L,
                                1389823183635388492L,
                                1353688558756731469L,
                                1389823114781694027L,
                                1317765589597723213L,
                                1371773531899860042L,
                                1389823183635651149L,
                                1371773462911685197L,
                                1389823183635650636L,
                                1353688626805119565L,
                                1371773531900123211L,
                                1335639250618849869L,
                                1371738278674077258L,
                                1389823114781694028L,
                                1353723811310638154L,1371738278674077259L,1335674228429068364L,1335674228429067338L,1335674228698027594L,
                                1317624576693539402L,1335709481520370249L,1299610178184057417L,1335638906349064264L,1299574993811968586L,
                                1299574924958011464L,1299610178184056904L,1299574924958011464L,1299610109330100296L,1299574924958011464L,
                                1299574924823793736L,1299574924958011465L,1281525273222484040L,1299574924958011464L,1281525273222484040L,
                                9548107335L}
*/

                        new long[]{
                                72198606942111748L, 72198606942111748L,
                                72198606942111748L, 72198606942111748L,
                                72198606942111748L, 72198606942111748L,
                                72198606942111748L, 72198606942111748L,
                                72198606942111748L, 72198606942111748L,
                                72198606942111748L, 72198606942111748L,
                                72198606942111748L, 72198606942111748L,
                                72198606942111748L, 72198606942111748L,
                                72198606942111748L, 72198606942111748L,
                                72198606942111748L, 72198606942111748L,
                                72198606942111748L, 72198606942111748L,
                                72198606942111748L, 72198606942111748L,
                                72198606942111748L, 72198606942111748L,
                                72198606942111748L, 72198606942111748L,
                                72198606942111748L, 72198606942111748L,
                                72198606942111748L, 72198606942111748L,
                                72198606942111748L, 72198606942111748L,
                                72198606942111748L, 72198606942111748L,
                                537921540L
                        }
                );

        CompoundTag main = new CompoundTag();
        CompoundTag tags = new CompoundTag();
        tags.add("MOTION_BLOCKING", t);

        main.add(new NamedTag("", tags));


        try {
            main.write(outputStreamA);

            outputStreamA.flush();


            byte[] a = outputStream.toByteArray();

            byte[] b = new byte[a.length - 1];

            System.arraycopy(a, 0, b, 0, b.length);


            //    SpecificTag tag = CompoundTag.read(new DataInputStream(getClass().getResourceAsStream("/chunk_heightmap.dat")));
            //      System.out.println(tag.dumpTree());

            return b;
        } catch (IOException e) {
            e.printStackTrace();

            return new byte[0];
        }
    }

    public boolean isBlockBlocking(int id) {
        return true;
    }

    public byte[] generateChunkPalette() throws IOException {
        r = 0;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        //    NetUtils.writeVarInt(blockPalette.size(), byteArrayOutputStream);

        for (Map.Entry<Integer, Integer> entry : blockPalette.entrySet()) {
            int blockID = entry.getKey();
            int paletteIndex = entry.getValue();


            NetUtils.writeVarInt(blockID, byteArrayOutputStream);
            //      NetUtils.writeVarInt(paletteIndex, byteArrayOutputStream);
            r += 1;
        }


        List<Integer> a = blockPalette.keySet().stream().sorted(Comparator.comparing(g -> {
            return blockPalette.get(g);
        })).collect(Collectors.toList());

        for (Integer integer : a) {
            //     NetUtils.writeVarInt(integer, byteArrayOutputStream);
        }

        return byteArrayOutputStream.toByteArray();
    }

    public int getR() {
        return r;
    }

    public byte[] generateContent() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        for (int y = 0; y < CHUNK_HEIGHT; y++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                for (int x = 0; x < CHUNK_SIZE; x++) {
                    int paletteIndex = blockData[x][y][z];
                    NetUtils.writeVarInt(paletteIndex, byteArrayOutputStream);
                    //  byteArrayOutputStream.write(paletteIndex);
                }
            }
        }

        return byteArrayOutputStream.toByteArray();
    }
}
