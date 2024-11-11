package su.terrafirmagreg.modules.plant.api.types.variant.block;

import su.terrafirmagreg.api.base.block.spi.IBlockSettings;
import su.terrafirmagreg.api.library.types.variant.block.IVariantBlock;
import su.terrafirmagreg.modules.plant.api.types.type.PlantType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IPlantBlock extends IVariantBlock<PlantBlockVariant, PlantType>, IBlockSettings {

  default boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
    return false;
  }
}
