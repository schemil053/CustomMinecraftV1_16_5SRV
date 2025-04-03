package de.emilschlampp.customMinecraftServer.utils.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ByteTag extends SpecificTag {
    public final int value;

    public static SpecificTag read(DataInputStream in) {
        try {
            return new ByteTag(in.readByte());
        } catch (IOException e) {
            return new ErrorTag("IOException while reading TAG_Byte:\n" + e.getMessage());
        }
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeByte(getData());
    }

    public ByteTag(int value) {
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
        return "TAG_Byte";
    }

    @Override
    public String tagName() {
        return "TAG_Byte";
    }

    @Override
    public int tagType() {
        return Tag.TAG_BYTE;
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
    public int byteValue() {
        return getData();
    }

    @Override
    public int byteValue(int defaultValue) {
        return getData();
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof ByteTag && ((ByteTag) obj).value == value);
    }

    @Override
    public int hashCode() {
        return value;
    }
}
