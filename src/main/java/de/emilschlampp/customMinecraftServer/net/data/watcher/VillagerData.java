package de.emilschlampp.customMinecraftServer.net.data.watcher;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class VillagerData {
    private VarInt type;
    private VarInt profession;
    private VarInt level;

    public VillagerData(VarInt type, VarInt profession, VarInt level) {
        this.type = type;
        this.profession = profession;
        this.level = level;
    }

    public VillagerData() {
    }

    public static VillagerData read(InputStream inputStream) throws IOException {
        return new VillagerData(
                VarInt.read(inputStream),
                VarInt.read(inputStream),
                VarInt.read(inputStream)
        );
    }

    public void write(OutputStream outputStream) throws IOException {
        type.write(outputStream);
        profession.write(outputStream);
        level.write(outputStream);
    }

    public VarInt getType() {
        return type;
    }

    public VillagerData setType(VarInt type) {
        this.type = type;
        return this;
    }

    public VarInt getProfession() {
        return profession;
    }

    public VillagerData setProfession(VarInt profession) {
        this.profession = profession;
        return this;
    }

    public VarInt getLevel() {
        return level;
    }

    public VillagerData setLevel(VarInt level) {
        this.level = level;
        return this;
    }
}
