package de.emilschlampp.customMinecraftServer.utils.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DoubleTag extends SpecificTag {
    public final double value;

    public static SpecificTag read(DataInputStream in) {
        try {
            return new DoubleTag(in.readDouble());
        } catch (IOException e) {
            return new ErrorTag("IOException while reading TAG_Double:\n" + e.getMessage());
        }
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeDouble(getData());
    }

    public DoubleTag(double value) {
        this.value = value;
    }

    public double getData() {
        return value;
    }

    @Override
    public String extraInfo() {
        return ": " + getData();
    }

    public String type() {
        return "TAG_Double";
    }

    @Override
    public String tagName() {
        return "TAG_Double";
    }

    @Override
    public int tagType() {
        return Tag.TAG_DOUBLE;
    }

    @Override
    public double doubleValue() {
        return getData();
    }

    @Override
    public double doubleValue(double defaultValue) {
        return getData();
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof DoubleTag && ((DoubleTag) obj).value == value);
    }

    @Override
    public int hashCode() {
        long x = Double.doubleToRawLongBits(value);
        return (int) ((x >> 32) ^ x);
    }
}
