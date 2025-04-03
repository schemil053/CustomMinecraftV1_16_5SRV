package de.emilschlampp.customMinecraftServer.utils;

import de.emilschlampp.customMinecraftServer.net.NetUtils;
import de.emilschlampp.customMinecraftServer.utils.json.JsonArray;
import de.emilschlampp.customMinecraftServer.utils.json.JsonElement;
import de.emilschlampp.customMinecraftServer.utils.json.JsonObject;
import de.emilschlampp.customMinecraftServer.utils.json.JsonParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegistryProvider {
    private static final String REGISTRY_PREFIX = "/versions/";
    private final String registryName;

    private Registry blocksRegistry;
    private Registry soundRegistry;
    private Registry itemRegistry;
    private Registry blockIDRegistry;
    private Registry entityRegistry;
    private Registry particleRegistry;


    public RegistryProvider(String registryName) {
        this.registryName = registryName;
    }

    public RegistryProvider readAll() {
        return readBlockRegistry().readRegistryFile();
    }

    public RegistryProvider readBlockRegistry() {
        blocksRegistry = new Registry();

        String name = buildName("blocks", "json");

        String content = readFully(name);

        JsonObject object = JsonParser.parseObjectFromString(content);

        for (String key : object.getKeys()) {
            JsonArray states = object.getObject(key).getArray("states");

            for (JsonElement jsonElement : states.array) {
                if(jsonElement instanceof JsonObject) {
                    int id = jsonElement.getInt("id", -1);
                    if(id != -1 && jsonElement.getBool("default", false)) {
                        blocksRegistry.data.put(key, id);
                    }
                }
            }
        }

        return this;
    }

    public RegistryProvider readRegistryFile() {
        soundRegistry = new Registry();
        itemRegistry = new Registry();
        blockIDRegistry = new Registry();
        entityRegistry = new Registry();
        particleRegistry = new Registry();

        String name = buildName("registries", "json");
        String content = readFully(name);

        JsonObject object = JsonParser.parseObjectFromString(content);


        readRegistry(object.getObject("minecraft:sound_event"), soundRegistry);
        readRegistry(object.getObject("minecraft:item"), itemRegistry);
        readRegistry(object.getObject("minecraft:block"), blockIDRegistry);
        readRegistry(object.getObject("minecraft:entity_type"), entityRegistry);
        readRegistry(object.getObject("minecraft:particle_type"), particleRegistry);


        return this;
    }

    private void readRegistry(JsonObject registryData, RegistryProvider.Registry registry) {
        registry.protocolID = registryData.getInt("protocol_id", -1);

        JsonObject entries = registryData.getObject("entries");

        for (String key : entries.getKeys()) {
            JsonObject object = entries.getObject(key);

            registry.data.put(key, object.getInt("protocol_id", -1));
        }
    }

    public String getRegistryName() {
        return registryName;
    }

    public Registry getBlocksRegistry() {
        return blocksRegistry;
    }

    public Registry getSoundRegistry() {
        return soundRegistry;
    }

    public Registry getEntityRegistry() {
        return entityRegistry;
    }

    public Registry getItemRegistry() {
        return itemRegistry;
    }

    public Registry getBlockIDRegistry() {
        return blockIDRegistry;
    }

    public Registry getParticleRegistry() {
        return particleRegistry;
    }

    private String readFully(String name) {
        try {
            return new String(NetUtils.readAllBytes(getClass().getResourceAsStream(name)));
        } catch (Throwable e) {
            return "";
        }
    }

    private String buildName(String reg, String format) {
        return REGISTRY_PREFIX+registryName+(registryName.endsWith("/") ? "" : "/")+reg+"."+format;
    }

    public static class Registry {
        private final Map<String, Integer> data = new HashMap<>();
        private int protocolID = -1;

        public int lookup(String val) {
            return data.get(val);
        }

        public String lookup(int id) {
            return data.entrySet().stream().filter(d -> d.getValue() == id).map(Map.Entry::getKey).findFirst().orElse(null);
        }

        public Map<String, Integer> getData() {
            return new HashMap<>(data);
        }

        public int getProtocolID() {
            return protocolID;
        }
    }
}
