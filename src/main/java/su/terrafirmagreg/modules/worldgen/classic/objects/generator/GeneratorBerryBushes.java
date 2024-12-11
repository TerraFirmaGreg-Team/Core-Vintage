package su.terrafirmagreg.modules.worldgen.classic.objects.generator;

import su.terrafirmagreg.modules.agriculture.ConfigAgriculture;
import su.terrafirmagreg.modules.agriculture.api.types.berrybush.BerryBushType;
import su.terrafirmagreg.modules.agriculture.init.BlocksAgriculture;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.core.feature.climate.Climate;
import su.terrafirmagreg.modules.worldgen.classic.ChunkGenClassic;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneratorBerryBushes implements IWorldGenerator {

  private static final List<BerryBushType> BUSHES = new ArrayList<>() {{
    addAll(BerryBushType.getTypes());
  }};

  @Override
  public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    if (chunkGenerator instanceof ChunkGenClassic && world.provider.getDimension() == 0 && !BUSHES.isEmpty()
        && ConfigAgriculture.BLOCKS.BERRY_BUSH.rarity > 0) {
      if (random.nextInt(ConfigAgriculture.BLOCKS.BERRY_BUSH.rarity) == 0) {
        // Guarantees bush generation if possible (easier to balance by config file while also making it random)
        Collections.shuffle(BUSHES);
        BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);

        float temperature = Climate.getAvgTemp(world, chunkBlockPos);
        float rainfall = ProviderChunkData.getRainfall(world, chunkBlockPos);
        var type = BUSHES.stream()
                         .filter(x -> x.isValidConditions(temperature, rainfall))
                         .findFirst()
                         .orElse(null);

        if (type != null) {
          final int x = (chunkX << 4) + random.nextInt(16) + 8;
          final int z = (chunkZ << 4) + random.nextInt(16) + 8;
          final BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));

          if (world.getBlockState(pos).getMaterial().isLiquid() || !world.getBlockState(pos)
                                                                         .getMaterial()
                                                                         .isReplaceable()) {
            return;
          }
          var block = BlocksAgriculture.BERRY_BUSH.get(type);
          if (block.canPlaceBlockAt(world, pos)) {
            world.setBlockState(pos, block.getDefaultState());
          }
        }
      }
    }
  }
}
