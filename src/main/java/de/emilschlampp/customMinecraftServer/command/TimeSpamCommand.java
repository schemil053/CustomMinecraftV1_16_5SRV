package de.emilschlampp.customMinecraftServer.command;

import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.packets.play.OutTimeUpdatePacket;
import de.emilschlampp.customMinecraftServer.utils.Command;

import java.util.concurrent.atomic.AtomicBoolean;

public class TimeSpamCommand extends Command {
    public TimeSpamCommand() {
        super("spamtime");
    }

    @Override
    public void run(ServerConnectionThread connectionThread, String[] args) {
        if(connectionThread.dataMap.containsKey("spamtime-command.spam")) {
            return;
        }
        connectionThread.dataMap.put("spamtime-command.spam",true);

        AtomicBoolean o = new AtomicBoolean(false);
        new Thread(() -> {
            while (connectionThread.isAlive() && !connectionThread.isMarkedForClose()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {

                }
                o.set(!o.get());
                if(o.get()) {
                    connectionThread.send(new OutTimeUpdatePacket(0, 10));
                } else {
                    connectionThread.send(new OutTimeUpdatePacket(0, 22000));
                }
            }
        }).start();
    }
}
