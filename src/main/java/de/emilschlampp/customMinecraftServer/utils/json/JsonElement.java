package de.emilschlampp.customMinecraftServer.utils.json;

public interface JsonElement {

    default int getInt(int defaultValue) {
        return defaultValue;
    }

    default long getLong(long defaultValue) {
        return defaultValue;
    }

    default float getFloat(float defaultValue) {
        return defaultValue;
    }

    default double getDouble(double defaultValue) {
        return defaultValue;
    }

    default boolean getBool(boolean defaultValue) {
        return defaultValue;
    }

    default String getStringValue(String defaultValue) {
        return defaultValue;
    }

    default boolean isNull() {
        return false;
    }


    default int getInt(String key, int defaultValue) {
        return defaultValue;
    }


    default long getLong(String key, long defaultValue) {
        return defaultValue;
    }


    default float getFloat(String key, float defaultValue) {
        return defaultValue;
    }


    default double getDouble(String key, double defaultValue) {
        return defaultValue;
    }


    default boolean getBool(String key, boolean defaultValue) {
        return defaultValue;
    }


    default String getString(String key, String defaultValue) {
        return defaultValue;
    }


    default JsonArray getArray(String key) {
        return null;
    }


    default JsonArray getArrayNoNull(String key) {
        return JsonArray.EMPTY_ARRAY;
    }


    default JsonElement get(String key) {
        return null;
    }
}
