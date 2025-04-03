package de.emilschlampp.customMinecraftServer.utils.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class StringTag extends SpecificTag {
    public final String value;

    public static SpecificTag read(DataInputStream in) {
        try {
            return new StringTag(in.readUTF());
        } catch (IOException e) {
            return new ErrorTag("IOException while reading TAG_String:\n" + e.getMessage());
        }
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        write(out, value);
    }

    static void write(DataOutputStream out, String data) throws IOException {
        out.writeUTF(data);
    }

    static void skip(DataInputStream in) {
        try {
            short length = in.readShort();
            in.skipBytes(length);
        } catch (IOException e) {
        }
    }

    public StringTag(String value) {
        this.value = value;
    }

    public String getData() {
        return value != null ? value : "";
    }

    @Override
    public String extraInfo() {
        return ": \"" + getData() + '"';
    }

    public String type() {
        return "TAG_String";
    }

    @Override
    public String tagName() {
        return "TAG_String";
    }

    @Override
    public int tagType() {
        return Tag.TAG_STRING;
    }

    @Override
    public String stringValue() {
        return getData();
    }

    @Override
    public String stringValue(String defaultValue) {
        return getData();
    }

    @Override
    public boolean same(String name) {
        return getData().equals(name);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof StringTag && ((StringTag) obj).value.equals(value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
