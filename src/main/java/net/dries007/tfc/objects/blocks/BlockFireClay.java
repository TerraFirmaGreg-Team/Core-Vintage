package net.dries007.tfc.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import net.dries007.tfc.objects.items.ItemsTFC;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
public class BlockFireClay extends Block implements ICapabilitySize {

  public BlockFireClay() {
    super(Material.CLAY);
    setSoundType(SoundType.GROUND);
    setHardness(1.0F);
  }

  @Override
  public int quantityDropped(Random random) {
    return 4;
  }

  @Nonnull
  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return ItemsTFC.FIRE_CLAY;
  }

  @Nonnull
  @Override
  public Size getSize(ItemStack stack) {
    return Size.VERY_SMALL;
  }

  @Nonnull
  @Override
  public Weight getWeight(ItemStack stack) {
    return Weight.HEAVY;
  }
}
