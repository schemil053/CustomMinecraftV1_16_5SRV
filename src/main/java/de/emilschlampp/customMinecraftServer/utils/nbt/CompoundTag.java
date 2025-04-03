package de.emilschlampp.customMinecraftServer.utils.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

public class CompoundTag extends SpecificTag implements Iterable<NamedTag> {
    final Map<String, NamedTag> items = new LinkedHashMap<>();

    public void add(String name, SpecificTag tag) {
        items.put(name, new NamedTag(name, tag));
    }

    public static SpecificTag read(DataInputStream in) {
        CompoundTag tagThis = new CompoundTag();
        while (true) {
            Tag last = NamedTag.read(in);
            if (last.isEnd()) {
                break;
            }
            tagThis.add((NamedTag) last);
        }
        return tagThis;
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        for (NamedTag tag : items.values()) {
            tag.write(out);
        }
        out.writeByte(Tag.TAG_END);
    }

    static Map<String, Tag> partialParse(DataInputStream in, String prefix,
                                         Map<String, Tag> result, Set<String> request, Set<String> prefixes) {
        try {
            while (true) {
                byte type = in.readByte();
                if (type == Tag.TAG_END) {
                    break;
                }
                SpecificTag name = StringTag.read(in);
                String tag = prefix + "." + name.stringValue();
                boolean parsed = NamedTag.partiallyParseTag(in, result, request, prefixes, type, tag);
                if (parsed) {
                    if (request.isEmpty()) {
                        return result;
                    }
                } else {
                    SpecificTag.skip(type, in);
                }
            }
        } catch (IOException e) {
        }
        return result;
    }

    static void skip(DataInputStream in) {
        try {
            while (true) {
                byte itemType = in.readByte();
                if (itemType == 0) {
                    break;
                }

                StringTag.skip(in);
                SpecificTag.skip(itemType, in);
            }
        } catch (IOException e) {
        }
    }

    @Override
    public void printTag(StringBuilder buff, String indent) {
        buff.append(indent);
        printTagInfo(buff);
        for (NamedTag tag : items.values()) {
            buff.append(String.format("%s  %s:\n", indent, tag.name()));
            tag.tag.printTag(buff, indent + "    ");
        }
    }

    public CompoundTag() {
    }

    public CompoundTag(List<? extends NamedTag> items) {
        for (NamedTag tag : items) {
            this.items.put(tag.name, tag);
        }
    }

    public int size() {
        return items.size();
    }

    public void add(NamedTag node) {
        items.put(node.name, node);
    }

    public String toString() {
        return dumpTree();
    }

    public String type() {
        return "TAG_Compound";
    }

    @Override
    public String tagName() {
        return "TAG_Compound";
    }

    @Override
    public int tagType() {
        return Tag.TAG_COMPOUND;
    }

    @Override
    public boolean isCompoundTag() {
        return true;
    }

    @Override
    public Tag get(String name) {
        if (items.containsKey(name)) {
            return items.get(name).unpack();
        }
        return new ErrorTag("No item named \"" + name + "\" in this compound tag.");
    }

    @Override
    public Iterator<NamedTag> iterator() {
        return items.values().iterator();
    }

    @Override
    public CompoundTag asCompound() {
        return this;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CompoundTag)) {
            return false;
        }
        CompoundTag other = (CompoundTag) obj;
        for (NamedTag tag : items.values()) {
            if (!other.get(tag.name()).equals(tag.tag)) {
                return false;
            }
        }
        for (NamedTag tag : other.items.values()) {
            if (!get(tag.name()).equals(tag.tag)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }

    public String getString(String name) {
        return get(name).stringValue();
    }

    public Number getNumber(String y) {
        return get(y).intValue();
    }

    public ListTag getListTag(String palette) {
        return get(palette).asList();
    }

    public void add(String name, String s) {
        add(name, new StringTag(s));
    }

    public void add(String blockLight, byte[] blockLight1) {
        add(blockLight, new ByteArrayTag(blockLight1));
    }

    public void add(String blockStates, long[] blockStates1) {
        add(blockStates, new LongArrayTag(blockStates1));
    }

    public ByteArrayTag getByteArrayTag(String blockLight) {
        return (ByteArrayTag) get(blockLight);
    }

    public LongArrayTag getLongArrayTag(String blockStates) {
        return (LongArrayTag) get(blockStates);
    }
}
