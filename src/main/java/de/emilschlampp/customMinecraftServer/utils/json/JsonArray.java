package de.emilschlampp.customMinecraftServer.utils.json;

import java.util.ArrayList;

public class JsonArray implements JsonElement {
    public static final JsonArray EMPTY_ARRAY = new JsonArray();
    public final ArrayList<JsonElement> array = new ArrayList<>();

    public void getInts(int[] defaultValues) {
        for (int i = 0; i < Math.min(defaultValues.length, array.size()); i++) {
            defaultValues[i] = array.get(i).getInt(defaultValues[i]);
        }
    }

    public void getInts(long[] defaultValues) {
        for (int i = 0; i < Math.min(defaultValues.length, array.size()); i++) {
            defaultValues[i] = array.get(i).getLong(defaultValues[i]);
        }
    }

    public void getFloats(float[] defaultValues) {
        for (int i = 0; i < Math.min(defaultValues.length, array.size()); i++) {
            defaultValues[i] = array.get(i).getFloat(defaultValues[i]);
        }
    }

    public void getDoubles(double[] defaultValues) {
        for (int i = 0; i < Math.min(defaultValues.length, array.size()); i++) {
            defaultValues[i] = array.get(i).getDouble(defaultValues[i]);
        }
    }

    public void getBools(boolean[] defaultValues) {
        for (int i = 0; i < Math.min(defaultValues.length, array.size()); i++) {
            defaultValues[i] = array.get(i).getBool(defaultValues[i]);
        }
    }

    public void getStrings(String[] defaultValues) {
        for (int i = 0; i < Math.min(defaultValues.length, array.size()); i++) {
            defaultValues[i] = array.get(i).getStringValue(defaultValues[i]);
        }
    }

    public String[] getStrings() {
        String[] stringArray = new String[array.size()];
        for (int i = 0; i < array.size(); i++) {
            stringArray[i] = array.get(i).getStringValue("");
        }
        return stringArray;
    }


    public void addInts(int... values) {
        for (int i = 0; i < values.length; i++) {
            array.add(new JsonInt(values[i]));
        }
    }

    public void addFloats(float... values) {
        for (int i = 0; i < values.length; i++) {
            array.add(new JsonFloat(values[i]));
        }
    }

    public void addBools(boolean... values) {
        for (int i = 0; i < values.length; i++) {
            array.add(new JsonOthers(false, values[i]));
        }
    }

    public void addStrings(String... values) {
        for (int i = 0; i < values.length; i++) {
            array.add(new JsonString(values[i]));
        }
    }


    public void add(JsonElement element) {
        array.add(element);
    }

    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append('[');
        for (int i = 0; i < array.size(); i++) {
            out.append(array.get(i).toString());
            if(i != array.size() -1) {
                out.append(',');
            }
        }
        out.append(']');
        return out.toString();
    }
}