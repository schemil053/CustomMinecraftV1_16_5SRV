package de.emilschlampp.customMinecraftServer.command;

import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.net.data.watcher.Particle;
import de.emilschlampp.customMinecraftServer.packets.play.OutParticlePacket;
import de.emilschlampp.customMinecraftServer.utils.Command;
import de.emilschlampp.customMinecraftServer.utils.generated.ParticleID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParticleActionCommand extends Command {

    public ParticleActionCommand() {
        super("particleaction");
    }

    @Override
    public void run(ServerConnectionThread connectionThread, String[] args) {
        super.run(connectionThread, args);
        if(args.length == 0) {
            connectionThread.asPacketPlayer().sendMessage("missing args");
            return;
        }

        Particle particle = new Particle(ParticleID.COMPOSTER_ID);

        if(args.length > 1) {
            for(String a : args) {
                run(connectionThread, new String[] {a});
            }
            return;
        }

        int c = 2000;
        double ca = 0.1;


        if(args[0].equals("1")) {
            new Thread(() -> {
                for (double t = 0; t < c; t=t+ca) {
                    double x = 0;
                    double y = 0;
                    double z = connectionThread.asPacketPlayer().z;

                    x = 1.4 * Math.sin(3 * t) + Math.sin(1.5 * t);
                    y = 1.4 * Math.cos(3 * t) + Math.cos(1.5 * t);

                    x += connectionThread.asPacketPlayer().x;
                    y += connectionThread.asPacketPlayer().y;

                    connectionThread.send(new OutParticlePacket(
                            particle,
                            false,
                            x, y, z, 0, 0, 0, 0, 2
                    ));
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {

                    }
                }
            }).start();
        }
        if(args[0].equals("2")) {
            new Thread(() -> {
                for (double t = 0; t < c*5; t=t+ca) {
                    double x = 0;
                    double y = 0;
                    double z = connectionThread.asPacketPlayer().z;

                    x = -8 * Math.sin(5 * t) - 5*Math.sin(9 * t);
                    y = -8 * Math.cos(5 * t) - 5* Math.cos(9 * t);

                    x += connectionThread.asPacketPlayer().x;
                    y += connectionThread.asPacketPlayer().y;

                    connectionThread.send(new OutParticlePacket(
                            particle,
                            false,
                            x, y, z, 0, 0, 0, 0, 2
                    ));
                }
            }).start();
        }

        if(args[0].equals("3")) {
            new Thread(() -> {
                for (double t = 0; t < c*5; t=t+ca) {
                    double x = 0;
                    double y = 0;
                    double z = connectionThread.asPacketPlayer().z;

                    x = 3 * Math.pow(Math.cos(t), 3);
                    y = 2 * Math.pow(Math.sin(t), 3);

                    x += connectionThread.asPacketPlayer().x;
                    y += connectionThread.asPacketPlayer().y;

                    connectionThread.send(new OutParticlePacket(
                            particle,
                            false,
                            x, y, z, 0, 0, 0, 0, 2
                    ));
                }
            }).start();
        }

        if(args[0].equals("4")) {
            new Thread(() -> {
                for (double t = 0; t < c*5; t=t+ca) {
                    double x = 0;
                    double y = 0;
                    double z = connectionThread.asPacketPlayer().z;

                    x = Math.cos(t);
                    y = Math.sin(t);

                    x += connectionThread.asPacketPlayer().x;
                    y += connectionThread.asPacketPlayer().y;

                    connectionThread.send(new OutParticlePacket(
                            particle,
                            false,
                            x, y, z, 0, 0, 0, 0, 2
                    ));
                }
            }).start();
        }

        if(args[0].equals("5")) {
            new Thread(() -> {
                for (double t = 0; t < c*5; t=t+ca) {
                    double x = 0;
                    double y = 0;
                    double z = connectionThread.asPacketPlayer().z;

                    x = Math.pow(Math.cos(t), 3);
                    y = Math.pow(Math.sin(t), 3);

                    x += connectionThread.asPacketPlayer().x;
                    y += connectionThread.asPacketPlayer().y;

                    connectionThread.send(new OutParticlePacket(
                            particle,
                            false,
                            x, y, z, 0, 0, 0, 0, 2
                    ));
                }
            }).start();
        }
    }

    @Override
    public List<String> tab(ServerConnectionThread connectionThread, String[] args) {
        List<String> s = new ArrayList<>();

        for(int x = 0; x<10; x++) {
            s.add(args[0].trim()+x);
        }

        return s;
    }
}
