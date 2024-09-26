package com.buuz135.hotornot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import com.buuz135.hotornot.object.item.ItemMetalTongsJawMold;
import net.dries007.tfc.client.gui.GuiLiquidTransfer;
import net.dries007.tfc.objects.container.ContainerLiquidTransfer;

import org.jetbrains.annotations.Nullable;

public class HotGuiHandler implements IGuiHandler {

  /**
   * This is a quick and dirty way to do this via a magic number. If we add more containers we should move to an enum like how TFC does it
   */
  public static void openMoldGui(final World world, final EntityPlayer player) {
    player.openGui(HotOrNot.getInstance(), 0, world, 0, 0, 0);
  }

  @Nullable
  @Override
  public Container getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
    if (ID == 0) {
      final ItemStack heldStack = player.getHeldItemMainhand();
      return new ContainerLiquidTransfer(player.inventory,
                                         heldStack.getItem() instanceof ItemMetalTongsJawMold ? heldStack : player.getHeldItemMainhand());
    }
    return null;
  }

  @Nullable
  @Override
  public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
    final Container container = getServerGuiElement(ID, player, world, x, y, z);
    if (ID == 0) {
      return new GuiLiquidTransfer(container, player, player.getHeldItemMainhand()
                                                            .getItem() instanceof ItemMetalTongsJawMold);
    }
    return null;
  }
}
