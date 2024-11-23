package su.terrafirmagreg.modules.flora.api.types.variant.block;

import su.terrafirmagreg.api.base.block.spi.IBlockSettings;
import su.terrafirmagreg.api.library.types.variant.block.IVariantBlock;
import su.terrafirmagreg.api.registry.provider.IProviderBlockColor;
import su.terrafirmagreg.modules.flora.api.types.type.FloraType;
import su.terrafirmagreg.modules.soil.client.GrassColorHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IFloraBlock extends IVariantBlock<FloraBlockVariant, FloraType>, IBlockSettings, IProviderBlockColor {

  default boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
    return false;
  }

  @Override
  default IBlockColor getBlockColor() {
    return GrassColorHandler::computeGrassColor;
  }
}
