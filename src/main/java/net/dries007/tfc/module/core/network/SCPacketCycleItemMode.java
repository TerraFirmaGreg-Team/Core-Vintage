package net.dries007.tfc.module.core.network;

import net.dries007.tfc.api.capability.player.CapabilityPlayerData;
import net.dries007.tfc.api.capability.player.IPlayerData;
import net.dries007.tfc.compat.gregtech.items.tools.TFGToolItems;
import net.dries007.tfc.module.rock.api.recipes.RecipeRockChisel;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import su.terrafirmagreg.tfc.TerraFirmaCraft;

public class SCPacketCycleItemMode implements IMessageEmpty, IMessageHandler<SCPacketCycleItemMode, IMessage> {
    @Override
    public IMessage onMessage(SCPacketCycleItemMode message, MessageContext ctx) {
        TerraFirmaCraft.getProxy().getThreadListener(ctx).addScheduledTask(() -> {
            var player = TerraFirmaCraft.getProxy().getPlayer(ctx);
            if (player != null) {
                if (player.getHeldItemMainhand().getItem() == TFGToolItems.CHISEL.get()) {
                    IPlayerData capability = player.getCapability(CapabilityPlayerData.CAPABILITY, null);

                    if (capability != null) {
                        RecipeRockChisel.Mode mode = capability.getChiselMode();
                        capability.setChiselMode(mode.next());
                        capability.updateAndSync();
                    }
                }

            }
        });
        return null;
    }
}
