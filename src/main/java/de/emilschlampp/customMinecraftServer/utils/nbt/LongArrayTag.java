package de.emilschlampp.customMinecraftServer.utils.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class LongArrayTag extends SpecificTag {
    public final long[] value;

    public static SpecificTag read(DataInputStream in) {
        try {
            int length = in.readInt();
            long[] data = new long[length];
            for (int i = 0; i < length; ++i) {
                data[i] = in.readLong();
            }
            return new LongArrayTag(data);
        } catch (IOException e) {
            return new ErrorTag("IOException while reading TAG_Long_Array:\n" + e.getMessage());
        }
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeInt(value.length);
        for (int i = 0; i < value.length; ++i) {
            out.writeLong(value[i]);
        }
    }

    static void skip(DataInputStream in) {
        try {
            int length = in.readInt();
            in.skipBytes(length * 8);
        } catch (IOException e) {
        }
    }

    public LongArrayTag(long[] data) {
        this.value = data;
    }

    public long[] getData() {
        return value;
    }

    @Override
    public String extraInfo() {
        return ": " + value.length;
    }

    @Override
    public String tagName() {
        return "TAG_Long_Array";
    }

    public String type() {
        return "TAG_Long_Array";
    }

    @Override
    public int tagType() {
        return Tag.TAG_LONG_ARRAY;
    }

    @Override
    public long[] longArray() {
        return value;
    }

    @Override
    public long[] longArray(long[] defaultValue) {
        return value;
    }

    @Override
    public boolean isLongArray(int size) {
        return value.length >= size;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj
                || (obj instanceof LongArrayTag && Arrays.equals(((LongArrayTag) obj).value, value));
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }
}
