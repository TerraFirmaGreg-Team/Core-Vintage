package net.dries007.tfc.objects.items;

import su.terrafirmagreg.modules.core.feature.size.capability.ICapabilitySize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;

import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.objects.blocks.blocktype.BlockSlabTFCF;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemSlabTFCF extends ItemSlab implements ICapabilitySize {

  public ItemSlabTFCF(BlockSlabTFCF.Half slab, BlockSlabTFCF.Half slab1, BlockSlabTFCF.Double doubleSlab) {
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
