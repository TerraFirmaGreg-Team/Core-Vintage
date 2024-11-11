package su.terrafirmagreg.modules.world.classic.objects.generator.structures;

import su.terrafirmagreg.api.helper.BlockHelper;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.CapabilityChunkData;
import su.terrafirmagreg.modules.world.classic.ChunkGenClassic;
import su.terrafirmagreg.modules.world.classic.WorldTypeClassic;
import su.terrafirmagreg.modules.world.classic.init.BiomesWorld;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import net.dries007.tfc.objects.blocks.groundcover.BlockCoral;
import tfcflorae.ConfigTFCF;

import java.util.Random;

public class WorldGenStructuresCorals implements IWorldGenerator {

  @Override
  public void generate(Random random, int chunkX, int chunkZ, World world,
                       IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    if (chunkGenerator instanceof ChunkGenClassic && world.provider.getDimension() == 0) {
      int coralsInChunk = 3 + random.nextInt(15);
      for (int i = 0; i < coralsInChunk; i++) {
        final int x = (chunkX << 4) + random.nextInt(16) + 8;
        final int z = (chunkZ << 4) + random.nextInt(16) + 8;
        final BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));
        final Biome b = world.getBiome(pos);
        var data = CapabilityChunkData.get(world, pos);
        IBlockState down = world.getBlockState(pos.down());
        IBlockState up = world.getBlockState(pos.up());

        if (ConfigTFCF.General.STRUCTURES.activateStructureGeneration) {
          if (data.isInitialized() &&
              (b == BiomesWorld.OCEAN || b == BiomesWorld.DEEP_OCEAN || b == BiomesWorld.BEACH
               || b == BiomesWorld.GRAVEL_BEACH)) {
            if ((up.getBlock() instanceof BlockCoral || world.getBlockState(pos)
                                                             .getBlock() instanceof BlockCoral || BlockHelper.isGround(down)
                 || world.getBlockState(pos)
                         .getBlock() == ChunkGenClassic.SALT_WATER.getBlock()) &&
                (pos.getY() < WorldTypeClassic.SEALEVEL - 7 && pos.getY() > 119
                 && data.getFloraDiversity() >= 0.3f &&
                 data.getFloraDensity() >= 0.4f && data.getFloraDensity() <= 0.6f
                 && data.getFloraDiversity() <= 0.5f &&
                 data.getAverageTemp() >= 10f && data.getAverageTemp() <= 28f
                 && data.getRainfall() >= 150f)) {
              int chance = random.nextInt(5);

              int randomCoral = random.nextInt(180) + 1;

              if (chance == 0) {
                StructureGeneratorCorals gen = new StructureGeneratorCorals(
                  "coral/brain/" + randomCoral);
                generateStructure(gen, world, random, pos);
              } else if (chance == 1) {
                StructureGeneratorCorals gen = new StructureGeneratorCorals(
                  "coral/bubble/" + randomCoral);
                generateStructure(gen, world, random, pos);
              } else if (chance == 2) {
                StructureGeneratorCorals gen = new StructureGeneratorCorals(
                  "coral/fire/" + randomCoral);
                generateStructure(gen, world, random, pos);
              } else if (chance == 3) {
                StructureGeneratorCorals gen = new StructureGeneratorCorals(
                  "coral/horn/" + randomCoral);
                generateStructure(gen, world, random, pos);
              } else if (chance == 4) {
                StructureGeneratorCorals gen = new StructureGeneratorCorals(
                  "coral/tube/" + randomCoral);
                generateStructure(gen, world, random, pos);
              }
            }
          }
        }
      }
    }
  }

  private void generateStructure(WorldGenerator generator, World world, Random random,
                                 BlockPos pos) {
    generator.generate(world, random, pos);
  }
}
