package de.emilschlampp.customMinecraftServer.utils.entity;

import de.emilschlampp.customMinecraftServer.net.data.Location;
import de.emilschlampp.customMinecraftServer.utils.EntityIDProvider;
import de.emilschlampp.customMinecraftServer.utils.generated.EntityID;

public class EnderEyeEntity extends Entity {
    public EnderEyeEntity(Location location) {
        super(location, EntityID.EYE_OF_ENDER_ID, 2);
    }
}
