package su.terrafirmagreg.api.helper;

import su.terrafirmagreg.api.util.BlockUtils;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.FluidRegistry;

import static su.terrafirmagreg.api.data.Properties.BoolProp.CLAY;

public class BlockHelper {

//  public static boolean isGrass(IBlockState current) {
//    return current.getBlock() instanceof IGrass;
//  }

  public static boolean isDirt(IBlockState current) {
//    var block = current.getBlock();
//    if (block instanceof ISoilBlock soil) {
//      return Variant.isVariant(soil.getVariant(),
//                               BlocksSoil.DIRT, BlocksSoil.COARSE_DIRT, BlocksSoil.ROOTED_DIRT);
//    }
    return false;
  }

  public static boolean isSoil(IBlockState current) {
//    var block = current.getBlock();
//    if (block instanceof BlockSoilPeat) {
//      return true;
//    }
//    if (block instanceof ISoilBlock soil) {
//      return Variant.isVariant(soil.getVariant(),
//                               BlocksSoil.GRASS, BlocksSoil.DRY_GRASS, BlocksSoil.PODZOL,
//                               BlocksSoil.MYCELIUM, BlocksSoil.DIRT, BlocksSoil.COARSE_DIRT,
//                               BlocksSoil.SPARSE_GRASS, BlocksSoil.ROOTED_DIRT);
//    }
    return false;
  }

  public static boolean isSoilOrGravel(IBlockState current) {
//    var block = current.getBlock();
//    if (block instanceof IRockBlock rock) {
//      return Variant.isVariant(rock.getVariant(), BlocksRock.GRAVEL);
//    }
//    if (block instanceof ISoilBlock soil) {
//      return Variant.isVariant(soil.getVariant(),
//                               BlocksSoil.GRASS, BlocksSoil.DRY_GRASS, BlocksSoil.COARSE_DIRT,
//                               BlocksSoil.SPARSE_GRASS, BlocksSoil.ROOTED_DIRT, BlocksSoil.DIRT,
//                               BlocksSoil.MUD, BlocksSoil.PODZOL, BlocksSoil.MYCELIUM);
//    }
    return false;
  }

  public static boolean isGround(IBlockState current) {
//    var block = current.getBlock();
//    if (block instanceof IRockBlock rock) {
//      return Variant.isVariant(rock.getVariant(),
//                               BlocksRock.GRAVEL, BlocksRock.SAND, BlocksRock.RAW);
//    }
//    if (block instanceof ISoilBlock soil) {
//      return Variant.isVariant(soil.getVariant(),
//                               BlocksSoil.GRASS, BlocksSoil.DRY_GRASS, BlocksSoil.COARSE_DIRT,
//                               BlocksSoil.SPARSE_GRASS, BlocksSoil.ROOTED_DIRT, BlocksSoil.DIRT,
//                               BlocksSoil.MUD, BlocksSoil.PODZOL, BlocksSoil.MYCELIUM);
//    }
    return false;
  }

  public static boolean isGrowableSoil(IBlockState current) {
//    var block = current.getBlock();
//    if (block instanceof ISoilBlock soil) {
//      return Variant.isVariant(soil.getVariant(),
//                               BlocksSoil.GRASS, BlocksSoil.DRY_GRASS, BlocksSoil.SPARSE_GRASS,
//                               BlocksSoil.DIRT, BlocksSoil.PODZOL, BlocksSoil.MYCELIUM);
//    }
    return false;
  }

  public static boolean isWater(IBlockState current) {
    return current.getMaterial() == Material.WATER;
  }

  public static boolean isSaltWater(IBlockState current) {
    return BlockUtils.isBlock(current.getBlock(), FluidRegistry.getFluid("salt_water").getBlock());
  }

  public static boolean isFreshWaterOrIce(IBlockState current) {
    return BlockUtils.isBlock(current.getBlock(), Blocks.ICE, FluidRegistry.getFluid("fresh_water").getBlock());
  }

  public static boolean isFreshWater(IBlockState current) {
    return BlockUtils.isBlock(current.getBlock(), FluidRegistry.getFluid("fresh_water").getBlock());
  }

  public static boolean isClay(IBlockState current) {
    return BlockUtils.hasProperty(current, CLAY);
  }
}
