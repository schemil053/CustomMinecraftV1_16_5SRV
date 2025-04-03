package de.emilschlampp.customMinecraftServer.net.data.watcher;

import de.emilschlampp.customMinecraftServer.net.NetUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class OptUUID {
    public boolean present;
    public UUID val;

    public OptUUID(boolean present, UUID val) {
        this.present = present;
        this.val = val;
    }

    public OptUUID() {
    }

    public boolean isPresent() {
        return present;
    }

    public OptUUID setPresent(boolean present) {
        this.present = present;
        return this;
    }

    public UUID getVal() {
        return val;
    }

    public OptUUID setVal(UUID val) {
        this.val = val;
        return this;
    }

    public void write(OutputStream outputStream) throws IOException {
        NetUtils.writeBoolean(present, outputStream);
        if(present) {
            NetUtils.writeVarUUID(val, outputStream);
        }
    }

    public static OptUUID read(InputStream inputStream) throws IOException {
        OptUUID optChat = new OptUUID();

        optChat.setPresent(NetUtils.readBoolean(inputStream));

        if(optChat.present) {
            optChat.setVal(NetUtils.readVarUUID(inputStream));
        }

        return optChat;
    }
}
