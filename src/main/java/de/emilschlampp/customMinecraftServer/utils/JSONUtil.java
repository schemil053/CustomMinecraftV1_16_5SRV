package de.emilschlampp.customMinecraftServer.utils;

public class JSONUtil {
    public static String escape(String a) {
        return a
                .replace("\\", "\\\\")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t")
                .replace("\"", "\\\"");
    }

    public static String simpleText(String text) {
        return "{\"text\": \"" + JSONUtil.escape(text) + "\"}";
    }

    public static String coloredText(String text) {
        return "{\"text\": \"" + escape(text).replace("§", "\\u00a7") + "\"}";
    }
}
