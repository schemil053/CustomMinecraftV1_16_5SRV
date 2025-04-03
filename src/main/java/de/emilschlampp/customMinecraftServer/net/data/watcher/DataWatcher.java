package de.emilschlampp.customMinecraftServer.net.data.watcher;

import java.util.HashMap;
import java.util.Map;

public class DataWatcher {
    //https://wiki.vg/index.php?title=Entity_metadata&oldid=16538
    private Map<Integer, Object> data;

    public DataWatcher(Map<Integer, Object> data) {
        this.data = data;
    }

    public DataWatcher() {
        this(new HashMap<>());
    }

    public void put(Integer i, Object o) {
        data.put(i, o);
    }

    public void rem(Integer i) {
        data.remove(i);
    }

    public Object get(Integer i) {
        return data.get(i);
    }

    public void clear() {
        data.clear();
    }

    public Map<Integer, Object> getData() {
        return new HashMap<>(data);
    }
}
