package de.emilschlampp.customMinecraftServer.utils.entity;

import de.emilschlampp.customMinecraftServer.net.data.Location;
import de.emilschlampp.customMinecraftServer.net.data.Slot;
import de.emilschlampp.customMinecraftServer.utils.generated.EntityID;

public class ItemEntity extends Entity {
    public ItemEntity(Location location, Slot slot) {
        super(location, EntityID.ITEM_ID, 2);
        getWatcher().put(7, slot);
    }
}
