package de.emilschlampp.customMinecraftServer.utils.generate;

import de.emilschlampp.customMinecraftServer.net.data.NamespacedKey;
import de.emilschlampp.customMinecraftServer.utils.RegistryProvider;

import java.io.FileWriter;
import java.util.Map;

public class ParticleIDGenerator {
    private static final String REGISTRY_NAME = "1-16-5";

    private static final String NEWLINE = "\n";
    public static void main(String[] args) throws Throwable {
        // TODO: 26.08.2023 Change line 24 and 72 for name, change line 32 and 62 for type

        StringBuilder content = new StringBuilder("//Autogenerated content");

        content.append(NEWLINE);

        content.append("package de.emilschlampp.customMinecraftServer.utils.generated;").append(NEWLINE);

        content.append(NEWLINE);
        content.append("import de.emilschlampp.customMinecraftServer.utils.RegistryProvider;" + NEWLINE + NEWLINE +
                "public class ParticleID {" + NEWLINE);

        content.append("    ").append("public static final String REGISTRY_VERSION = ").append("\"").append(REGISTRY_NAME).append("\";").append(NEWLINE);

        RegistryProvider provider = new RegistryProvider(REGISTRY_NAME);

        provider.readAll();

        RegistryProvider.Registry registry = provider.getParticleRegistry();

        for (Map.Entry<String, Integer> stringIntegerEntry : registry.getData().entrySet()) {
            NamespacedKey key = new NamespacedKey(stringIntegerEntry.getKey());

            content.append("    ").append("public static final String ").append(key.getKey().toUpperCase()).append("_NAME = \"").append(key.toString()).append("\";").append(NEWLINE);
        }


        content.append(NEWLINE);

        for (Map.Entry<String, Integer> stringIntegerEntry : registry.getData().entrySet()) {
            NamespacedKey key = new NamespacedKey(stringIntegerEntry.getKey());

            content.append("    ").append("public static int ").append(key.getKey().toUpperCase()).append("_ID;").append(NEWLINE);
        }

        content.append(NEWLINE);

        content.append("    ").append("private static boolean init = false;");
        content.append(NEWLINE);
        content.append("    ").append("public static void init(RegistryProvider provider) {");
        content.append(NEWLINE);
        content.append("        ").append("if(init) { return; } init = true;");
        content.append(NEWLINE);
        content.append(NEWLINE);

        for (Map.Entry<String, Integer> stringIntegerEntry : registry.getData().entrySet()) {
            NamespacedKey key = new NamespacedKey(stringIntegerEntry.getKey());

            content.append("        ").append(key.getKey().toUpperCase()).append("_ID = provider.getParticleRegistry().lookup(\"").append(key.toString()).append("\");").append(NEWLINE);
        }

        content.append(NEWLINE);
        content.append("    ").append("}");

        content.append(NEWLINE);
        content.append("}");


        FileWriter writer = new FileWriter("ParticleID.java");
        writer.write(content.toString());
        writer.close();
    }
}
