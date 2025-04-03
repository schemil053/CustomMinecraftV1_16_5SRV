package de.emilschlampp.customMinecraftServer.net.data;

import de.emilschlampp.customMinecraftServer.net.NetUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class Node {
    private byte flags;
    private int nodeType;
    private boolean isExecutable;
    private boolean hasRedirect;
    private boolean hasSuggestionsType;

    private int[] children; //Index von ChildNodes
    private int redirectNode; //Redirect-Node, nur wenn redirect true
    private String name;
    private String parser; //Parser, nur wenn argumentNode, https://wiki.vg/Command_Data#:~:text=for%20argument%20nodes.-,Parsers,-Clients%20are%20expected
    private byte[] properties; //Properties für parser
    private String suggestionType; //Suggestion, nur wenn argNode und hasSuggestionsType

    public Node(byte flags) {
        this.flags = flags;
        convertFlagsToValues();
    }

    public Node() {
    }

    public Node(int nodeType, boolean isExecutable, boolean hasRedirect, boolean hasSuggestionsType) {
        this.nodeType = nodeType;
        this.isExecutable = isExecutable;
        this.hasRedirect = hasRedirect;
        this.hasSuggestionsType = hasSuggestionsType;
        recalcFlags();
    }

    public Node(int nodeType, boolean isExecutable, boolean hasRedirect, boolean hasSuggestionsType, int[] children, int redirectNode, String name, String parser, byte[] properties, String suggestionType) {
        this.nodeType = nodeType;
        this.isExecutable = isExecutable;
        this.hasRedirect = hasRedirect;
        this.hasSuggestionsType = hasSuggestionsType;
        this.children = children;
        this.redirectNode = redirectNode;
        this.name = name;
        this.parser = parser;
        this.properties = properties;
        this.suggestionType = suggestionType;
        recalcFlags();
    }

    public Node recalcFlags() {
        flags = 0;

        flags |= nodeType;

        if(isExecutable) {
            flags |= 0x04;
        }
        if(hasRedirect) {
            flags |= 0x08;
        }

        if(hasSuggestionsType && nodeType == 2) {
            flags |= 0x10;
        }

        return this;
    }

    public Node convertFlagsToValues() {
        nodeType = flags & 0x03;
        isExecutable = (flags & 0x04) != 0;
        hasRedirect = (flags & 0x08) != 0;
        hasSuggestionsType = ((flags & 0x10) != 0) && (nodeType == 2);
        return this;
    }

    public static enum NodeType {
        ROOT(0), LITERAL(1), ARGUMENT(2), UNUSED(3);

        public final int id;
        NodeType(int id) {
            this.id = id;
        }
    }

    public Node setFlags(byte flags) {
        this.flags = flags;
        return this;
    }

    public byte getFlags() {
        return flags;
    }

    public int getNodeType() {
        return nodeType;
    }

    public Node setNodeType(int nodeType) {
        this.nodeType = nodeType;
        return this;
    }

    public boolean isExecutable() {
        return isExecutable;
    }

    public Node setExecutable(boolean executable) {
        isExecutable = executable;
        return this;
    }

    public boolean isHasRedirect() {
        return hasRedirect;
    }

    public Node setHasRedirect(boolean hasRedirect) {
        this.hasRedirect = hasRedirect;
        return this;
    }

    public boolean isHasSuggestionsType() {
        return hasSuggestionsType;
    }

    public Node setHasSuggestionsType(boolean hasSuggestionsType) {
        this.hasSuggestionsType = hasSuggestionsType;
        return this;
    }

    public int[] getChildren() {
        return children;
    }

    public Node setChildren(int[] children) {
        this.children = children;
        return this;
    }

    public int getRedirectNode() {
        return redirectNode;
    }

    public Node setRedirectNode(int redirectNode) {
        this.redirectNode = redirectNode;
        return this;
    }

    public String getName() {
        return name;
    }

    public Node setName(String name) {
        this.name = name;
        return this;
    }

    public String getParser() {
        return parser;
    }

    public Node setParser(String parser) {
        this.parser = parser;
        return this;
    }

    public byte[] getProperties() {
        return properties;
    }

    public Node setProperties(byte[] properties) {
        this.properties = properties;
        return this;
    }

    public String getSuggestionType() {
        return suggestionType;
    }

    public Node setSuggestionType(String suggestionType) {
        this.suggestionType = suggestionType;
        return this;
    }

    @Override
    public String toString() {
        return "Node{" +
                "flags=" + flags +
                ", nodeType=" + nodeType +
                ", isExecutable=" + isExecutable +
                ", hasRedirect=" + hasRedirect +
                ", hasSuggestionsType=" + hasSuggestionsType +
                ", children=" + Arrays.toString(children) +
                ", redirectNode=" + redirectNode +
                ", name='" + name + '\'' +
                ", parser='" + parser + '\'' +
                ", properties=" + Arrays.toString(properties) +
                ", suggestionType='" + suggestionType + '\'' +
                '}';
    }

    //TODO: Finish
    public static class ParserProperties extends NetUtils {
        public static byte[] read(String parser, InputStream inputStream) throws IOException {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            if(parser.equals("brigadier:double")) {
                int fl = inputStream.read();
                outputStream.write(fl);
                double max;

            }


            return outputStream.toByteArray();
        }
    }
}
