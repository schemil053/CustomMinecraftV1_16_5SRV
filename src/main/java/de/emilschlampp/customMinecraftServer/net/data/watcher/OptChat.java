package de.emilschlampp.customMinecraftServer.net.data.watcher;

import de.emilschlampp.customMinecraftServer.net.NetUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class OptChat {
    public boolean present;
    public String val;

    public OptChat(boolean present, String val) {
        this.present = present;
        this.val = val;
    }

    public OptChat() {
    }

    public boolean isPresent() {
        return present;
    }

    public OptChat setPresent(boolean present) {
        this.present = present;
        return this;
    }

    public String getVal() {
        return val;
    }

    public OptChat setVal(String val) {
        this.val = val;
        return this;
    }

    public void write(OutputStream outputStream) throws IOException {
        NetUtils.writeBoolean(present, outputStream);
        if(present) {
            NetUtils.writeVarString(val, outputStream);
        }
    }

    public static OptChat read(InputStream inputStream) throws IOException {
        OptChat optChat = new OptChat();

        optChat.setPresent(NetUtils.readBoolean(inputStream));

        if(optChat.present) {
            optChat.setVal(NetUtils.readVarString(inputStream));
        }

        return optChat;
    }
}
