package de.emilschlampp.customMinecraftServer.net;

import de.emilschlampp.customMinecraftServer.utils.DisconnectHandler;
import de.emilschlampp.customMinecraftServer.utils.PacketPlayer;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerConnectionThread extends Thread {
    public int state = 0;
    private boolean markedForClose;
    public final Map<String, Object> dataMap = new HashMap<>();

    private final List<Packet> queue = new ArrayList<>();
    public final ServerThread serverThread;

    private Socket socket;

    public ServerConnectionThread(Socket socket, ServerThread serverThread) {
        this.socket = socket;
        this.serverThread = serverThread;
    }

    public void send(Packet packet) {
        if(state != packet.getState()) {
            return;
        }
        synchronized (queue) {
            queue.add(packet);
        }
    }

    private void sendInternally(Packet packet) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            NetUtils.writeVarInt(packet.getID(), outputStream);
            packet.write(this, outputStream);

            NetUtils.writeVarInt(outputStream.size(), socket.getOutputStream());
            socket.getOutputStream().write(outputStream.toByteArray());
        } catch (Throwable throwable) {
            if(throwable instanceof SocketException) {
                return;
            }
            throwable.printStackTrace();
        }
    }

    public void writeQueue() {
        synchronized (queue) {
            queue.removeIf(packet -> {
                sendInternally(packet);
                return true;
            });
            if(markedForClose) {
                close();
            }
        }
    }

    @Override
    public void run() {
        InputStream inputStream;

        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            return;
        }

        while (socket.isConnected() && isAlive() && !isInterrupted()) {
            try {

                int len = NetUtils.readVarInt(inputStream);

                byte[] read = new byte[len];
                inputStream.read(read);

                ByteArrayInputStream c = new ByteArrayInputStream(read);

                int id = NetUtils.readVarInt(c);

                try {
                    PacketRegistry.received(this, id, c);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            } catch (Throwable throwable) {
                if(throwable instanceof EOFException) {
                    handleDisconnect();
                    return;
                }
                if(throwable instanceof SocketException) {
                    handleDisconnect();
                    return;
                }
                throwable.printStackTrace();
            }
        }
    }

    public void handleDisconnect() {
        new DisconnectHandler().handle(this);
    }

    public PacketPlayer asPacketPlayer() {
        return PacketPlayer.get(this);
    }

    public void close() {
        if(socket.isConnected()) {
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }

    public void markForClose() {
        markedForClose = true;
    }

    public boolean isMarkedForClose() {
        return markedForClose;
    }
}
