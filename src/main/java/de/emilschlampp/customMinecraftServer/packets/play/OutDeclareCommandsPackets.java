package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.net.data.Node;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.util.Arrays;

public class OutDeclareCommandsPackets extends Packet {
    private int rootIndex;
    private Node[] nodes;

    public OutDeclareCommandsPackets(int rootIndex, Node[] nodes) {
        this.rootIndex = rootIndex;
        this.nodes = nodes;
    }

    public OutDeclareCommandsPackets() {
    }

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x10;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        writeVarInt(nodes.length, outputStream);
        for (Node node : nodes) {
            writeNode(node, outputStream);
        }
        writeVarInt(rootIndex, outputStream);
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {

    }
}
