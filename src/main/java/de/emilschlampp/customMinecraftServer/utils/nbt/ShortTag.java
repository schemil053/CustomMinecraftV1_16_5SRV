package de.emilschlampp.customMinecraftServer.utils.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ShortTag extends SpecificTag {
    public final short value;

    public static SpecificTag read(DataInputStream in) {
        try {
            return new ShortTag(in.readShort());
        } catch (IOException e) {
            return new ErrorTag("IOException while reading TAG_Short:\n" + e.getMessage());
        }
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeShort(getData());
    }

    public ShortTag(short value) {
        this.value = value;
    }

    public short getData() {
        return value;
    }

    @Override
    public String extraInfo() {
        return ": " + getData();
    }

    public String type() {
        return "TAG_Short";
    }

    @Override
    public String tagName() {
        return "TAG_Short";
    }

    @Override
    public int tagType() {
        return Tag.TAG_SHORT;
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
    public short shortValue() {
        return getData();
    }

    @Override
    public short shortValue(short defaultValue) {
        return getData();
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof ShortTag && ((ShortTag) obj).value == value);
    }

    @Override
    public int hashCode() {
        return value;
    }
}
