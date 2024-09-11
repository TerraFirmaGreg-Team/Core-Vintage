package net.dries007.tfc.network;

import su.terrafirmagreg.api.util.OreDictUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.client.TFCGuiHandler;
import net.dries007.tfc.util.config.InventoryCraftingMode;

public class PacketOpenCraftingGui implements IMessageEmpty {

  public static final class Handler implements IMessageHandler<PacketOpenCraftingGui, IMessage> {

    @Override
    public IMessage onMessage(PacketOpenCraftingGui message, MessageContext ctx) {
      TerraFirmaCraft.getProxy().getThreadListener(ctx).addScheduledTask(() -> {
        EntityPlayer player = TerraFirmaCraft.getProxy().getPlayer(ctx);
        if (player != null) {
          if (ConfigTFC.General.PLAYER.inventoryCraftingMode == InventoryCraftingMode.ALWAYS ||
                  (ConfigTFC.General.PLAYER.inventoryCraftingMode == InventoryCraftingMode.ENABLED &&
                          OreDictUtils.playerHasItemMatchingOre(player.inventory, "workbench"))) {
            TFCGuiHandler.openGui(player.world, player, TFCGuiHandler.Type.CRAFTING);
          }
        }
      });
      return null;
    }
  }
}
