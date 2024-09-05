package su.terrafirmagreg.modules.device.plugin.top.provider;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.data.Constants;
import su.terrafirmagreg.modules.device.objects.blocks.BlockLogPile;
import su.terrafirmagreg.modules.device.objects.tiles.TileLogPile;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;


import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IBlockDisplayOverride;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.api.TextStyleClass;

public class ProviderLogPile implements IProbeInfoProvider, IBlockDisplayOverride {

  @Override
  public String getID() {
    return ModUtils.localize("top", "device.log_pile");
  }

  @Override
  public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, EntityPlayer entityPlayer,
      World world, IBlockState iBlockState, IProbeHitData iProbeHitData) {

  }

  @Override
  public boolean overrideStandardInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player,
      World world, IBlockState state, IProbeHitData hitData) {
    Block block = state.getBlock();
    BlockPos pos = hitData.getPos();

    if (block instanceof BlockLogPile) {
      var tile = TileUtils.getTile(world, pos, TileLogPile.class);
      if (tile == null) {
        return false;
      }

      ItemStack icon = ItemStack.EMPTY;

      IItemHandler cap = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
      if (cap != null) {
        for (int i = 0; i < cap.getSlots(); i++) {
          ItemStack slotStack = cap.getStackInSlot(i);
          if (!slotStack.isEmpty()) {
            if (icon.isEmpty()) {
              icon = slotStack.copy();
            } else if (slotStack.isItemEqual(icon)) {
              icon.grow(slotStack.getCount());
            }
          }
        }
      }

      if (icon.isEmpty()) {
        icon = hitData.getPickBlock();
      }

      info.horizontal(info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
          .item(icon)
          .vertical()
          .itemLabel(icon)
          .text(TextStyleClass.MODNAME + Constants.MOD_NAME);

    }
    return false;
  }
}
