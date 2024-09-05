package su.terrafirmagreg.modules.world.classic.objects.generator.soil;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.CapabilityChunkData;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.rock.init.BlocksRock;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;
import su.terrafirmagreg.modules.world.classic.ChunkGenClassic;
import su.terrafirmagreg.modules.world.classic.WorldTypeClassic;
import su.terrafirmagreg.modules.world.classic.init.BiomesWorld;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;


import tfcflorae.ConfigTFCF;

import java.util.Random;

public class GeneratorSoilDecorative implements IWorldGenerator {

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
      if (ConfigTFCF.General.WORLD.enableSandGen) {
        BlockPos pos1 = world.getTopSolidOrLiquidBlock(
            chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateSand(world, random, pos1);
        BlockPos pos2 = world.getTopSolidOrLiquidBlock(
            chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateSand(world, random, pos2);
      }

      if (ConfigTFCF.General.WORLD.enableAllPodzol) {
        BlockPos pos1 = world.getTopSolidOrLiquidBlock(
            chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generatePodzol(world, random, pos1);
        BlockPos pos2 = world.getTopSolidOrLiquidBlock(
            chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generatePodzol(world, random, pos2);
        BlockPos pos3 = world.getTopSolidOrLiquidBlock(
            chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generatePodzol(world, random, pos3);
      }

      if (ConfigTFCF.General.WORLD.enableMudGen) {
        BlockPos pos = world.getTopSolidOrLiquidBlock(
            chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateMud(world, random, pos);
      }

      //            if (ConfigTFCF.General.WORLD.enableAllBogIron) {
      //                BlockPos pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
      //                generateBogIron(world, random, pos);
      //            }

      BlockPos pos = world.getTopSolidOrLiquidBlock(
          chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
      generatePeat(world, random, pos);
    }
  }

  private void generateSand(World world, Random rng, BlockPos start) {
    if (ConfigTFCF.General.WORLD.enableAllBlockTypes && ConfigTFCF.General.WORLD.enableSandGen) {
      final Biome b = world.getBiome(start);
      if (b == BiomesWorld.OCEAN || b == BiomesWorld.DEEP_OCEAN || b == BiomesWorld.BEACH
          || b == BiomesWorld.LAKE) {
        var data = CapabilityChunkData.get(world, start);
        if (data.isInitialized() && start.getY() <= WorldTypeClassic.SEALEVEL
            && data.getFloraDensity() >= 0.2f + (rng.nextGaussian() / 10) &&
            ProviderChunkData.getRainfall(world, start) >= RAINFALL_SAND + 15) {
          int length = rng.nextInt(4) + 3;
          int depth = rng.nextInt(3) + 1;
          float widthMultiplier = rng.nextInt(1) + 1f;
          int curveHeight = rng.nextInt(4) + 3;
          float curveFrequency = (rng.nextInt(1) + 1f) / 10f;

          int z;
          int tz;
          float tWidth = widthMultiplier / 4;

          int angle = rng.nextInt(360);

          int rx;
          int rz;

          for (int x = -length; x <= length; x++) {
            if (x < -length + 3) {
              tWidth *= 2;
            } else if (length - x < 3) {
              tWidth /= 2;
            }

            z = (int) (curveHeight + curveFrequency * x * MathHelper.sin(
                (-curveHeight + MathHelper.sin(x))) +
                MathHelper.sin((float) (x)));
            tz = (int) ((float) MathHelper.abs(z) * tWidth);

            for (int width = -tz; width <= tz; width++) {
              rx = (int) (x * MathHelper.cos(angle) - width * MathHelper.sin(angle));
              rz = (int) (x * MathHelper.sin(angle) + width * MathHelper.cos(angle));

              final BlockPos posHorizontal = start.add(rx, 0, rz);

              for (int y = -depth; y <= +depth; y++) {
                final BlockPos pos = posHorizontal.add(0, y, 0);
                final IBlockState current = world.getBlockState(pos);
                if (BlockUtils.isSoilOrGravel(current) || BlockUtils.isRawStone(current)) {
                  world.setBlockState(pos,
                      BlocksRock.SAND.get(ProviderChunkData.getRockHeight(world, pos))
                          .getDefaultState(), 2);
                }
              }
            }
          }
        }
      }
    }
  }

  private void generatePodzol(World world, Random rng, BlockPos start) {
    if (ConfigTFCF.General.WORLD.enableAllBlockTypes && ConfigTFCF.General.WORLD.enableAllPodzol) {
      if (rng.nextInt(ConfigTFCF.General.WORLD.podzolRarity) == 0 && start.getY() >= 146
          && start.getY() <= 175) {
        var data = CapabilityChunkData.get(world, start);
        if (data.isInitialized() && data.getRainfall() >= 90f && data.getFloraDensity() >= 0.5f &&
            ProviderChunkData.getDrainage(world, start) >= 2) {
          int length = rng.nextInt(4) + 3;
          int depth = rng.nextInt(3) + 1;
          float widthMultiplier = rng.nextInt(1) + 1f;
          int curveHeight = rng.nextInt(4) + 3;
          float curveFrequency = (rng.nextInt(1) + 1f) / 10f;

          int z;
          int tz;
          float tWidth = widthMultiplier / 4;

          int angle = rng.nextInt(360);

          int rx;
          int rz;

          for (int x = -length; x <= length; x++) {
            if (x < -length + 3) {
              tWidth *= 2;
            } else if (length - x < 3) {
              tWidth /= 2;
            }

            z = (int) (curveHeight + curveFrequency * x * MathHelper.sin(
                (-curveHeight + MathHelper.sin(x))) +
                MathHelper.sin((float) (x)));
            tz = (int) ((float) MathHelper.abs(z) * tWidth);

            for (int width = -tz; width <= tz; width++) {
              rx = (int) (x * MathHelper.cos(angle) - width * MathHelper.sin(angle));
              rz = (int) (x * MathHelper.sin(angle) + width * MathHelper.cos(angle));

              final BlockPos posHorizontal = start.add(rx, 0, rz);

              for (int y = -depth; y <= +depth; y++) {
                final BlockPos pos = posHorizontal.add(0, y, 0);
                final IBlockState current = world.getBlockState(pos);
                if (BlockUtils.isGrass(current)) {
                  world.setBlockState(pos,
                      BlocksSoil.PODZOL.get(ProviderChunkData.getSoilHeight(world, pos))
                          .getDefaultState(), 2);
                } else if (BlockUtils.isDryGrass(current)) {
                  world.setBlockState(pos,
                      BlocksSoil.PODZOL.get(ProviderChunkData.getSoilHeight(world, pos))
                          .getDefaultState(), 2);
                }
              }
            }
          }
        }
      }
    }
  }

  private void generateMud(World world, Random rng, BlockPos start) {
    if (rng.nextInt(ConfigTFCF.General.WORLD.mudRarity) == 0
        && start.getY() >= WorldTypeClassic.SEALEVEL && start.getY() <= 150 &&
        ProviderChunkData.getDrainage(world, start) <= 2) {
      final Biome b = world.getBiome(start);
      if (b == BiomesWorld.SWAMPLAND || b == BiomesWorld.BAYOU || b == BiomesWorld.MANGROVE
          || b == BiomesWorld.MARSH) {
        var data = CapabilityChunkData.get(world, start);
        if (data.isInitialized() && data.getRainfall() >= RAINFALL_SAND_SANDY_MIX) {
          int length = rng.nextInt(4) + 3;
          int depth = rng.nextInt(3) + 1;
          float widthMultiplier = rng.nextInt(1) + 1f;
          int curveHeight = rng.nextInt(4) + 3;
          float curveFrequency = (rng.nextInt(1) + 1f) / 10f;

          int z;
          int tz;
          float tWidth = widthMultiplier / 4;

          int angle = rng.nextInt(360);

          int rx;
          int rz;

          for (int x = -length; x <= length; x++) {
            if (x < -length + 3) {
              tWidth *= 2;
            } else if (length - x < 3) {
              tWidth /= 2;
            }

            //tx = x + shiftMultiplier;
            z = (int) (curveHeight + curveFrequency * x * MathHelper.sin(
                (-curveHeight + MathHelper.sin(x))) +
                MathHelper.sin((float) (x)));
            //z = (int) (curveSlope * MathHelper.sin(curveFrequency * x) + curveHeight);
            tz = (int) ((float) MathHelper.abs(z) * tWidth);

            for (int width = -tz; width <= tz; width++) {
              rx = (int) (x * MathHelper.cos(angle) - width * MathHelper.sin(angle));
              rz = (int) (x * MathHelper.sin(angle) + width * MathHelper.cos(angle));

              final BlockPos posHorizontal = start.add(rx, 0, rz);

              for (int y = -depth; y <= +depth; y++) {
                final BlockPos pos = posHorizontal.add(0, y, 0);
                final IBlockState current = world.getBlockState(pos);
                if (BlockUtils.isDirt(current)) {
                  world.setBlockState(pos,
                      BlocksSoil.MUD.get(ProviderChunkData.getSoilHeight(world, pos))
                          .getDefaultState(), 2);
                } else if (BlockUtils.isGrass(current)) {
                  world.setBlockState(pos,
                      BlocksSoil.MUD.get(ProviderChunkData.getSoilHeight(world, pos))
                          .getDefaultState(), 2);
                }
              }
            }
          }
        }
      }
    }
  }

  //    private void generateBogIron(World world, Random rng, BlockPos start) {
  //        if (ConfigTFCF.General.WORLD.enableAllBogIron) {
  //            var data = CapabilityChunkData.get(world, start);
  //            if (rng.nextInt(ConfigTFCF.General.WORLD.bogIronRarity) == 0 && start.getY() <= 150 && data.getAverageTemp() >= 0f &&
  //                    ProviderChunkData.getDrainage(world, start) <= 2) {
  //                final Biome b = world.getBiome(start);
  //                if (b == BiomesWorld.SWAMPLAND || b == BiomesWorld.BAYOU || b == BiomesWorld.MANGROVE || b == BiomesWorld.MARSH) {
  //                    int radius = rng.nextInt(5) + 2;
  //                    int depth = rng.nextInt(3) + 1;
  //
  //                    for (int x = -radius; x <= radius; x++) {
  //                        for (int z = -radius; z <= radius; z++) {
  //                            if (x * x + z * z > radius * radius) continue;
  //                            final BlockPos posHorizontal = start.add(x, 0, z);
  //
  //                            for (int y = -depth; y <= +depth; y++) {
  //                                final BlockPos pos = posHorizontal.add(0, y, 0);
  //                                final IBlockState current = world.getBlockState(pos);
  //
  //                                if (data.getRainfall() >= RAINFALL_SAND_SANDY_MIX) {
  //                                    if (BlockUtils.isDirt(current)) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.BOG_IRON)
  //                                                        .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.BOG_IRON_PODZOL)
  //                                                        .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.SPARSE_BOG_IRON_GRASS)
  //                                                        .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isDryGrass(current)) {
  //                                        world.setBlockState(pos,
  //                                                BlockRockVariantTFCF.get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.DRY_BOG_IRON_GRASS)
  //                                                        .getDefaultState(), 2);
  //                                    } else if (BlockUtils.isGrass(current)) {
  //                                        world.setBlockState(pos, BlockRockVariantTFCF
  //                                                        .get(ProviderChunkData.getRockHeight(world, pos), RockTFCF.SPARSE_BOG_IRON_GRASS).getDefaultState(),
  //                                                2);
  //                                    }
  //                                }
  //                                if (rng.nextInt(15) == 0) {
  //                                    final BlockPos posTop = world.getTopSolidOrLiquidBlock(posHorizontal);
  //
  //                                    for (Plant plant : TFCRegistries.PLANTS.getValuesCollection()) {
  //                                        if (plant.getIsClayMarking()) {
  //                                            BlockPlantTFC plantBlock = BlockPlantTFC.get(plant);
  //                                            IBlockState state = plantBlock.getDefaultState();
  //                                            int plantAge = plant.getAgeForWorldgen(rng, Climate.getActualTemp(world, posTop));
  //
  //                                            if (!world.provider.isNether() && !world.isOutsideBuildHeight(posTop) &&
  //                                                    plant.isValidLocation(Climate.getActualTemp(world, posTop),
  //                                                            ProviderChunkData.getRainfall(world, posTop),
  //                                                            world.getLightFor(EnumSkyBlock.SKY, posTop)) &&
  //                                                    world.isAirBlock(posTop) &&
  //                                                    plantBlock.canBlockStay(world, posTop, state)) {
  //                                                if (BlockUtils.isClay(current)) {
  //                                                    world.setBlockState(posTop, state.withProperty(BlockPlantTFC.AGE, plantAge), 2);
  //                                                }
  //                                            }
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

  private boolean generatePeat(World world, Random rng, BlockPos start) {
    // If this has to have a radius that is >= 8, then it needs to be moved to a cascading-lag safe model
    // Otherwise, do not change this unless you are prepared to do some fairly large re-writes, similar to how ore gen is handled
    int radius = rng.nextInt(4) + 4;
    byte depth = 2;

    if (rng.nextInt(30) != 0 || start.getY() > WorldTypeClassic.SEALEVEL) {
      return false;
    }
    var data = CapabilityChunkData.get(world, start);
    final Biome b = world.getBiome(start);
    if (b == BiomesWorld.SWAMPLAND || b == BiomesWorld.BAYOU || b == BiomesWorld.MANGROVE
        || b == BiomesWorld.MARSH) {
      if (data.isInitialized() && data.getRainfall() >= 375f && data.getFloraDiversity() >= 0.5f
          && data.getFloraDensity() >= 0.5f && world
          .getBiome(start)
          .getHeightVariation() < 0.15) {
        return false;
      }

      for (int x = -radius; x <= radius; ++x) {
        for (int z = -radius; z <= radius; ++z) {
          if (x * x + z * z > radius * radius) {
            continue;
          }

          for (int y = -depth; y <= depth; ++y) {
            final BlockPos pos = start.add(x, y, z);
            final IBlockState current = world.getBlockState(pos);

            if (BlockUtils.isGrass(current)) {
              world.setBlockState(pos, BlocksSoil.PEAT_GRASS.getDefaultState(), 2);
            } else if (BlockUtils.isDirt(current) || BlockUtils.isClay(current)) {
              world.setBlockState(pos, BlocksSoil.PEAT.getDefaultState(), 2);
            }
          }
        }
      }
    }
    return true;
  }
}
