package su.terrafirmagreg.modules.soil.api.spi;

import su.terrafirmagreg.api.helper.BlockHelper;
import su.terrafirmagreg.api.library.types.type.IType;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;
import su.terrafirmagreg.modules.soil.object.block.BlockSoilPeat;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public interface IGrassBlock extends ISoilBlock {


  default void updateTick(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {
    if (worldIn.isRemote) {
      return;
    }

    if (!worldIn.isAreaLoaded(pos, 3)) {
      return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
    }
    Block block = worldIn.getBlockState(pos).getBlock();
    if (block instanceof IType<?> type) {
      if (type.getType() instanceof SoilType soilType) {

        if (worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up()).getLightOpacity(worldIn, pos.up()) > 2) {
          worldIn.setBlockState(pos, BlocksSoil.DIRT.get(soilType).getDefaultState());

        } else {
          if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
            for (int i = 0; i < 4; ++i) {
              BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

              if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !worldIn.isBlockLoaded(blockpos)) {
                return;
              }

              IBlockState iblockstate = worldIn.getBlockState(blockpos.up());
              IBlockState iblockstate1 = worldIn.getBlockState(blockpos);

              if (iblockstate1.getBlock() == BlocksSoil.DIRT.get(soilType) && worldIn.getLightFromNeighbors(blockpos.up()) >= 4
                  && iblockstate.getLightOpacity(worldIn, pos.up()) <= 2) {
                worldIn.setBlockState(blockpos, BlocksSoil.GRASS.get(soilType).getDefaultState());
              }
            }
          }
        }
      }
    }

  }

  default void spreadGrass(World world, BlockPos pos, IBlockState us, Random rand) {
    // Получаем позицию верхнего блока
    BlockPos upPos = pos.up();
    // Получаем состояние верхнего блока
    IBlockState up = world.getBlockState(upPos);
    // Получаем значение освещенности от соседних блоков
    int neighborLight = world.getLightFromNeighbors(upPos);
    // Получаем тип блока, с которого распространяется трава
    Block usBlock = us.getBlock();

    // Проверяем условие для генерации торфа
    if (up.getMaterial().isLiquid() || (neighborLight < 4 && up.getLightOpacity(world, upPos) > 2)) {

      // Генерируем торф в зависимости от типа блока
      if (usBlock instanceof BlockSoilPeat) {
        world.setBlockState(pos, BlocksSoil.PEAT.getDefaultState());

      } else if (usBlock instanceof ISoilBlock soil) {
        world.setBlockState(pos, soil.getDirt());
      }
    } else if (neighborLight >= 9) {
      for (int i = 0; i < 4; ++i) {
        // Генерируем случайную позицию вокруг исходной позиции
        BlockPos target = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

        // Проверяем, находится ли целевая позиция в пределах допустимой области
        if (world.isOutsideBuildHeight(target) || !world.isBlockLoaded(target)) {
          return;
        }

        // Получаем состояние текущего блока
        IBlockState current = world.getBlockState(target);

        // Пропускаем итерацию, если текущий блок не является почвой или уже имеет траву
        if (!BlockHelper.isSoil(current) || BlockHelper.isGrass(current)) {
          continue;
        }

        // Получаем позицию верхнего блока целевой позиции
        BlockPos targetUp = target.up();
        // Получаем состояние верхнего блока целевой позиции
        IBlockState targetUpState;

        // Пропускаем итерацию, если верхний блок жидкость или имеет высокую прозрачность
        if (world.getLightFromNeighbors(targetUp) < 4 || (targetUpState = world.getBlockState(targetUp)).getMaterial().isLiquid() || targetUpState.getLightOpacity(world, targetUp) > 3) {
          continue;
        }

        // Получаем тип текущего блока
        Block currentBlock = current.getBlock();

        // Генерируем траву в зависимости от типа текущего блока
        if (currentBlock instanceof IDirtBlock dirtBlock) {
          world.setBlockState(target, dirtBlock.getGrass());
        }
      }
      // Генерируем короткую траву на верхнем блоке с определенной вероятностью
//      for (FloraType plant : FloraType.getTypes()) {
//        if (plant.getCategory() == FloraCategories.SHORT_GRASS && rand.nextFloat() < 0.5f) {
//          float temp = Climate.getActualTemp(world, upPos);
//          var plantBlock = (BlockPlantShortGrass) BlocksFlora.PLANT.get(plant);
//
//          if (world.isAirBlock(upPos) &&
//              plant.isValidLocation(temp, CapabilityProviderChunkData.getRainfall(world, upPos),
//                Math.subtractExact(world.getLightFor(EnumSkyBlock.SKY, upPos), world.getSkylightSubtracted())) &&
//              plant.isValidGrowthTemp(temp) &&
//              rand.nextDouble() < plantBlock.getGrowthRate(world, upPos)) {
//            world.setBlockState(upPos, plantBlock.getDefaultState());
//          }
//        }
//      }
    }
  }
}
