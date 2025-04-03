package de.emilschlampp.customMinecraftServer.net;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class PacketRegistry {

    private static List<Packet> packets = new ArrayList<>();

    static {
        try {
            for (Class aClass : getClasses("de.emilschlampp.customMinecraftServer.packets")) {
                if (aClass.getSimpleName().equals("Packet")) {
                    continue;
                }
                if (Packet.class.isAssignableFrom(aClass)) {
                    packets.add((Packet) aClass.newInstance());
                }
            }
        } catch (Throwable throwable) {

        }
    }


    public static void received(ServerConnectionThread client, int packetID, ByteArrayInputStream inputStream) throws Throwable {
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        for (Packet packet : packets) {
            if (packet.getID() == packetID && packet.getState() == client.state && !packet.isClientbound()) {
                Packet p = packet.newInstance().read(client, inputStream, dataInputStream);
                p.handle(client);
               // System.out.println("Known packet: "+p.getClass().getSimpleName());
                return;
            }
        }

        System.out.println("Unknown packet: " + packetID);
    }


    private static Class[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
