package de.emilschlampp.customMinecraftServer.utils.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

public class ListTag extends SpecificTag implements Iterable<SpecificTag> {
    public final int type;
    public final List<SpecificTag> items;

    public static SpecificTag read(DataInputStream in) {
        try {
            byte itemType = in.readByte();
            int numItems = in.readInt();
            if (itemType == Tag.TAG_END && numItems > 0) {
                return new ErrorTag("Cannot create list of TAG_End");
            }
            ListTag tagThis = new ListTag(itemType, Collections.<SpecificTag>emptyList());
            for (int i = 0; i < numItems; ++i) {
                SpecificTag last = SpecificTag.read(itemType, in);
                tagThis.add(last);
            }
            return tagThis;
        } catch (IOException e) {
            return new ErrorTag("IOException while reading TAG_List:\n" + e.getMessage());
        }
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeByte(getType());
        out.writeInt(size());
        for (SpecificTag item : items) {
            item.write(out);
        }
    }

    static Map<String, Tag> partialParse(DataInputStream in, String prefix,
                                         Map<String, Tag> result, Set<String> request, Set<String> prefixes) {

        try {
            byte itemType = in.readByte();
            int numItems = in.readInt();
            if (itemType == Tag.TAG_END && numItems > 0) {
                return result;
            }
            for (int i = 0; i < numItems; ++i) {
                String tag = prefix + "." + i;
                boolean parsed = NamedTag.partiallyParseTag(in, result, request, prefixes, itemType, tag);
                if (parsed) {
                    if (request.isEmpty()) {
                        return result;
                    }
                } else {
                    SpecificTag.skip(itemType, in);
                }
            }
        } catch (IOException e) {
        }
        return result;
    }

    static void skip(DataInputStream in) {
        try {
            byte itemType = in.readByte();
            int numItems = in.readInt();
            if (itemType == 0) {
                return;
            }
            for (int i = 0; i < numItems; ++i) {
                SpecificTag.skip(itemType, in);
            }
        } catch (IOException e) {
        }
    }

    public ListTag(int type, List<? extends SpecificTag> items) {
        this.type = type;
        this.items = new ArrayList<>(items);
    }

    public int getType() {
        return type;
    }

    public int size() {
        return items.size();
    }

    public void add(SpecificTag node) {
        items.add(node);
    }

    @Override
    public void set(int i, SpecificTag node) {
        items.set(i, node);
    }

    public String toString() {
        return dumpTree();
    }

    public String type() {
        return "TAG_List";
    }

    @Override
    public void printTag(StringBuilder buff, String indent) {
        buff.append(indent);
        printTagInfo(buff);
        for (Tag item : items) {
            item.printTag(buff, indent + "  ");
        }
    }

    @Override
    public String tagName() {
        return "TAG_List";
    }

    @Override
    public int tagType() {
        return Tag.TAG_LIST;
    }

    @Override
    public boolean isList() {
        return true;
    }

    @Override
    public Tag get(int i) {
        return items.get(i);
    }

    @Override
    public Iterator<SpecificTag> iterator() {
        return items.iterator();
    }

    @Override
    public ListTag asList() {
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
        if (!(obj instanceof ListTag)) {
            return false;
        }
        return items.equals(((ListTag) obj).items);
    }

    @Override
    public int hashCode() {
        int code = 0;
        for (SpecificTag tag : items) {
            code *= 31;
            code ^= tag.hashCode();
        }
        return code;
    }

    public void remove(int i) {
        items.remove(i);
    }
}
