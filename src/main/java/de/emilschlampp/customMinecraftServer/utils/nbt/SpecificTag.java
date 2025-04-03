package de.emilschlampp.customMinecraftServer.utils.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class SpecificTag extends Tag {
    public static SpecificTag read(byte type, DataInputStream in) {
        switch (type) {
            case Tag.TAG_BYTE:
                return ByteTag.read(in);
            case Tag.TAG_SHORT:
                return ShortTag.read(in);
            case Tag.TAG_INT:
                return IntTag.read(in);
            case Tag.TAG_LONG:
                return LongTag.read(in);
            case Tag.TAG_FLOAT:
                return FloatTag.read(in);
            case Tag.TAG_DOUBLE:
                return DoubleTag.read(in);
            case Tag.TAG_BYTE_ARRAY:
                return ByteArrayTag.read(in);
            case Tag.TAG_STRING:
                return StringTag.read(in);
            case Tag.TAG_LIST:
                return ListTag.read(in);
            case Tag.TAG_COMPOUND:
                return CompoundTag.read(in);
            case Tag.TAG_INT_ARRAY:
                return IntArrayTag.read(in);
            case Tag.TAG_LONG_ARRAY:
                return LongArrayTag.read(in);
            default:
                return new ErrorTag("Unknown tag type: " + type);
        }
    }

    public void writeType(DataOutputStream out) throws IOException {
        out.writeByte(tagType());
    }

    static void skip(byte type, DataInputStream in) {
        try {
            switch (type) {
                case Tag.TAG_BYTE:
                    in.skipBytes(1);
                    break;
                case Tag.TAG_SHORT:
                    in.skipBytes(2);
                    break;
                case Tag.TAG_INT:
                    in.skipBytes(4);
                    break;
                case Tag.TAG_LONG:
                    in.skipBytes(8);
                    break;
                case Tag.TAG_FLOAT:
                    in.skipBytes(4);
                    break;
                case Tag.TAG_DOUBLE:
                    in.skipBytes(8);
                    break;
                case Tag.TAG_BYTE_ARRAY:
                    ByteArrayTag.skip(in);
                    break;
                case Tag.TAG_STRING:
                    StringTag.skip(in);
                    break;
                case Tag.TAG_LIST:
                    ListTag.skip(in);
                    break;
                case Tag.TAG_COMPOUND:
                    CompoundTag.skip(in);
                    break;
                case Tag.TAG_INT_ARRAY:
                    IntArrayTag.skip(in);
                    break;
                case Tag.TAG_LONG_ARRAY:
                    LongArrayTag.skip(in);
                    break;
            }
        } catch (IOException e) {
        }
    }

    public SpecificTag() {
        super();
    }

    @Override
    public SpecificTag clone() throws CloneNotSupportedException {
        SpecificTag node = (SpecificTag) super.clone();
        return node;
    }

    public abstract int tagType();
}
