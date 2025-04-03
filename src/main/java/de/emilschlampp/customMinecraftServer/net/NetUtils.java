package de.emilschlampp.customMinecraftServer.net;

import de.emilschlampp.customMinecraftServer.net.data.CommandMatch;
import de.emilschlampp.customMinecraftServer.net.data.Node;
import de.emilschlampp.customMinecraftServer.net.data.BlockPosition;
import de.emilschlampp.customMinecraftServer.net.data.Slot;

import java.io.*;
import java.util.UUID;

public class NetUtils {
    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;

    public static int readVarInt(InputStream in) throws IOException {
        int value = 0;
        int position = 0;
        int currentByte;

        while (true) {
            currentByte = in.read();

            if(currentByte == -1) {
                throw new EOFException();
            }

            value |= (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;

            if (position >= 32) throw new RuntimeException("VarInt is too big");
        }

        return value;
    }

    public static String readVarString(InputStream inputStream) throws IOException {
        int len = readVarInt(inputStream);

        char[] chars = new char[len];

        for(int i = 0; i<chars.length; i++) {
            chars[i] = (char) inputStream.read();
        }

        return new String(chars);
    }

    public static void writeVarString(String string, OutputStream outputStream) throws IOException {
        writeVarInt(string.length(), outputStream);
        for (char c : string.toCharArray()) {
            outputStream.write(c);
        }
    }

    public static UUID readVarUUID(InputStream inputStream) throws IOException {
        return new UUID(readLong(inputStream), readLong(inputStream));
    }

    public static void writeVarUUID(UUID uuid, OutputStream outputStream) throws IOException {
        writeLong(uuid.getMostSignificantBits(), outputStream);
        writeLong(uuid.getLeastSignificantBits(), outputStream);
    }

    public static void writeLong(long l, OutputStream outputStream) throws IOException {
        byte[] writeBuffer = new byte[8];
        writeBuffer[0] = (byte)(l >>> 56);
        writeBuffer[1] = (byte)(l >>> 48);
        writeBuffer[2] = (byte)(l >>> 40);
        writeBuffer[3] = (byte)(l >>> 32);
        writeBuffer[4] = (byte)(l >>> 24);
        writeBuffer[5] = (byte)(l >>> 16);
        writeBuffer[6] = (byte)(l >>>  8);
        writeBuffer[7] = (byte)(l >>>  0);
        outputStream.write(writeBuffer, 0, 8);
    }

    public static long readLong(InputStream inputStream) throws IOException {
        byte[] readBuffer = new byte[8];

        inputStream.read(readBuffer);

        return (((long)readBuffer[0] << 56) +
                ((long)(readBuffer[1] & 255) << 48) +
                ((long)(readBuffer[2] & 255) << 40) +
                ((long)(readBuffer[3] & 255) << 32) +
                ((long)(readBuffer[4] & 255) << 24) +
                ((readBuffer[5] & 255) << 16) +
                ((readBuffer[6] & 255) <<  8) +
                ((readBuffer[7] & 255) <<  0));
    }

    public static void writeInt(int i, OutputStream outputStream) throws IOException {
        outputStream.write((i >>> 24) & 0xFF);
        outputStream.write((i >>> 16) & 0xFF);
        outputStream.write((i >>>  8) & 0xFF);
        outputStream.write((i >>>  0) & 0xFF);
    }

    public static int readInt(InputStream inputStream) throws IOException {
        int ch1 = inputStream.read();
        int ch2 = inputStream.read();
        int ch3 = inputStream.read();
        int ch4 = inputStream.read();
        if ((ch1 | ch2 | ch3 | ch4) < 0) {
            throw new EOFException();
        }
        return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
    }

    public static BlockPosition readPosition(InputStream inputStream) throws IOException {
        long val = readLong(inputStream);
        int x = (int) (val >> 38);
        int y = (int) (val << 52 >> 52);
        int z = (int) (val << 26 >> 38);


        return new BlockPosition(x, y, z);
    }

    public static void writePosition(BlockPosition blockPosition, OutputStream outputStream) throws IOException {
        writeLong(((long) (blockPosition.getX() & 0x3FFFFFF) << 38) | ((long) (blockPosition.getZ() & 0x3FFFFFF) << 12) | (blockPosition.getY() & 0xFFF), outputStream);
    }

    public static void writeDouble(double i, OutputStream outputStream) throws IOException {
        writeLong(Double.doubleToLongBits(i), outputStream);
    }

    public static double readDouble(InputStream inputStream) throws IOException {
        return Double.longBitsToDouble(readLong(inputStream));
    }

    public static void writeNode(Node node, OutputStream outputStream) throws IOException {
        outputStream.write(node.getFlags());

        if(node.getChildren() == null) {
            writeVarInt(0, outputStream);
        } else {
            writeVarInt(node.getChildren().length, outputStream);
            for (int child : node.getChildren()) {
                writeVarInt(child, outputStream);
            }
        }

        if(node.isHasRedirect()) {
            writeVarInt(node.getRedirectNode(), outputStream);
        }
        if(node.getNodeType() == 1 || node.getNodeType() == 2) {
            writeVarString(node.getName(), outputStream);
        }
        if(node.getNodeType() == 2) {
            writeVarString(node.getParser(), outputStream);
            outputStream.write(node.getProperties());
            if(node.isHasSuggestionsType()) {
                writeVarString(node.getSuggestionType(), outputStream);
            }
        }
    }

    public static byte[] createVarIntByteArray(int i) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            writeVarInt(i, outputStream);
        } catch (IOException ignored) {}
        return outputStream.toByteArray();
    }

    public static Node readNode(InputStream inputStream) throws IOException {
        Node node = new Node((byte) inputStream.read());
        node.convertFlagsToValues();

        int[] children = new int[readVarInt(inputStream)];
        for(int i = 0; i<children.length; i++) {
            children[i] = readVarInt(inputStream);
        }

        if(node.isHasRedirect()) {
            node.setRedirectNode(readVarInt(inputStream));
        }

        if(node.getNodeType() == 1 || node.getNodeType() == 2) {
            node.setName(readVarString(inputStream));
        }

        if(node.getNodeType() == 2) {
            node.setParser(readVarString(inputStream));
            node.setProperties(Node.ParserProperties.read(node.getParser(), inputStream));
            if(node.isHasSuggestionsType() && node.getNodeType() == 2) {
                node.setSuggestionType(readVarString(inputStream));
            }
        }

        return node;
    }

    public static void writeCommandMatch(CommandMatch match, OutputStream outputStream) throws IOException {
        writeVarString(match.getMatch(), outputStream);
        writeBoolean(match.isHasTooltip(), outputStream);
        if(match.isHasTooltip()) {
            writeVarString(match.getTooltip(), outputStream);
        }
    }

    public static CommandMatch readCommandMatch(InputStream inputStream) throws IOException {
        CommandMatch commandMatch = new CommandMatch();

        commandMatch.setMatch(readVarString(inputStream));
        commandMatch.setHasTooltip(readBoolean(inputStream));

        if(commandMatch.isHasTooltip()) {
            commandMatch.setTooltip(readVarString(inputStream));
        }

        return commandMatch;
    }

    public static void writeSlot(Slot slot, OutputStream outputStream) throws IOException {
        writeBoolean(slot.isPresent(), outputStream);
        if(slot.isPresent()) {
            writeVarInt(slot.getId(), outputStream);
            outputStream.write(slot.getCount());
            writeVarInt(slot.getNbt().length, outputStream);
            outputStream.write(slot.getNbt());
        }
    }

    public static Slot readSlot(InputStream inputStream) throws IOException {
        Slot slot = new Slot();
        slot.setPresent(readBoolean(inputStream));

        if(slot.isPresent()) {
            slot.setId(readVarInt(inputStream));
            slot.setCount((byte) inputStream.read());
            byte[] r = new byte[readVarInt(inputStream)];
            inputStream.read(r);
            slot.setNbt(r);
        }

        return slot;
    }

    public static void writeShort(int i, OutputStream outputStream) throws IOException {
        outputStream.write((i >>> 8) & 0xFF);
        outputStream.write((i >>> 0) & 0xFF);
    }

    public static int readShort(InputStream inputStream) throws IOException {
        int ch1 = inputStream.read();
        int ch2 = inputStream.read();
        if ((ch1 | ch2) < 0)
            throw new EOFException();
        return (short)((ch1 << 8) + (ch2 << 0));
    }

    public static void writeBoolean(boolean i, OutputStream outputStream) throws IOException {
        outputStream.write(i ? 1 : 0);
    }

    public static boolean readBoolean(InputStream inputStream) throws IOException {
        return inputStream.read() == 1;
    }

    public static void writeFloat(float i, OutputStream outputStream) throws IOException {
        writeInt(Float.floatToIntBits(i), outputStream);
    }

    public static float readFloat(InputStream inputStream) throws IOException {
        return Float.intBitsToFloat(readInt(inputStream));
    }

    public static byte[] readAllBytes(InputStream inputStream) throws IOException {
        byte[] i = new byte[inputStream.available()];
        inputStream.read(i);
        return i;
    }

    public static long readVarLong(InputStream in) throws IOException {
        long value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = (byte) in.read();
            value |= (long) (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;

            if (position >= 64) throw new RuntimeException("VarLong is too big");
        }

        return value;
    }

    public static void writeVarInt(int value, OutputStream outputStream) throws IOException {
        while (true) {
            if ((value & ~SEGMENT_BITS) == 0) {
                outputStream.write(value);
                return;
            }

            outputStream.write((value & SEGMENT_BITS) | CONTINUE_BIT);
            value >>>= 7;
        }
    }

    public static void writeVarLong(long value, OutputStream outputStream) throws IOException {
        while (true) {
            if ((value & ~((long) SEGMENT_BITS)) == 0) {
                outputStream.write((int) value);
                return;
            }

            outputStream.write((int) ((value & SEGMENT_BITS) | CONTINUE_BIT));

            value >>>= 7;
        }
    }
}
