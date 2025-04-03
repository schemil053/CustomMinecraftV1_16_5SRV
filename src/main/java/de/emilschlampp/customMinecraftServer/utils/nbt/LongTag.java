package de.emilschlampp.customMinecraftServer.utils.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LongTag extends SpecificTag {
    public final long value;

    public static SpecificTag read(DataInputStream in) {
        try {
            return new LongTag(in.readLong());
        } catch (IOException e) {
            return new ErrorTag("IOException while reading TAG_Long:\n" + e.getMessage());
        }
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeLong(getData());
    }

    public LongTag(long value) {
        this.value = value;
    }

    public long getData() {
        return value;
    }

    @Override
    public String extraInfo() {
        return ": " + getData();
    }

    public String type() {
        return "TAG_Long";
    }

    @Override
    public String tagName() {
        return "TAG_Long";
    }

    @Override
    public int tagType() {
        return Tag.TAG_LONG;
    }

    @Override
    public boolean boolValue() {
        return getData() != 0;
    }

    @Override
    public boolean boolValue(boolean defaultValue) {
        return getData() != 0;
    }

    @Override
    public long longValue() {
        return getData();
    }

    @Override
    public long longValue(long defaultValue) {
        return getData();
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof LongTag && ((LongTag) obj).value == value);
    }

    @Override
    public int hashCode() {
        return (int) value;
    }
}
