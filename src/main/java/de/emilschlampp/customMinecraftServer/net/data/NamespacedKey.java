package de.emilschlampp.customMinecraftServer.net.data;

public class NamespacedKey {
    private String tag;
    private String key;

    public NamespacedKey(String tag, String key) {
        this.tag = tag;
        this.key = key;
    }

    public NamespacedKey(String key) {
        if(key.contains(":")) {
            this.tag = key.split(":", 2)[0];
            this.key = key.split(":", 2)[1];
        } else {
            this.key = key;
            this.tag = "minecraft";
        }
    }

    @Override
    public String toString() {
        return tag+":"+key;
    }

    public NamespacedKey setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public NamespacedKey setKey(String key) {
        this.key = key;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public String getKey() {
        return key;
    }
}
