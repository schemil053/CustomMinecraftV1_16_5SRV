package de.emilschlampp.customMinecraftServer.net.data.watcher;

import de.emilschlampp.customMinecraftServer.net.NetUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class VarInt {
    private int value;

    public VarInt(int value) {
        this.value = value;
    }

    public void write(OutputStream outputStream) throws IOException {
        NetUtils.writeVarInt(value, outputStream);
    }

    public static VarInt read(InputStream inputStream) throws IOException {
        return new VarInt(NetUtils.readVarInt(inputStream));
    }
}
