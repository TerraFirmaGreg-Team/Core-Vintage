package su.terrafirmagreg.modules.soil.api.spi;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariants;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilPeat;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import java.util.Random;

public interface IGrass {

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
                world.setBlockState(pos, soil.getVariant().getNonGrassVersion().get(soil.getType()).getDefaultState());
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
                if (!BlockUtils.isSoil(current) || BlockUtils.isGrass(current)) {
                    continue;
                }

                // Получаем позицию верхнего блока целевой позиции
                BlockPos targetUp = target.up();
                // Получаем состояние верхнего блока целевой позиции
                IBlockState targetUpState = world.getBlockState(targetUp);

                // Пропускаем итерацию, если верхний блок жидкость или имеет высокую прозрачность
                if (world.getLightFromNeighbors(targetUp) < 4 || targetUpState.getMaterial()
                        .isLiquid() || targetUpState.getLightOpacity(world, targetUp) > 3) {
                    continue;
                }

                // Получаем тип текущего блока
                Block currentBlock = current.getBlock();

                // Генерируем траву в зависимости от типа текущего блока
                if (currentBlock instanceof BlockSoilPeat) {
                    world.setBlockState(target, BlocksSoil.PEAT_GRASS.getDefaultState());
                } else if (currentBlock instanceof ISoilBlock soilBlock) {
                    SoilBlockVariant spreader = SoilBlockVariants.GRASS;

                    // Проверяем тип блока, с которого распространяется трава
                    if (usBlock instanceof ISoilBlock) {
                        if (BlockUtils.isDryGrass(usBlock.getDefaultState())) {
                            spreader = SoilBlockVariants.DRY_GRASS;
                        } else if (BlockUtils.isSparseGrass(usBlock.getDefaultState())) {
                            spreader = SoilBlockVariants.SPARSE_GRASS;
                        } else {
                            spreader = SoilBlockVariants.GRASS;
                        }
                    }

                    var s = soilBlock.getVariant().getGrassVersion(spreader);
                    world.setBlockState(target, s.get(soilBlock.getType()).getDefaultState());
                }
            }
            // Генерируем короткую траву на верхнем блоке с определенной вероятностью
            //            for (PlantType plant : PlantType.getPlantTypes()) {
            //                if (plant.getPlantVariant() == PlantEnumVariant.SHORT_GRASS && rand.nextFloat() < 0.5f) {
            //                    float temp = ClimateTFC.getActualTemp(world, upPos);
            //                    var plantBlock = (BlockPlantShortGrass) StoragePlant.getPlantBlock(plant.getPlantVariant(), plant);
            //
            //                    // Проверяем условия для генерации короткой травы
            //                    if (world.isAirBlock(upPos) &&
            //                            plant.isValidLocation(temp, ChunkDataTFC.getRainfall(world, upPos), Math.subtractExact(world.getLightFor(EnumSkyBlock.SKY, upPos), world.getSkylightSubtracted())) &&
            //                            plant.isValidGrowthTemp(temp) &&
            //                            rand.nextDouble() < plantBlock.getGrowthRate(world, upPos)) {
            //                        world.setBlockState(upPos, plantBlock.getDefaultState());
            //                    }
            //                }
            //            }
        }
    }
}
