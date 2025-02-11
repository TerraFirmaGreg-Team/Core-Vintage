package net.dries007.tfc.objects.items.groundcover;

import mcp.MethodsReturnNonnullByDefault;

import net.dries007.tfc.util.OreDictionaryHelper;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.objects.blocks.groundcover.BlockSurfaceSeashells;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemSeashell extends ItemBlockTFC {

  public ItemSeashell(BlockSurfaceSeashells block) {
    super(block);
    net.dries007.tfc.util.OreDictionaryHelper.register(this, "seashell");
    OreDictionaryHelper.register(this, "seashells");
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
