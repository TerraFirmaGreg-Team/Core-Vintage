package net.dries007.tfcflorae.objects.items;

import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

import mcp.MethodsReturnNonnullByDefault;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.dries007.tfcflorae.objects.blocks.blocktype.BlockSlabTFCF;
import net.dries007.tfcflorae.objects.blocks.wood.fruitwood.BlockFruitSlab;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemSlabTFCF extends ItemSlab implements ICapabilitySize {

  public ItemSlabTFCF(BlockFruitSlab.Half slab, BlockFruitSlab.Half slab1, BlockFruitSlab.Double doubleSlab) {
    super(slab, slab1, doubleSlab);
  }

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
