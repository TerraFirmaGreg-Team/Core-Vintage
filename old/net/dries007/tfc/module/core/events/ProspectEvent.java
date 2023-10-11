package net.dries007.tfc.module.core.events;

import net.dries007.tfc.compat.gregtech.items.tools.behaviors.PropickBehavior.ProspectResult.Type;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Event fired when the Prospector's Pickaxe is used.
 * Carries all the data relative to the result displayed to the player.
 * One of the two subclasses will be used according to the logical side.
 */
public abstract class ProspectEvent extends Event {

    private final Side side;
    private final EntityPlayer player;
    private final BlockPos pos;
    private final Type type;
    private final String materialName;

    protected ProspectEvent(Side side, EntityPlayer player, BlockPos pos, Type type, String materialName) {
        this.side = side;
        this.player = player;
        this.pos = pos;
        this.type = type;
        this.materialName = materialName;
    }

    public Side getSide() {
        return side;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public BlockPos getBlockPos() {
        return pos;
    }

    public Type getResultType() {
        return type;
    }

    public String getMaterialName() {
        return materialName;
    }

    public static class Server extends ProspectEvent {
        public Server(EntityPlayer player, BlockPos pos, Type type, String materialName) {
            super(Side.SERVER, player, pos, type, materialName);
        }
    }

    public static class Client extends ProspectEvent {
        public Client(EntityPlayer player, BlockPos pos, Type type, String materialName) {
            super(Side.CLIENT, player, pos, type, materialName);
        }
    }
}
