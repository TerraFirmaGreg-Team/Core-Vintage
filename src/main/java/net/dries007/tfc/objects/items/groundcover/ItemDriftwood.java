package net.dries007.tfc.objects.items.groundcover;

import net.dries007.tfc.util.OreDictionaryHelper;

import net.minecraft.item.ItemStack;

import mcp.MethodsReturnNonnullByDefault;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.objects.blocks.groundcover.BlockDriftwood;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemDriftwood extends ItemBlockTFC {

  public ItemDriftwood(BlockDriftwood block) {
    super(block);
    net.dries007.tfc.util.OreDictionaryHelper.register(this, "wood");
    net.dries007.tfc.util.OreDictionaryHelper.register(this, "wood_driftwood");
    OreDictionaryHelper.register(this, "driftwood");
  }

  @Nonnull
  @Override
  public Size getSize(ItemStack stack) {
    return Size.SMALL;
  }

  @Nonnull
  @Override
  public Weight getWeight(ItemStack stack) {
    return Weight.LIGHT;
  }

  @Override
  public int getItemStackLimit(ItemStack stack) {
    return getStackSize(stack);
  }
}
