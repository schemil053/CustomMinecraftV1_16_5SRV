package de.emilschlampp.customMinecraftServer.utils;

import de.emilschlampp.customMinecraftServer.packets.play.OutChunkUnloadPacket;
import de.emilschlampp.customMinecraftServer.utils.event.SEventHandler;
import de.emilschlampp.customMinecraftServer.utils.event.SListener;
import de.emilschlampp.customMinecraftServer.utils.event.impl.player.PlayerMoveEvent;
import de.emilschlampp.customMinecraftServer.utils.event.impl.player.PlayerMoveWithOldEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultServerLogicListener implements SListener {
    @SEventHandler
    public void onMove(PlayerMoveWithOldEvent event) {
        if(1 == 1) {
            return;
        }
        int gcx = (int) (event.getPlayer().x/16);
        int gcz = (int) (event.getPlayer().z/16);

        int gcxo = (int) (event.getOld().getX()/16);
        int gczo = (int) (event.getOld().getZ()/16);

        int dir = 16;

        List<int[]> intL = new ArrayList<>();

        for(int x = -dir; x<=dir; x++) {
            for (int z = -dir; z <= dir; z++) {
                int cx = gcxo + x;
                int cz = gczo + z;
                if(event.getPlayer().serverConnectionThread.dataMap.containsKey("chunk."+cx+"."+cz)) {
                    intL.add(new int[]{cx, cz});
                }
            }
        }

        for(int x = -dir; x<=dir; x++) {
            for(int z = -dir; z<=dir; z++) {
                int cx = gcx+x;
                int cz = gcz+z;
                intL.removeIf(e -> {
                    return e.length == 0 || (e[0] == cx && e[1] == cz);
                });
                if(!event.getPlayer().serverConnectionThread.dataMap.containsKey("chunk."+cx+"."+cz)) {
                    try {
                        event.getPlayer().sendChunk(cx, cz);
                    } catch (IOException e) {

                    }
                    event.getPlayer().serverConnectionThread.dataMap.put("chunk."+cx+"."+cz, 1);
                }
            }
        }

        for (int[] ints : intL) {
            event.getPlayer().send(new OutChunkUnloadPacket(ints[0], ints[1]));
            event.getPlayer().serverConnectionThread.dataMap.remove("chunk."+ints[0]+"."+ints[1]);
        }

    }
}
