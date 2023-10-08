package net.dries007.tfc.module.core.network;

import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.FoodStatsTFC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import su.terrafirmagreg.tfc.TerraFirmaCraft;

/**
 * This packet is send to client to signal that it may need to replace the vanilla FoodStats with a TFC version
 * Since all the relevant events where this is listened for are server only
 * {@link CapabilityFood}
 */
public class SCPacketFoodStatsReplace implements IMessageEmpty, IMessageHandler<SCPacketFoodStatsReplace, IMessage> {
    @Override
    public IMessage onMessage(SCPacketFoodStatsReplace message, MessageContext ctx) {
        TerraFirmaCraft.getProxy().getThreadListener(ctx).addScheduledTask(() -> {
            EntityPlayer player = TerraFirmaCraft.getProxy().getPlayer(ctx);
            if (player != null) {
                FoodStatsTFC.replaceFoodStats(player);
            }
        });
        return null;
    }
}
