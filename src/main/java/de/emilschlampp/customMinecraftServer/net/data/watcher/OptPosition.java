package de.emilschlampp.customMinecraftServer.net.data.watcher;

import de.emilschlampp.customMinecraftServer.net.NetUtils;
import de.emilschlampp.customMinecraftServer.net.data.BlockPosition;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class OptPosition {
    public boolean present;
    public BlockPosition val;

    public OptPosition(boolean present, BlockPosition val) {
        this.present = present;
        this.val = val;
    }

    public OptPosition() {
    }

    public boolean isPresent() {
        return present;
    }

    public OptPosition setPresent(boolean present) {
        this.present = present;
        return this;
    }

    public BlockPosition getVal() {
        return val;
    }

    public OptPosition setVal(BlockPosition val) {
        this.val = val;
        return this;
    }

    public void write(OutputStream outputStream) throws IOException {
        NetUtils.writeBoolean(present, outputStream);
        if(present) {
            NetUtils.writePosition(val, outputStream);
        }
    }

    public static OptPosition read(InputStream inputStream) throws IOException {
        OptPosition optChat = new OptPosition();

        optChat.setPresent(NetUtils.readBoolean(inputStream));

        if(optChat.present) {
            optChat.setVal(NetUtils.readPosition(inputStream));
        }

        return optChat;
    }
}
