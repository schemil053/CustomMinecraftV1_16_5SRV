package de.emilschlampp.customMinecraftServer.net.data;

import de.emilschlampp.customMinecraftServer.net.NetUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Chat {
    public String val;

    public Chat(String val) {
        this.val = val;
    }

    public Chat() {
    }

    public String getVal() {
        return val;
    }

    public Chat setVal(String val) {
        this.val = val;
        return this;
    }

    public void write(OutputStream outputStream) throws IOException {
        NetUtils.writeVarString(val, outputStream);
    }

    public static Chat read(InputStream inputStream) throws IOException {
        Chat optChat = new Chat();

        optChat.setVal(NetUtils.readVarString(inputStream));

        return optChat;
    }
}
