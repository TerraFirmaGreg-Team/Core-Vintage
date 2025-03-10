package net.dries007.tfc.objects.blocks;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

@ParametersAreNonnullByDefault
public class BlockDecorativeStone extends Block implements ICapabilitySize {

  public static final Map<EnumDyeColor, BlockDecorativeStone> ALABASTER_BRICKS = new HashMap<>();
  public static final Map<EnumDyeColor, BlockDecorativeStone> ALABASTER_POLISHED = new HashMap<>();
  public static final Map<EnumDyeColor, BlockDecorativeStone> ALABASTER_RAW = new HashMap<>();

  public BlockDecorativeStone(MapColor blockMapColorIn) {
    super(Material.ROCK, blockMapColorIn);
    setSoundType(SoundType.STONE);
    setHardness(1.0F);
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
}
