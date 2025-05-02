package net.dries007.tfc.world.classic.worldgen.cave;

import su.terrafirmagreg.modules.core.feature.climate.Climate;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.objects.blocks.plants.BlockCreepingPlantTFCF;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
public class WorldGenCaveMoss extends WorldGenerator {

  private Plant plant;

  public void setGeneratedPlant(Plant plantIn) {
    this.plant = plantIn;
  }

  @Override
  public boolean generate(World worldIn, Random rng, BlockPos pos) {
    BlockCreepingPlantTFCF plantBlock = BlockCreepingPlantTFCF.get(plant);
    IBlockState state = plantBlock.getDefaultState();

    for (int i = 0; i < ChunkDataTFC.getRainfall(worldIn, pos) / 16; ++i) {
      BlockPos blockpos = pos.add(rng.nextInt(4) - rng.nextInt(4), rng.nextInt(4) - rng.nextInt(4), rng.nextInt(4) - rng.nextInt(4));

      if (plant.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
          worldIn.isAirBlock(blockpos) &&
          pos.getY() < WorldTypeTFC.SEALEVEL - 3 &&
          worldIn.getLightFor(EnumSkyBlock.SKY, blockpos) < 14 &&
          plantBlock.canBlockStay(worldIn, blockpos, state)) {
        int plantAge = plant.getAgeForWorldgen(rng, Climate.getActualTemp(worldIn, blockpos));
        setBlockAndNotifyAdequately(worldIn, blockpos, state.withProperty(BlockCreepingPlantTFCF.AGE, plantAge));
      }
    }
    return true;
  }
}
