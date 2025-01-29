package net.dries007.tfc.objects.items;

import mcp.MethodsReturnNonnullByDefault;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import net.dries007.tfc.objects.blocks.BlockSlabTFC;

import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemSlabTFC extends ItemSlab implements ICapabilitySize {

  public ItemSlabTFC(BlockSlabTFC.Half slab, BlockSlabTFC.Half slab1, BlockSlabTFC.Double doubleSlab) {
    super(slab, slab1, doubleSlab);
  }

  @Nonnull
  @Override
  public Size getSize(ItemStack stack) {
    return Size.SMALL; // if blocks fits in small vessels, this should too
  }

  @Nonnull
  @Override
  public Weight getWeight(ItemStack stack) {
    return Weight.VERY_LIGHT; // Double the stacksize of a block (or 64)
  }
}
