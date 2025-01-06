package tfcflorae.world.worldgen.soil;

import su.terrafirmagreg.modules.core.feature.climate.Climate;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import tfcflorae.ConfigTFCF;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.blocktype.BlockRockVariantTFCF;
import tfcflorae.types.BlockTypesTFCF.RockTFCF;

import java.util.Random;

public class WorldGenClays implements IWorldGenerator {

  public static final float RAINFALL_SAND = 75;
  public static final float RAINFALL_SAND_SANDY_MIX = 125;
  public static final float RAINFALL_SANDY = 200; // Upper thresholds
  public static final float RAINFALL_SILTY = 275; // Lower thresholds
  public static final float RAINFALL_SILT_SILTY_MIX = 350;
  public static final float RAINFALL_SILT = 400;

  @Override
  public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    if (!(chunkGenerator instanceof ChunkGenTFC)) {return;}
    final BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);

    if (ConfigTFCF.General.WORLD.enableAllBlockTypes) {
      if (ConfigTFCF.General.WORLD.enableAllSpecialSoil) {
        BlockPos pos = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + random.nextInt(16), 0, 8 + random.nextInt(16)));
        generateClaySurface(world, random, pos);
      }
    }
  }

  private void generateClaySurface(World world, Random rng, BlockPos start) {
    if (ConfigTFCF.General.WORLD.enableAllSpecialSoil && ChunkDataTFC.getDrainage(world, start) <= 1) {
      ChunkDataTFC data = ChunkDataTFC.get(world, start);

      int radius = rng.nextInt(6) + 2;
      int depth = rng.nextInt(3) + 1;

      int sandyClayRarity = rng.nextInt(ConfigTFCF.General.WORLD.sandyClayRarity);
      int sandyClayLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.sandyClayLoamRarity);
      int clayLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.clayLoamRarity);
      int siltyClayLoamRarity = rng.nextInt(ConfigTFCF.General.WORLD.siltyClayLoamRarity);
      int clayHumusRarity = rng.nextInt(ConfigTFCF.General.WORLD.clayHumusRarity);
      int siltyClayRarity = rng.nextInt(ConfigTFCF.General.WORLD.siltyClayRarity);

      for (int x = -radius; x <= radius; x++) {
        for (int z = -radius; z <= radius; z++) {
          if (x * x + z * z > radius * radius) {continue;}
          final BlockPos posHorizontal = start.add(x, 0, z);

          for (int y = -depth; y <= +depth; y++) {
            final BlockPos pos = posHorizontal.add(0, y, 0);
            final IBlockState current = world.getBlockState(pos);

            if (data.getRainfall() < RAINFALL_SANDY) {
              if (data.getRainfall() > RAINFALL_SAND_SANDY_MIX) {
                if (sandyClayRarity == 0) {
                  if (BlocksTFC.isDirt(current)) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_CLAY).getDefaultState(), 2);
                  } else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_CLAY_PODZOL).getDefaultState(), 2);
                  } else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_SANDY_CLAY_GRASS)
                      .getDefaultState(), 2);
                  } else if (BlocksTFC.isDryGrass(current)) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_CLAY_GRASS)
                      .getDefaultState(), 2);
                  } else if (BlocksTFC.isGrass(current)) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_CLAY_GRASS).getDefaultState(), 2);
                  }
                }
              } else if (data.getRainfall() > RAINFALL_SAND) {
                if (sandyClayLoamRarity == 0) {
                  if (BlocksTFC.isDirt(current)) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_CLAY_LOAM).getDefaultState(), 2);
                  } else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_CLAY_LOAM_PODZOL)
                      .getDefaultState(), 2);
                  } else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_SANDY_CLAY_LOAM_GRASS)
                      .getDefaultState(), 2);
                  } else if (BlocksTFC.isDryGrass(current)) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SANDY_CLAY_LOAM_GRASS)
                      .getDefaultState(), 2);
                  } else if (BlocksTFC.isGrass(current)) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SANDY_CLAY_LOAM_GRASS)
                      .getDefaultState(), 2);
                  }
                }
              }
            } else if (data.getRainfall() > RAINFALL_SANDY) {
              if (data.getRainfall() < RAINFALL_SILTY) {
                if (clayLoamRarity == 0) {
                  if (BlocksTFC.isDirt(current)) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.CLAY_LOAM).getDefaultState(), 2);
                  } else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.CLAY_LOAM_PODZOL).getDefaultState(), 2);
                  } else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_CLAY_LOAM_GRASS)
                      .getDefaultState(), 2);
                  } else if (BlocksTFC.isDryGrass(current)) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_CLAY_LOAM_GRASS)
                      .getDefaultState(), 2);
                  } else if (BlocksTFC.isGrass(current)) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.CLAY_LOAM_GRASS).getDefaultState(), 2);
                  }
                }
              }
            } else if (data.getRainfall() > RAINFALL_SILTY) {
              if (data.getRainfall() < RAINFALL_SILT_SILTY_MIX) {
                if (siltyClayLoamRarity == 0) {
                  if (BlocksTFC.isDirt(current)) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_CLAY_LOAM).getDefaultState(), 2);
                  } else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_CLAY_LOAM_PODZOL)
                      .getDefaultState(), 2);
                  } else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_SILTY_CLAY_LOAM_GRASS)
                      .getDefaultState(), 2);
                  } else if (BlocksTFC.isDryGrass(current)) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILTY_CLAY_LOAM_GRASS)
                      .getDefaultState(), 2);
                  } else if (BlocksTFC.isGrass(current)) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_CLAY_LOAM_GRASS)
                      .getDefaultState(), 2);
                  }
                }
              } else if (data.getRainfall() < RAINFALL_SILT) {
                if (clayHumusRarity == 0) {
                  if (BlocksTFC.isDirt(current)) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.CLAY_HUMUS).getDefaultState(), 2);
                  } else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_CLAY_HUMUS_GRASS)
                      .getDefaultState(), 2);
                  } else if (BlocksTFC.isDryGrass(current)) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_CLAY_HUMUS_GRASS)
                      .getDefaultState(), 2);
                  } else if (BlocksTFC.isGrass(current)) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.CLAY_HUMUS_GRASS).getDefaultState(), 2);
                  }
                }
              } else {
                if (siltyClayRarity == 0) {
                  if (BlocksTFC.isDirt(current)) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_CLAY).getDefaultState(), 2);
                  } else if (BlocksTFCF.isPodzol(current) && ConfigTFCF.General.WORLD.enableAllPodzol) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_CLAY_PODZOL).getDefaultState(), 2);
                  } else if (BlocksTFCF.isSparseGrass(current) && ConfigTFCF.General.WORLD.enableAllSparseGrass) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SPARSE_SILTY_CLAY_GRASS)
                      .getDefaultState(), 2);
                  } else if (BlocksTFC.isDryGrass(current)) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.DRY_SILTY_CLAY_GRASS)
                      .getDefaultState(), 2);
                  } else if (BlocksTFC.isGrass(current)) {
                    world.setBlockState(pos, BlockRockVariantTFCF.get(ChunkDataTFC.getRockHeight(world, pos), RockTFCF.SILTY_CLAY_GRASS).getDefaultState(), 2);
                  }
                }
              }
            }
            if (rng.nextInt(10) == 0) {
              final BlockPos posTop = world.getTopSolidOrLiquidBlock(posHorizontal);

              for (Plant plant : TFCRegistries.PLANTS.getValuesCollection()) {
                if (plant.getIsClayMarking()) {
                  BlockPlantTFC plantBlock = BlockPlantTFC.get(plant);
                  IBlockState state = plantBlock.getDefaultState();
                  int plantAge = plant.getAgeForWorldgen(rng, Climate.getActualTemp(world, posTop));

                  if (!world.provider.isNether() && !world.isOutsideBuildHeight(posTop) &&
                      plant.isValidLocation(Climate.getActualTemp(world, posTop), ChunkDataTFC.getRainfall(world, posTop), world.getLightFor(EnumSkyBlock.SKY, posTop))
                      &&
                      world.isAirBlock(posTop) &&
                      plantBlock.canBlockStay(world, posTop, state)) {
                    if (BlocksTFC.isClay(current) || BlocksTFCF.isClay(current)) {
                      world.setBlockState(posTop, state.withProperty(BlockPlantTFC.AGE, plantAge), 2);
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
