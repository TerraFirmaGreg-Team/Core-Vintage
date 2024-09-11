package su.terrafirmagreg.modules.world.classic.objects.generator.soil;

import su.terrafirmagreg.modules.world.classic.ChunkGenClassic;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;


import tfcflorae.ConfigTFCF;

import java.util.Random;

public class GeneratorClays implements IWorldGenerator {

  public static final float RAINFALL_SAND = 75;
  public static final float RAINFALL_SAND_SANDY_MIX = 125;
  public static final float RAINFALL_SANDY = 200; // Upper thresholds
  public static final float RAINFALL_SILTY = 275; // Lower thresholds
  public static final float RAINFALL_SILT_SILTY_MIX = 350;
  public static final float RAINFALL_SILT = 400;

  @Override
  public void generate(Random random, int chunkX, int chunkZ, World world,
          IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    if (!(chunkGenerator instanceof ChunkGenClassic)) {
      return;
    }
    final BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);

    if (ConfigTFCF.General.WORLD.enableAllBlockTypes) {
      //            if (ConfigTFCF.General.WORLD.enableAllSpecialSoil) {
      //                BlockPos pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
      //                generateClaySurface(world, random, pos);
      //            }
    }
  }

  //    private void generateClaySurface(World world, Random rng, BlockPos start) {
  //        if (ConfigTFCF.General.WORLD.enableAllSpecialSoil && ProviderChunkData.getDrainage(world, start) <= 1) {
  //            var data = CapabilityChunkData.get(world, start);
  //
  //            int radius = rng.nextInt(6) + 2;
  //            int depth = rng.nextInt(3) + 1;
  //
  //            int sandyClayRarity = rng.nextInt(ConfigTFCF.General.WORLD.sandyClayRarity);
  //            int sandyClayLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.sandyClayLoamRarity);
  //            int clayLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.clayLoamRarity);
  //            int siltyClayLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.siltyClayLoamRarity);
  //            int clayHumusRarity = rng.nextInt(ConfigTFCF.General.WORLD.clayHumusRarity);
  //            int siltyClayRarity = rng.nextInt(ConfigTFCF.General.WORLD.siltyClayRarity);
  //
  //            for (int x = -radius; x <= radius; x++) {
  //                for (int z = -radius; z <= radius; z++) {
  //                    if (x * x + z * z > radius * radius) continue;
  //                    final BlockPos posHorizontal = start.add(x, 0, z);
  //
  //                    for (int y = -depth; y <= +depth; y++) {
  //                        final BlockPos pos = posHorizontal.add(0, y, 0);
  //                        final IBlockState current = world.getBlockState(pos);
  //
  //                        if (data.getRainfall() < RAINFALL_SANDY) {
  //                            if (data.getRainfall() > RAINFALL_SAND_SANDY_MIX) {
  //                                if (sandyClayRarity == 0) {
  //                                    if (BlockUtils.isDirt(current)) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.SANDY_CLAY)
  //                                                        .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.SANDY_CLAY_PODZOL)
  //                                                        .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos),
  //                                                                RockTFCF.SPARSE_SANDY_CLAY_GRASS)
  //                                                        .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isDryGrass(current)) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.DRY_SANDY_CLAY_GRASS)
  //                                                        .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isGrass(current)) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.SANDY_CLAY_GRASS)
  //                                                        .getDefaultState(), 2);
  //                                    }
  //                                }
  //                            } else if (data.getRainfall() > RAINFALL_SAND) {
  //                                if (sandyClayLoamRarity == 0) {
  //                                    if (BlockUtils.isDirt(current)) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.SANDY_CLAY_LOAM)
  //                                                        .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.SANDY_CLAY_LOAM_PODZOL)
  //                                                        .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass) {
  //                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos),
  //                                                        RockTFCF.SPARSE_SANDY_CLAY_LOAM_GRASS)
  //                                                .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isDryGrass(current)) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos),
  //                                                                RockTFCF.DRY_SANDY_CLAY_LOAM_GRASS)
  //                                                        .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isGrass(current)) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.SANDY_CLAY_LOAM_GRASS)
  //                                                        .getDefaultState(), 2);
  //                                    }
  //                                }
  //                            }
  //                        } else if (data.getRainfall() > RAINFALL_SANDY) {
  //                            if (data.getRainfall() < RAINFALL_SILTY) {
  //                                if (clayLoamRarity == 0) {
  //                                    if (BlockUtils.isDirt(current)) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.CLAY_LOAM)
  //                                                        .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.CLAY_LOAM_PODZOL)
  //                                                        .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.SPARSE_CLAY_LOAM_GRASS)
  //                                                        .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isDryGrass(current)) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.DRY_CLAY_LOAM_GRASS)
  //                                                        .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isGrass(current)) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.CLAY_LOAM_GRASS)
  //                                                        .getDefaultState(), 2);
  //                                    }
  //                                }
  //                            }
  //                        } else if (data.getRainfall() > RAINFALL_SILTY) {
  //                            if (data.getRainfall() < RAINFALL_SILT_SILTY_MIX) {
  //                                if (siltyClayLoamRarity == 0) {
  //                                    if (BlockUtils.isDirt(current)) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.SILTY_CLAY_LOAM)
  //                                                        .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.SILTY_CLAY_LOAM_PODZOL)
  //                                                        .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass) {
  //                                        world.setBlockState(pos, BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos),
  //                                                        RockTFCF.SPARSE_SILTY_CLAY_LOAM_GRASS)
  //                                                .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isDryGrass(current)) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos),
  //                                                                RockTFCF.DRY_SILTY_CLAY_LOAM_GRASS)
  //                                                        .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isGrass(current)) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.SILTY_CLAY_LOAM_GRASS)
  //                                                        .getDefaultState(), 2);
  //                                    }
  //                                }
  //                            } else if (data.getRainfall() < RAINFALL_SILT) {
  //                                if (clayHumusRarity == 0) {
  //                                    if (BlockUtils.isDirt(current)) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.CLAY_HUMUS)
  //                                                        .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos),
  //                                                                RockTFCF.SPARSE_CLAY_HUMUS_GRASS)
  //                                                        .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isDryGrass(current)) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.DRY_CLAY_HUMUS_GRASS)
  //                                                        .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isGrass(current)) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.CLAY_HUMUS_GRASS)
  //                                                        .getDefaultState(), 2);
  //                                    }
  //                                }
  //                            } else {
  //                                if (siltyClayRarity == 0) {
  //                                    if (BlockUtils.isDirt(current)) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.SILTY_CLAY)
  //                                                        .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.SILTY_CLAY_PODZOL)
  //                                                        .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos),
  //                                                                RockTFCF.SPARSE_SILTY_CLAY_GRASS)
  //                                                        .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isDryGrass(current)) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.DRY_SILTY_CLAY_GRASS)
  //                                                        .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isGrass(current)) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.SILTY_CLAY_GRASS)
  //                                                        .getDefaultState(), 2);
  //                                    }
  //                                }
  //                            }
  //                        }
  //                        if (rng.nextInt(10) == 0) {
  //                            final BlockPos posTop = world.getTopSolidOrLiquidBlock(posHorizontal);
  //
  //                            for (Plant plant : TFCRegistries.PLANTS.getValuesCollection()) {
  //                                if (plant.getIsClayMarking()) {
  //                                    BlockPlantTFC plantBlock = BlockPlantTFC.get(plant);
  //                                    IBlockState state = plantBlock.getDefaultState();
  //                                    int plantAge = plant.getAgeForWorldgen(rng, Climate.getActualTemp(world, posTop));
  //
  //                                    if (!world.provider.isNether() && !world.isOutsideBuildHeight(posTop) &&
  //                                            plant.isValidLocation(Climate.getActualTemp(world, posTop), ProviderChunkData.getRainfall(world, posTop),
  //                                                    world.getLightFor(EnumSkyBlock.SKY, posTop)) &&
  //                                            world.isAirBlock(posTop) &&
  //                                            plantBlock.canBlockStay(world, posTop, state)) {
  //                                        if (BlockUtils.isClay(current)) {
  //                                            world.setBlockState(posTop, state.withProperty(BlockPlantTFC.AGE, plantAge), 2);
  //                                        }
  //                                    }
  //                                }
  //                            }
  //                        }
  //                    }
  //                }
  //            }
  //        }
  //    }
}
