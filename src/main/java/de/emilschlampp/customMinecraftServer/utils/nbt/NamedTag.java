package de.emilschlampp.customMinecraftServer.utils.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NamedTag extends Tag {
    public final String name;
    public final SpecificTag tag;

    public NamedTag(String name, SpecificTag tag) {
        this.name = name;
        this.tag = tag;
    }

    public static Tag read(DataInputStream in) {
        try {
            byte type = in.readByte();
            if (type == 0) {
                return Tag.END;
            } else {
                SpecificTag name = StringTag.read(in);
                SpecificTag payload = SpecificTag.read(type, in);
                return new NamedTag(name.stringValue(), payload);
            }
        } catch (IOException e) {
            return new ErrorTag("IOException while reading tag type:\n" + e.getMessage());
        }
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        getTag().writeType(out);
        StringTag.write(out, name);
        getTag().write(out);
    }

    public static Map<String, Tag> quickParse(DataInputStream in, Set<String> request) {
        Map<String, Tag> result = new HashMap<>();
        Set<String> prefixes = new HashSet<>();

        for (String tag : request) {
            String[] parts = tag.split("\\.");
            String prefix = "";
            for (int i = 0; i < parts.length - 1; ++i) {
                if (i > 0) {
                    prefix += ".";
                }
                prefix += parts[i];
                prefixes.add(prefix);
            }
        }

        for (String tag : request) {
            result.put(tag, new ErrorTag("[not loaded]"));
        }

        return partialParse(in, result, request, prefixes);
    }

    private static Map<String, Tag> partialParse(DataInputStream in, Map<String, Tag> result,
                                                 Set<String> request, Set<String> prefixes) {
        try {
            byte type = in.readByte();
            if (type != 0) {
                SpecificTag name = StringTag.read(in);
                String tag = name.stringValue();
                partiallyParseTag(in, result, request, prefixes, type, tag);
            }
        } catch (IOException e) {
        }
        return result;
    }

    static boolean partiallyParseTag(DataInputStream in,
                                     Map<String, Tag> result,
                                     Set<String> request, Set<String> prefixes,
                                     byte type, String tag) {
        if (request.contains(tag)) {
            SpecificTag payload = SpecificTag.read(type, in);
            result.put(tag, payload);
            request.remove(tag);
            return true;
        } else if (prefixes.contains(tag)) {
            if (type == TAG_LIST) {
                ListTag.partialParse(in, tag, result, request, prefixes);
            } else if (type == TAG_COMPOUND) {
                CompoundTag.partialParse(in, tag, result, request, prefixes);
            }
            return true;
        }
        return false;
    }

    public String name() {
        return name;
    }

    public SpecificTag getTag() {
        return tag;
    }

    @Override
    public String tagName() {
        return "TAG:named";
    }

    @Override
    public Tag unpack() {
        return getTag();
    }

    @Override
    public boolean isNamed(String name) {
        return this.name.equals(name);
    }

    @Override
    public void printTag(StringBuilder buff, String indent) {
        buff.append(indent);
        printTagInfo(buff);
        buff.append(indent).append("  ").append("TAG_String: \"").append(name).append("\"\n");
        tag.printTag(buff, indent + "  ");
    }

    @Override
    public ListTag asList() {
        return tag.asList();
    }

    @Override
    public CompoundTag asCompound() {
        return tag.asCompound();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof NamedTag)) {
            return false;
        }
        NamedTag other = (NamedTag) obj;
        return name.equals(other.name) && tag.equals(other.tag);
    }

    @Override
    public int hashCode() {
        return (name.hashCode() * 31) ^ tag.hashCode();
    }
}
