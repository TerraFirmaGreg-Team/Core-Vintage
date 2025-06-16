package su.terrafirmagreg.modules.soil.feature.soiltype.spi;

import net.minecraft.block.state.IBlockState;

public interface ISoilBlock {

  IBlockState getDirt();

  //  default boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
//    int beachDistance = 2;
//
//    IBlockState blockState = this.asBlock().getDefaultState();
//
//    if (plantable instanceof BlockPlant plant) {
//      switch (plant.getEnumPlantType()) {
//        case CLAY -> {
//          return Variant.isVariant(getVariant(), DIRT, GRASS, DRY_GRASS, COARSE_DIRT, MUD, PODZOL, SPARSE_GRASS) && BlockHelper.isClay(blockState);
//        }
//        case DESERT_CLAY -> {
//          return Variant.isVariant(getVariant(), MUD, SAND) || BlockHelper.isClay(blockState);
//        }
//        case DRY_CLAY -> {
//          return Variant.isVariant(getVariant(), DIRT, DRY_GRASS, COARSE_DIRT, MUD, PODZOL, SAND, SPARSE_GRASS) || BlockHelper.isClay(blockState);
//        }
//        case DRY -> {
//          return Variant.isVariant(getVariant(), DIRT, COARSE_DIRT, DRY_GRASS, MUD, SAND, SPARSE_GRASS);
//        }
//        case FRESH_WATER -> {
//          return Variant.isVariant(getVariant(), DIRT, GRASS, DRY_GRASS, GRAVEL, SPARSE_GRASS, COARSE_DIRT);
//        }
//        case SALT_WATER -> {
//          return Variant.isVariant(getVariant(), DIRT, GRASS, DRY_GRASS, SAND, GRAVEL, COARSE_DIRT, SPARSE_GRASS, PODZOL);
//        }
//        case FRESH_BEACH -> {
//          boolean flag = false;
//          for (EnumFacing facing : EnumFacing.HORIZONTALS) {
//            for (int i = 1; i <= beachDistance; i++) {
//              if (BlockHelper.isFreshWaterOrIce(world.getBlockState(pos.offset(facing, i)))) {
//                flag = true;
//                break;
//              }
//            }
//          }
//          return (Variant.isVariant(getVariant(), DIRT, GRASS, DRY_GRASS, SAND, COARSE_DIRT, MUD, PODZOL, SPARSE_GRASS)) && flag;
//        }
//        case SALT_BEACH -> {
//          boolean flag = false;
//          for (EnumFacing facing : EnumFacing.HORIZONTALS) {
//            for (int i = 1; i <= beachDistance; i++) {
//              if (BlockHelper.isSaltWater(world.getBlockState(pos.offset(facing, i)))) {
//                flag = true;
//              }
//            }
//          }
//          return (Variant.isVariant(getVariant(), DIRT, GRASS, DRY_GRASS, SAND, COARSE_DIRT, MUD, SPARSE_GRASS)) && flag;
//        }
//      }
//    } else if (plantable instanceof BlockCropTFC) {
//      IBlockState cropState = world.getBlockState(pos.up());
//      if (cropState.getBlock() instanceof BlockCropTFC) {
//        boolean isWild = cropState.getValue(WILD);
//        if (isWild) {
//          if (Variant.isVariant(getVariant(), DIRT, GRASS, DRY_GRASS, PODZOL, SPARSE_GRASS, COARSE_DIRT) || BlockHelper.isClay(blockState)) {
//            return true;
//          }
//        }
//        return Variant.isVariant(getVariant(), FARMLAND);
//      }
//    }
//
//    switch (plantable.getPlantType(world, pos.offset(direction))) {
//      case Plains -> {
//        return Variant.isVariant(getVariant(), DIRT, GRASS, FARMLAND, DRY_GRASS, COARSE_DIRT, MUD, PODZOL, SPARSE_GRASS) || BlockHelper.isClay(blockState);
//      }
//      case Crop -> {
//        return Variant.isVariant(getVariant(), FARMLAND);
//      }
//      case Desert -> {
//        return Variant.isVariant(getVariant(), SAND);
//      }
//      case Cave -> {
//        return true;
//      }
//      case Water, Nether -> {
//        return false;
//      }
//      case Beach -> {
//        boolean flag = false;
//        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
//          for (int i = 1; i <= beachDistance; i++) {
//            if (BlockHelper.isWater(world.getBlockState(pos.offset(facing, i)))) {
//              flag = true;
//            }
//          }
//        }
//        return (Variant.isVariant(getVariant(), DIRT, GRASS, DRY_GRASS, SAND, COARSE_DIRT, MUD, PODZOL, SPARSE_GRASS)) && flag;
//      }
//    }
//
//    return false;
//  }
}
