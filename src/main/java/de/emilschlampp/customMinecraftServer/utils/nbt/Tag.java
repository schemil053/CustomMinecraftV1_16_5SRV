package de.emilschlampp.customMinecraftServer.utils.nbt;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collections;

public abstract class Tag {
    public static final int TAG_END = 0;
    public static final int TAG_BYTE = 1;
    public static final int TAG_SHORT = 2;
    public static final int TAG_INT = 3;
    public static final int TAG_LONG = 4;
    public static final int TAG_FLOAT = 5;
    public static final int TAG_DOUBLE = 6;
    public static final int TAG_BYTE_ARRAY = 7;
    public static final int TAG_STRING = 8;
    public static final int TAG_LIST = 9;
    public static final int TAG_COMPOUND = 10;
    public static final int TAG_INT_ARRAY = 11;
    public static final int TAG_LONG_ARRAY = 12;

    private static final ListTag EMPTY_LIST =
            new ListTag(Tag.TAG_END, Collections.<SpecificTag>emptyList());
    private static final CompoundTag EMPTY_COMPOUND = new CompoundTag();

    protected Tag() {
    }

    public static final SpecificTag END = new SpecificTag() {
        @Override
        public void write(DataOutputStream out) throws IOException {
            writeType(out);
        }

        @Override
        public String tagName() {
            return "TAG_End";
        }

        @Override
        public int tagType() {
            return Tag.TAG_END;
        }

        @Override
        public boolean isEnd() {
            return true;
        }
    };

    public abstract String tagName();

    public boolean isError() {
        return false;
    }

    public boolean isNamed(String name) {
        return false;
    }

    public boolean same(String name) {
        return false;
    }

    public String dumpTree() {
        StringBuilder buff = new StringBuilder(4096);
        printTag(buff, "");
        return buff.toString();
    }

    public void printTagInfo(StringBuilder buff) {
        buff.append(tagName()).append(extraInfo()).append('\n');
    }

    public void printTag(StringBuilder buff, String indent) {
        buff.append(indent);
        printTagInfo(buff);
    }

    public String extraInfo() {
        return "";
    }

    public abstract void write(DataOutputStream out) throws IOException;

    public String toString() {
        return tagName() + extraInfo();
    }

    public boolean isEnd() {
        return false;
    }

    public Tag unpack() {
        return this;
    }

    public String error() {
        return "";
    }

    public boolean boolValue() {
        return false;
    }

    public boolean boolValue(boolean defaultValue) {
        return defaultValue;
    }

    public int byteValue() {
        return 0;
    }

    public int byteValue(int defaultValue) {
        return defaultValue;
    }

    public short shortValue() {
        return (short) 0;
    }

    public short shortValue(short defaultValue) {
        return defaultValue;
    }

    public int intValue() {
        return 0;
    }

    public int intValue(int defaultValue) {
        return defaultValue;
    }

    public long longValue() {
        return (long) 0;
    }

    public long longValue(long defaultValue) {
        return defaultValue;
    }

    public float floatValue() {
        return (float) 0;
    }

    public float floatValue(float defaultValue) {
        return defaultValue;
    }

    public double doubleValue() {
        return (double) 0;
    }

    public double doubleValue(double defaultValue) {
        return defaultValue;
    }

    public String stringValue() {
        return "";
    }

    public String stringValue(String defaultValue) {
        return defaultValue;
    }

    public byte[] byteArray() {
        return new byte[0];
    }

    public byte[] byteArray(byte[] defaultValue) {
        return defaultValue;
    }

    public int[] intArray() {
        return new int[0];
    }

    public int[] intArray(int[] defaultValue) {
        return defaultValue;
    }

    public long[] longArray() {
        return new long[0];
    }

    public long[] longArray(long[] defaultValue) {
        return defaultValue;
    }

    public boolean isCompoundTag() {
        return false;
    }

    public boolean isList() {
        return false;
    }

    public boolean isByteArray(int size) {
        return false;
    }

    public boolean isIntArray(int size) {
        return false;
    }

    public boolean isLongArray(int size) {
        return false;
    }

    public Tag get(String name) {
        return new ErrorTag("Cannot index-by-name in a non-CompoundTag");
    }

    public void set(int i, SpecificTag tag) {
        throw new Error("Cannot set item in non-list tag.");
    }

    public Tag get(int i) {
        return new ErrorTag("Cannot index a non-ListTag");
    }

    public ListTag asList() {
        return EMPTY_LIST;
    }

    public CompoundTag asCompound() {
        return EMPTY_COMPOUND;
    }
}
