package de.emilschlampp.customMinecraftServer.packets.play;

import de.emilschlampp.customMinecraftServer.net.Packet;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class OutEntityStatusPacket extends Packet {
    private int entityID;
    private byte status;

    public OutEntityStatusPacket(int entityID, byte status) {
        this.entityID = entityID;
        this.status = status;
    }

    public OutEntityStatusPacket() {
    }

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public int getID() {
        return 0x1A;
    }

    @Override
    public Packet read(ServerConnectionThread connectionThread, ByteArrayInputStream inputStream, DataInputStream dataInputStream) throws Throwable {
        entityID = readVarInt(inputStream);
        status = (byte) inputStream.read();
        return this;
    }

    @Override
    public Packet write(ServerConnectionThread connectionThread, ByteArrayOutputStream outputStream) throws Throwable {
        writeInt(entityID, outputStream);
        outputStream.write(status);
        return this;
    }

    @Override
    public void handle(ServerConnectionThread connectionThread) throws Throwable {

    }

    public static enum Status {
        LIVING_ENTITY_HURT(2),
        LIVING_ENTITY_DIE(3),
        LIVING_ENTITY_SHIELD_BLOCK(29),
        LIVING_ENTITY_SHIELD_BREAK(30),
        LIVING_ENTITY_SHIELD_THORNS(33),
        LIVING_ENTITY_DROWN_HURT(36),
        LIVING_ENTITY_BURN_HURT(37),
        LIVING_ENTITY_BERRY_HURT(44),
        LIVING_ENTITY_PORTAL(46),
        LIVING_ENTITY_BREAK_MAIN_HAND(47),
        LIVING_ENTITY_BREAK_OFF_HAND(48),
        LIVING_ENTITY_BREAK_HEAD_SLOT(49),
        LIVING_ENTITY_BREAK_CHEST_SLOT(50),
        LIVING_ENTITY_BREAK_LEGS_SLOT(51),
        LIVING_ENTITY_BREAK_FEET_SLOT(52),
        LIVING_ENTITY_HONEY(54),
        LIVING_ENTITY_SWAP_ITEMS(55),

        PLAYER_USE_FINISHED(9),
        PLAYER_REDUCED_DEBUG_SCREEN_ON(22),
        PLAYER_REDUCED_DEBUG_SCREEN_OFF(23),
        PLAYER_OP_PERM_LEVEL0(24),
        PLAYER_OP_PERM_LEVEL1(25),
        PLAYER_OP_PERM_LEVEL2(26),
        PLAYER_OP_PERM_LEVEL3(27),
        PLAYER_OP_PERM_LEVEL4(28),
        PLAYER_CLOUD_PARTICLES(43),

        ARMOR_STAND_HIT(32),
        MOB_EXPLOSIVE_PARTICLE(20),
        SQUID_RESET(19),
        DOLPIN_HAPPY(38),
        ANIMAL_LOVE(18),
        HORSE_SMOKE(6),
        HORSE_HEARTH(7),
        CAT_SMOKE(40),
        CAT_HEARTH(41),
        RABBIT_JUMP(1),
        SHEEP_EAT(10),
        FOX_CHEWING(45),
        TAME_SMOKE(6),
        TAME_HEARTH(7),
        WOLF_WATER_SHAKING(8),
        VILLAGER_MATING_HEART(12),
        VILLAGER_ANGRY(13),
        VILLAGER_HAPPY(14),
        VILLAGER_SPLASH(42),
        IRON_GOLEM_ATTACK(4),
        IRON_GOLEM_ROSE(11),
        IRON_GOLEM_ROSE_AWAY(34),
        EVOKER_FANGS_ATTACK(4),
        WITCH_MAGIC(15),
        RAVAGER_ATTACK(4),
        RAVAGER_STUNNED(39),
        ZOMBIE_VILLAGER_CURE(16),
        GUARDIAN_ATTACK(21),
        TNT_MINECART_IGNITE(10),
        MINECART_SPAWNER_DELAY_RESET(1),
        FIREWORK_ROCKET_EXPLOSION(17),
        TIPPED_ARROW_PARTICLE(0),
        FISHING_HOOK_CAUGHT(31),
        EGG_THROWN(3),
        SNOWBALL_POOF(3),
        TOTEM_UNDYING(35)
        ;

        public final byte status;

        Status(int status) {
            this.status = (byte) status;
        }
    }
}
