package net.dries007.tfc.objects.blocks;

import net.dries007.tfc.util.OreDictionaryHelper;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class BlockBale extends BlockRotatedPillar implements ICapabilitySize {

  public BlockBale() {
    super(new Material(MapColor.FOLIAGE));
    this.setSoundType(SoundType.PLANT);
    setHardness(0.6F);
    net.dries007.tfc.util.OreDictionaryHelper.register(this, "thatch");
    OreDictionaryHelper.register(this, "bale");
    Blocks.FIRE.setFireInfo(this, 60, 20);
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

  @SideOnly(Side.CLIENT)
  @Override
  @Nonnull
  public BlockRenderLayer getRenderLayer() {
    return BlockRenderLayer.CUTOUT_MIPPED;
  }

  /**
   * Block's chance to react to a living entity falling on it.
   */
  public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
    entityIn.fall(fallDistance, 0.2F);
  }
}
