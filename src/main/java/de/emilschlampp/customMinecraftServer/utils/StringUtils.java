package de.emilschlampp.customMinecraftServer.utils;

import java.util.Arrays;

public class StringUtils {
    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static long[] concatL(long[] first, long[] second) {
        long[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static String[] removeFirstElement(String[] arr) {
        if (arr.length == 0) {
            return arr;
        }
        String[] newArr = new String[arr.length - 1];
        System.arraycopy(arr, 1, newArr, 0, arr.length - 1);
        return newArr;
    }
}
