package net.dries007.tfc.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class BlockFireBrick extends Block implements ICapabilitySize {

  public BlockFireBrick() {
    super(Material.ROCK);
    setSoundType(SoundType.STONE);
    setHardness(1.0F);
  }

  @Nonnull
  @Override
  public Size getSize(ItemStack stack) {
    return Size.SMALL; // Stored everywhere
  }

  @Nonnull
  @Override
  public Weight getWeight(ItemStack stack) {
    return Weight.LIGHT; // Stacksize = 32
  }
}
