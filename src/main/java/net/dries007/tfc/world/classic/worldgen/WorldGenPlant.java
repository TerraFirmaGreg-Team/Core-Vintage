package net.dries007.tfc.world.classic.worldgen;

import net.dries007.tfc.module.core.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.module.plant.StoragePlant;
import net.dries007.tfc.module.plant.api.type.PlantType;
import net.dries007.tfc.module.plant.common.blocks.*;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
public class WorldGenPlant extends WorldGenerator {
    private PlantType plant;

    public void setGeneratedPlant(PlantType plantIn) {
        this.plant = plantIn;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position) {
        if (plant.isClayMarking()) return false;
        if (plant.isSwampPlant() && (/*!ClimateTFC.isSwamp(worldIn, position) ||*/ !BiomeDictionary.hasType(worldIn.getBiome(position), BiomeDictionary.Type.SWAMP)))
            return false;

        switch (plant.getPlantVariant()) {
            case MUSHROOM -> {
                var plantBlock = (BlockPlantMushroom) StoragePlant.getPlantBlock(plant.getPlantVariant(), plant);
                IBlockState state = plantBlock.getDefaultState();

                for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, position) / 16; ++i) {
                    BlockPos blockpos = position.add(rand.nextInt(4) - rand.nextInt(4), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(4) - rand.nextInt(4));

                    if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                            plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
                            worldIn.isAirBlock(blockpos) &&
                            plantBlock.canPlaceBlockAt(worldIn, blockpos)) {
                        int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                        setBlockAndNotifyAdequately(worldIn, blockpos, state.withProperty(BlockPlant.AGE, plantAge));
                    }
                }
            }
            case SHORT_GRASS -> {
                var plantBlock = (BlockPlantShortGrass) StoragePlant.getPlantBlock(plant.getPlantVariant(), plant);
                IBlockState state = plantBlock.getDefaultState();

                for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, position) / 4; ++i) {
                    BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(7) - rand.nextInt(7));

                    if (plant.isValidGrowthTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                            plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
                            worldIn.isAirBlock(blockpos) &&
                            plantBlock.canBlockStay(worldIn, blockpos, state)) {
                        int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                        setBlockAndNotifyAdequately(worldIn, blockpos, state.withProperty(BlockPlantShortGrass.AGE, plantAge));
                    }
                }
            }
            case TALL_GRASS -> {
                var plantBlock = (BlockPlantTallGrass) StoragePlant.getPlantBlock(plant.getPlantVariant(), plant);
                IBlockState state = plantBlock.getDefaultState();

                for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, position) / 16; ++i) {
                    BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(7) - rand.nextInt(7));

                    int j = 1 + rand.nextInt(plant.getMaxHeight());

                    for (int k = 0; k < j; ++k) {
                        if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                                plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.up(k))) &&
                                worldIn.isAirBlock(blockpos.up(k)) &&
                                plantBlock.canBlockStay(worldIn, blockpos.up(k), state)) {
                            int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                            setBlockAndNotifyAdequately(worldIn, blockpos.up(k), state.withProperty(BlockPlantShortGrass.AGE, plantAge));
                        }
                    }
                }
            }
            case CREEPING -> {
                var plantBlock = (BlockPlantCreeping) StoragePlant.getPlantBlock(plant.getPlantVariant(), plant);
                IBlockState state = plantBlock.getDefaultState();

                for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, position) / 16; ++i) {
                    BlockPos blockpos = position.add(rand.nextInt(4) - rand.nextInt(4), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(4) - rand.nextInt(4));

                    if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                            plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
                            worldIn.isAirBlock(blockpos) &&
                            plantBlock.canBlockStay(worldIn, blockpos, state) &&
                            !TFCBlocks.isSand(worldIn.getBlockState(blockpos.down()))) {
                        int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                        setBlockAndNotifyAdequately(worldIn, blockpos, state.withProperty(BlockPlantCreeping.AGE, plantAge));
                    }
                }
            }
            case HANGING -> {
                var plantBlock = (BlockPlantHanging) StoragePlant.getPlantBlock(plant.getPlantVariant(), plant);
                IBlockState state = plantBlock.getDefaultState();

                for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, position) / 4; ++i) {
                    BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(16), rand.nextInt(7) - rand.nextInt(7));

                    if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                            plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
                            worldIn.isAirBlock(blockpos) &&
                            plantBlock.canBlockStay(worldIn, blockpos, state)) {
                        int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                        setBlockAndNotifyAdequately(worldIn, blockpos, state.withProperty(BlockPlantHanging.AGE, plantAge));
                    }
                }
            }
            case REED, REED_SEA -> {
                var plantBlock = (BlockPlant) StoragePlant.getPlantBlock(plant.getPlantVariant(), plant);
                IBlockState state = plantBlock.getDefaultState();

                for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, position) / 16; ++i) {
                    BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(7) - rand.nextInt(7));

                    if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                            plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
                            worldIn.isAirBlock(blockpos) &&
                            worldIn.getBlockState(blockpos.down()).getBlock().canSustainPlant(state, worldIn, blockpos.down(), EnumFacing.UP, plantBlock)) {
                        int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                        setBlockAndNotifyAdequately(worldIn, blockpos, state.withProperty(BlockPlant.AGE, plantAge));
                    }
                }
            }
            case TALL_REED, TALL_REED_SEA, TALL_PLANT -> {
                var plantBlock = (BlockPlantTall) StoragePlant.getPlantBlock(plant.getPlantVariant(), plant);
                IBlockState state = plantBlock.getDefaultState();

                for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, position) / 16; ++i) {
                    BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(7) - rand.nextInt(7));

                    int j = 1 + rand.nextInt(plant.getMaxHeight());

                    for (int k = 0; k < j; ++k) {
                        if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                                plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.up(k))) &&
                                worldIn.isAirBlock(blockpos.up(k)) &&
                                plantBlock.canBlockStay(worldIn, blockpos.up(k), state)) {
                            int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                            setBlockAndNotifyAdequately(worldIn, blockpos.up(k), state.withProperty(BlockPlantTall.AGE, plantAge));
                        }
                    }
                }
            }
            case DESERT, DRY -> {
                var plantBlock = (BlockPlant) StoragePlant.getPlantBlock(plant.getPlantVariant(), plant);
                IBlockState state = plantBlock.getDefaultState();

                for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, position); ++i) {
                    BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(7) - rand.nextInt(7));

                    if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                            plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
                            worldIn.isAirBlock(blockpos) &&
                            !BiomeDictionary.hasType(worldIn.getBiome(blockpos), BiomeDictionary.Type.BEACH) &&
                            plantBlock.canBlockStay(worldIn, blockpos, state)) {
                        int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                        setBlockAndNotifyAdequately(worldIn, blockpos, state.withProperty(BlockPlant.AGE, plantAge));
                    }
                }
            }
            case DESERT_TALL_PLANT, DRY_TALL_PLANT -> {
                var plantBlock = (BlockPlantTall) StoragePlant.getPlantBlock(plant.getPlantVariant(), plant);
                IBlockState state = plantBlock.getDefaultState();

                for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, position); ++i) {
                    BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(7) - rand.nextInt(7));

                    int j = 1 + rand.nextInt(plant.getMaxHeight());

                    for (int k = 0; k < j; ++k) {
                        if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                                plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.up(k))) &&
                                worldIn.isAirBlock(blockpos.up(k)) &&
                                plantBlock.canBlockStay(worldIn, blockpos.up(k), state)) {
                            int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                            setBlockAndNotifyAdequately(worldIn, blockpos.up(k), state.withProperty(BlockPlantTall.AGE, plantAge));
                        }
                    }
                }
            }
            case WATER, WATER_SEA -> {
                var plantBlock = (BlockPlantWater) StoragePlant.getPlantBlock(plant.getPlantVariant(), plant);
                IBlockState state = plantBlock.getDefaultState();
                IBlockState water = plant.getWaterType();

                int depth = plant.getValidWaterDepth(worldIn, position, water);
                if (depth == -1) return false;

                BlockPos blockpos = position.add(0, -depth + 1, 0);

                if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                        plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
                        plantBlock.canPlaceBlockAt(worldIn, blockpos)) {
                    int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                    setBlockAndNotifyAdequately(worldIn, blockpos, state.withProperty(BlockPlantWater.AGE, plantAge));
                }
            }
            case EMERGENT_TALL_WATER, EMERGENT_TALL_WATER_SEA -> {
                var plantBlock = (BlockPlantEmergentTallWater) StoragePlant.getPlantBlock(plant.getPlantVariant(), plant);
                IBlockState state = plantBlock.getDefaultState();
                IBlockState water = plant.getWaterType();

                int depth = plant.getValidWaterDepth(worldIn, position, water);
                if (depth == -1) return false;
                BlockPos blockpos = position.add(0, -depth + 1, 0);

                if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                        plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
                        plantBlock.canPlaceBlockAt(worldIn, blockpos)) {
                    int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                    setBlockAndNotifyAdequately(worldIn, blockpos, state.withProperty(BlockPlantEmergentTallWater.AGE, plantAge));
                    if (rand.nextInt(3) < plantAge && plantBlock.canGrow(worldIn, blockpos, state, worldIn.isRemote))
                        setBlockAndNotifyAdequately(worldIn, blockpos.up(), state);
                }
            }
            case TALL_WATER, TALL_WATER_SEA -> {
                var plantBlock = (BlockPlantTallWater) StoragePlant.getPlantBlock(plant.getPlantVariant(), plant);
                IBlockState state = plantBlock.getDefaultState();
                IBlockState water = plant.getWaterType();

                int depth = plant.getValidWaterDepth(worldIn, position, water);
                if (depth == -1) return false;
                BlockPos blockpos = position.add(0, -depth + 1, 0);

                if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                        plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
                        plantBlock.canPlaceBlockAt(worldIn, blockpos)) {
                    int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                    setBlockAndNotifyAdequately(worldIn, blockpos, state.withProperty(BlockPlantTall.AGE, plantAge));
                    if (rand.nextInt(4) < plantAge && plantBlock.canGrow(worldIn, blockpos, state, worldIn.isRemote))
                        setBlockAndNotifyAdequately(worldIn, blockpos.up(), state);
                }
            }
            case FLOATING -> {
                var plantBlock = (BlockPlantFloatingWater) StoragePlant.getPlantBlock(plant.getPlantVariant(), plant);
                IBlockState state = plantBlock.getDefaultState();
                IBlockState water = plant.getWaterType();

                for (int i = 0; i < 8; ++i) {
                    final BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), 0, rand.nextInt(7) - rand.nextInt(7));

                    if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                            plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
                            worldIn.isAirBlock(blockpos) &&
                            plantBlock.canPlaceBlockAt(worldIn, blockpos) &&
                            plant.isValidFloatingWaterDepth(worldIn, blockpos, water)) {
                        int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                        setBlockAndNotifyAdequately(worldIn, blockpos, state.withProperty(BlockPlantFloatingWater.AGE, plantAge));
                    }
                }
            }
            case FLOATING_SEA -> {
                var plantBlock = (BlockPlantFloatingWater) StoragePlant.getPlantBlock(plant.getPlantVariant(), plant);
                IBlockState state = plantBlock.getDefaultState();
                IBlockState water = plant.getWaterType();

                for (int i = 0; i < 128; ++i) {
                    final BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), 0, rand.nextInt(7) - rand.nextInt(7));

                    if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                            plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
                            worldIn.isAirBlock(blockpos) &&
                            plantBlock.canPlaceBlockAt(worldIn, blockpos) &&
                            plant.isValidFloatingWaterDepth(worldIn, blockpos, water)) {
                        int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                        setBlockAndNotifyAdequately(worldIn, blockpos, state.withProperty(BlockPlantFloatingWater.AGE, plantAge));
                    }
                }
            }
            case CACTUS -> {
                var plantBlock = (BlockPlantCactus) StoragePlant.getPlantBlock(plant.getPlantVariant(), plant);
                var state = plantBlock.getDefaultState();

                for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, position) / 8; ++i) {
                    BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(7) - rand.nextInt(7));

                    int j = 1 + rand.nextInt(plant.getMaxHeight());

                    for (int k = 0; k < j; ++k) {
                        if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                                plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.up(k))) &&
                                worldIn.isAirBlock(blockpos.up(k)) &&
                                plantBlock.canBlockStay(worldIn, blockpos.up(k), state)) {
                            int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                            setBlockAndNotifyAdequately(worldIn, blockpos.up(k), state.withProperty(BlockPlantCactus.AGE, plantAge));
                        }
                    }
                }
            }
            case EPIPHYTE -> {
                var plantBlock = (BlockPlantEpiphyte) StoragePlant.getPlantBlock(plant.getPlantVariant(), plant);

                for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, position) / 4; ++i) {
                    BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(16), rand.nextInt(7) - rand.nextInt(7));

                    if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                            plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
                            worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos) &&
                            plantBlock.canPlaceBlockAt(worldIn, blockpos)) {
                        int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                        setBlockAndNotifyAdequately(worldIn, blockpos, plantBlock.getStateForWorldGen(worldIn, blockpos).withProperty(BlockPlantEpiphyte.AGE, plantAge));
                    }
                }
            }
            default -> {
                var plantBlock = (BlockPlant) StoragePlant.getPlantBlock(plant.getPlantVariant(), plant);
                IBlockState state = plantBlock.getDefaultState();

                for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, position) / 16; ++i) {
                    BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(7) - rand.nextInt(7));

                    if (plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                            plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
                            worldIn.isAirBlock(blockpos) &&
                            plantBlock.canBlockStay(worldIn, blockpos, state)) {
                        int plantAge = plant.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
                        setBlockAndNotifyAdequately(worldIn, blockpos, state.withProperty(BlockPlant.AGE, plantAge));
                    }
                }
            }
        }
        return true;
    }
}
