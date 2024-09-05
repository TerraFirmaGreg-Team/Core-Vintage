package su.terrafirmagreg.modules.device.plugin.top.provider;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.objects.blocks.BlockFridge;
import su.terrafirmagreg.modules.device.objects.tiles.TileFridge;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;


import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;

import java.util.ArrayList;
import java.util.List;

import static su.terrafirmagreg.data.Properties.UPPER;

public final class ProviderFridge implements IProbeInfoProvider {

  @Override
  public String getID() {
    return ModUtils.localize("top", "device.fridge");
  }

  @Override
  public void addProbeInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world,
      IBlockState state, IProbeHitData hitData) {
    Block block = state.getBlock();
    BlockPos pos = hitData.getPos();
    if (block instanceof BlockFridge) {

      var tile = TileUtils.getTile(world, pos, TileFridge.class);
      if (tile == null) {
        return;
      }

      if (!state.getValue(UPPER)) {
        pos = pos.up();
      }

      IProbeInfo horizontalPane = info.horizontal(
          info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
      horizontalPane.text(
          new TextComponentTranslation(ModUtils.localize("top", "device.fridge.efficiency"),
              (int) tile.getEfficiency()).getFormattedText());
      if (tile.isOpen()) {
        int slot = BlockFridge.getPlayerLookingItem(pos.down(), player,
            state.getValue(BlockFridge.FACING));
        if (slot > -1) {
          ItemStack stack = tile.getSlot(slot);
          if (!stack.isEmpty()) {
            info.horizontal(info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
                .item(stack)
                .vertical()
                .itemLabel(stack);
            IFood cap = stack.getCapability(CapabilityFood.CAPABILITY, null);
            List<String> list = new ArrayList<>();
            if (cap != null) {
              cap.addTooltipInfo(stack, list, player);
            }
            for (String text : list) {
              info.text(text);
            }
          }
        }
      }

    }
  }
}
