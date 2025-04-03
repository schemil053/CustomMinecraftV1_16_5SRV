package de.emilschlampp.customMinecraftServer.utils.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class IntTag extends SpecificTag {
    public final int value;

    public static SpecificTag read(DataInputStream in) {
        try {
            return new IntTag(in.readInt());
        } catch (IOException e) {
            return new ErrorTag("IOException while reading TAG_Int:\n" + e.getMessage());
        }
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeInt(value);
    }

    public IntTag(boolean boolValue) {
        this(boolValue ? 1 : 0);
    }

    public IntTag(int value) {
        this.value = value;
    }

    public int getData() {
        return value;
    }

    @Override
    public String extraInfo() {
        return ": " + getData();
    }

    public String type() {
        return "TAG_Int";
    }

    @Override
    public String tagName() {
        return "TAG_Int";
    }

    @Override
    public int tagType() {
        return Tag.TAG_INT;
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
    public int intValue() {
        return getData();
    }

    @Override
    public int intValue(int defaultValue) {
        return getData();
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof IntTag && ((IntTag) obj).value == value);
    }

    @Override
    public int hashCode() {
        return value;
    }
}
