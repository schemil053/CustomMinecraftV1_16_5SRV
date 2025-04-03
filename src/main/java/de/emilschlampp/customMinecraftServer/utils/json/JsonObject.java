package de.emilschlampp.customMinecraftServer.utils.json;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class JsonObject implements JsonElement {
    public HashMap<String, JsonElement> map;

    public JsonObject() {
        map = new HashMap<>();
    }

    @Override
    public int getInt(String key, int defaultValue) {
        JsonElement object = map.get(key);
        if (object != null) {
            return object.getInt(defaultValue);
        }
        return defaultValue;
    }

    @Override
    public long getLong(String key, long defaultValue) {
        JsonElement object = map.get(key);
        if (object != null) {
            return object.getLong(defaultValue);
        }
        return defaultValue;
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        JsonElement object = map.get(key);
        if (object != null) {
            return object.getFloat(defaultValue);
        }
        return defaultValue;
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        JsonElement object = map.get(key);
        if (object != null) {
            return object.getDouble(defaultValue);
        }
        return defaultValue;
    }

    @Override
    public boolean getBool(String key, boolean defaultValue) {
        JsonElement object = map.get(key);
        if (object != null) {
            return object.getBool(defaultValue);
        }
        return defaultValue;
    }

    @Override
    public String getString(String key, String defaultValue) {
        JsonElement object = map.get(key);
        if (object != null) {
            return object.getStringValue(defaultValue);
        }
        return defaultValue;
    }

    @Override
    public JsonArray getArray(String key) {
        JsonElement object = map.get(key);
        if (object instanceof JsonArray) {
            return (JsonArray) object;
        }
        return null;
    }

    @Override
    public JsonArray getArrayNoNull(String key) {
        JsonElement object = map.get(key);
        if (object instanceof JsonArray) {
            return (JsonArray) object;
        }
        return JsonArray.EMPTY_ARRAY;
    }

    @Override
    public JsonElement get(String key) {
        return map.get(key);
    }

    public JsonObject getObject(String key) {
        JsonElement object = map.get(key);
        if (object instanceof JsonObject) {
            return (JsonObject) object;
        }
        return null;
    }

    public JsonObject getObjectOrNew(String key) {
        JsonElement object = map.get(key);
        if (object instanceof JsonObject) {
            return (JsonObject) object;
        }
        return null;
    }


    public void put(String key, long value) {
        map.put(key, new JsonInt(value));
    }


    public void put(String key, double value) {
        map.put(key, new JsonFloat(value));
    }


    public void put(String key, boolean value) {
        map.put(key, new JsonOthers(false, value));
    }


    public void put(String key, String value) {
        map.put(key, new JsonString(value));
    }


    public void put(String key, JsonElement element) {
        map.put(key, element);
    }


    public boolean has(String key) {
        return map.containsKey(key);
    }

    public Set<String> getKeys() {
        return map.keySet();
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append('{');
        int c = 0;
        for (Entry<String, JsonElement> entries : map.entrySet()) {
            out.append("\"");
            out.append(entries.getKey());
            out.append("\":");
            out.append(entries.getValue().toString());
            if(c != map.size()-1) {
                out.append(',');
            }

            c++;
        }
        out.append('}');
        return out.toString();
    }
}
