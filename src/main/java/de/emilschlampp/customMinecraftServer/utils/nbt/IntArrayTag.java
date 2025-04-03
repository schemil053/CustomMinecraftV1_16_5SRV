package de.emilschlampp.customMinecraftServer.utils.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class IntArrayTag extends SpecificTag {
    public final int[] value;

    public static SpecificTag read(DataInputStream in) {
        try {
            int length = in.readInt();
            int[] data = new int[length];
            for (int i = 0; i < length; ++i) {
                data[i] = in.readInt();
            }
            return new IntArrayTag(data);
        } catch (IOException e) {
            return new ErrorTag("IOException while reading TAG_Int_Array:\n" + e.getMessage());
        }
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeInt(value.length);
        for (int i = 0; i < value.length; ++i) {
            out.writeInt(value[i]);
        }
    }

    static void skip(DataInputStream in) {
        try {
            int length = in.readInt();
            in.skipBytes(length * 4);
        } catch (IOException e) {
        }
    }

    public IntArrayTag(int[] data) {
        this.value = data;
    }

    public int[] getData() {
        return value;
    }

    @Override
    public String extraInfo() {
        return ": " + value.length;
    }

    @Override
    public String tagName() {
        return "TAG_Int_Array";
    }

    public String type() {
        return "TAG_Int_Array";
    }

    @Override
    public int tagType() {
        return Tag.TAG_INT_ARRAY;
    }

    @Override
    public int[] intArray() {
        return value;
    }

    @Override
    public int[] intArray(int[] defaultValue) {
        return value;
    }

    @Override
    public boolean isIntArray(int size) {
        return value.length >= size;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj
                || (obj instanceof IntArrayTag && Arrays.equals(((IntArrayTag) obj).value, value));
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }
}
