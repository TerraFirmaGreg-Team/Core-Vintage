package net.dries007.tfc.network;

import su.terrafirmagreg.modules.core.capabilities.player.CapabilityPlayer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.recipes.ChiselRecipe;
import net.dries007.tfc.objects.items.metal.ItemMetalChisel;

public class PacketCycleItemMode implements IMessageEmpty {

  public static final class Handler implements IMessageHandler<PacketCycleItemMode, IMessage> {

    @Override
    public IMessage onMessage(PacketCycleItemMode message, MessageContext ctx) {
      TerraFirmaCraft.getProxy().getThreadListener(ctx).addScheduledTask(() -> {
        EntityPlayer player = TerraFirmaCraft.getProxy().getPlayer(ctx);
        if (player != null) {
          if (player.getHeldItemMainhand().getItem() instanceof ItemMetalChisel) {
            var cap = CapabilityPlayer.get(player);

            if (cap != null) {
              ChiselRecipe.Mode mode = cap.getChiselMode();
              cap.setChiselMode(mode.next());
              cap.updateAndSync();
            }
          }

        }
      });
      return null;
    }
  }
}
