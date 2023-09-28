package net.dries007.tfc.network;

import net.dries007.tfc.TerraFirmaGreg;
import net.dries007.tfc.client.util.TFCGuiHandler;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.config.InventoryCraftingMode;
import net.dries007.tfc.util.Helpers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketOpenCraftingGui implements IMessageEmpty {
    public static final class Handler implements IMessageHandler<PacketOpenCraftingGui, IMessage> {
        @Override
        public IMessage onMessage(PacketOpenCraftingGui message, MessageContext ctx) {
            TerraFirmaGreg.getProxy().getThreadListener(ctx).addScheduledTask(() -> {
                EntityPlayer player = TerraFirmaGreg.getProxy().getPlayer(ctx);
                if (player != null) {
                    if (ConfigTFC.General.PLAYER.inventoryCraftingMode == InventoryCraftingMode.ALWAYS || (ConfigTFC.General.PLAYER.inventoryCraftingMode == InventoryCraftingMode.ENABLED && Helpers.playerHasItemMatchingOre(player.inventory, "workbench"))) {
                        TFCGuiHandler.openGui(player.world, player, TFCGuiHandler.Type.CRAFTING);
                    }
                }
            });
            return null;
        }
    }
}
