package de.emilschlampp.customMinecraftServer.utils.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FloatTag extends SpecificTag {
    public final float value;

    public static SpecificTag read(DataInputStream in) {
        try {
            return new FloatTag(in.readFloat());
        } catch (IOException e) {
            return new ErrorTag("IOException while reading TAG_Float:\n" + e.getMessage());
        }
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeFloat(getData());
    }

    public FloatTag(float value) {
        this.value = value;
    }

    public float getData() {
        return value;
    }

    @Override
    public String extraInfo() {
        return ": " + getData();
    }

    public String type() {
        return "TAG_Float";
    }

    @Override
    public String tagName() {
        return "TAG_Float";
    }

    @Override
    public int tagType() {
        return Tag.TAG_FLOAT;
    }

    @Override
    public float floatValue() {
        return getData();
    }

    @Override
    public float floatValue(float defaultValue) {
        return getData();
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof FloatTag && ((FloatTag) obj).value == value);
    }

    @Override
    public int hashCode() {
        return Float.floatToRawIntBits(value);
    }
}
