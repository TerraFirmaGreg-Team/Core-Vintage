package net.dries007.tfc.network;

import net.dries007.tfc.TerraFirmaGreg;
import net.dries007.tfc.api.capability.player.CapabilityPlayerData;
import net.dries007.tfc.api.capability.player.IPlayerData;
import net.dries007.tfc.api.recipes.ChiselRecipe;
import net.dries007.tfc.compat.gregtech.items.tools.TFGToolItems;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketCycleItemMode implements IMessageEmpty {
    public static final class Handler implements IMessageHandler<PacketCycleItemMode, IMessage> {
        @Override
        public IMessage onMessage(PacketCycleItemMode message, MessageContext ctx) {
            TerraFirmaGreg.getProxy().getThreadListener(ctx).addScheduledTask(() -> {
                var player = TerraFirmaGreg.getProxy().getPlayer(ctx);
                if (player != null) {
                    if (player.getHeldItemMainhand().getItem() == TFGToolItems.CHISEL.get()) {
                        IPlayerData capability = player.getCapability(CapabilityPlayerData.CAPABILITY, null);

                        if (capability != null) {
                            ChiselRecipe.Mode mode = capability.getChiselMode();
                            capability.setChiselMode(mode.next());
                            capability.updateAndSync();
                        }
                    }

                }
            });
            return null;
        }
    }
}
