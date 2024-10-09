package su.terrafirmagreg.modules.soil.api.types.variant.block;

import su.terrafirmagreg.api.base.block.spi.IBlockSettings;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.data.lib.types.variant.block.IVariantBlock;
import su.terrafirmagreg.modules.plant.object.block.BlockPlant;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;

import net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC;

import static su.terrafirmagreg.data.Properties.BoolProp.WILD;
import static su.terrafirmagreg.modules.rock.init.BlocksRock.GRAVEL;
import static su.terrafirmagreg.modules.rock.init.BlocksRock.SAND;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.COARSE_DIRT;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.DIRT;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.DRY_GRASS;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.FARMLAND;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.GRASS;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.MUD;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.PODZOL;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.SPARSE_GRASS;

public interface ISoilBlock extends IVariantBlock<SoilBlockVariant, SoilType>, IBlockSettings {


  default boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
    int beachDistance = 2;

    IBlockState blockState = this.getBlock().getDefaultState();

    if (plantable instanceof BlockPlant plant) {
      switch (plant.getEnumPlantType()) {
        case CLAY -> {
          return BlockUtils.isVariant(getVariant(), DIRT, GRASS, DRY_GRASS, COARSE_DIRT, MUD, PODZOL, SPARSE_GRASS) && BlockUtils.isClay(blockState);
        }
        case DESERT_CLAY -> {
          return BlockUtils.isVariant(getVariant(), MUD, SAND) || BlockUtils.isClay(blockState);
        }
        case DRY_CLAY -> {
          return BlockUtils.isVariant(getVariant(), DIRT, DRY_GRASS, COARSE_DIRT, MUD, PODZOL, SAND, SPARSE_GRASS) || BlockUtils.isClay(blockState);
        }
        case DRY -> {
          return BlockUtils.isVariant(getVariant(), DIRT, COARSE_DIRT, DRY_GRASS, MUD, SAND, SPARSE_GRASS);
        }
        case FRESH_WATER -> {
          return BlockUtils.isVariant(getVariant(), DIRT, GRASS, DRY_GRASS, GRAVEL, SPARSE_GRASS, COARSE_DIRT);
        }
        case SALT_WATER -> {
          return BlockUtils.isVariant(getVariant(), DIRT, GRASS, DRY_GRASS, SAND, GRAVEL, COARSE_DIRT, SPARSE_GRASS, PODZOL);
        }
        case FRESH_BEACH -> {
          boolean flag = false;
          for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            for (int i = 1; i <= beachDistance; i++) {
              if (BlockUtils.isFreshWaterOrIce(world.getBlockState(pos.offset(facing, i)))) {
                flag = true;
                break;
              }
            }
          }
          return (BlockUtils.isVariant(getVariant(), DIRT, GRASS, DRY_GRASS, SAND, COARSE_DIRT, MUD, PODZOL, SPARSE_GRASS)) && flag;
        }
        case SALT_BEACH -> {
          boolean flag = false;
          for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            for (int i = 1; i <= beachDistance; i++) {
              if (BlockUtils.isSaltWater(world.getBlockState(pos.offset(facing, i)))) {
                flag = true;
              }
            }
          }
          return (BlockUtils.isVariant(getVariant(), DIRT, GRASS, DRY_GRASS, SAND, COARSE_DIRT, MUD, SPARSE_GRASS)) && flag;
        }
      }
    } else if (plantable instanceof BlockCropTFC) {
      IBlockState cropState = world.getBlockState(pos.up());
      if (cropState.getBlock() instanceof BlockCropTFC) {
        boolean isWild = cropState.getValue(WILD);
        if (isWild) {
          if (BlockUtils.isVariant(getVariant(), DIRT, GRASS, DRY_GRASS, PODZOL, SPARSE_GRASS, COARSE_DIRT) || BlockUtils.isClay(blockState)) {
            return true;
          }
        }
        return BlockUtils.isVariant(getVariant(), FARMLAND);
      }
    }

    switch (plantable.getPlantType(world, pos.offset(direction))) {
      case Plains -> {
        return BlockUtils.isVariant(getVariant(), DIRT, GRASS, FARMLAND, DRY_GRASS, COARSE_DIRT, MUD, PODZOL, SPARSE_GRASS) || BlockUtils.isClay(blockState);
      }
      case Crop -> {
        return BlockUtils.isVariant(getVariant(), FARMLAND);
      }
      case Desert -> {
        return BlockUtils.isVariant(getVariant(), SAND);
      }
      case Cave -> {
        return true;
      }
      case Water, Nether -> {
        return false;
      }
      case Beach -> {
        boolean flag = false;
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
          for (int i = 1; i <= beachDistance; i++) {
            if (BlockUtils.isWater(world.getBlockState(pos.offset(facing, i)))) {
              flag = true;
            }
          }
        }
        return (BlockUtils.isVariant(getVariant(), DIRT, GRASS, DRY_GRASS, SAND, COARSE_DIRT, MUD, PODZOL, SPARSE_GRASS)) && flag;
      }
    }

    return false;
  }
}
