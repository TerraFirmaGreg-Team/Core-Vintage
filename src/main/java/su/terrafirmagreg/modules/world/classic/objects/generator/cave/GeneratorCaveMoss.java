package su.terrafirmagreg.modules.world.classic.objects.generator.cave;

import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.plant.api.types.type.PlantType;
import su.terrafirmagreg.modules.plant.init.BlocksPlant;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantCreeping;
import su.terrafirmagreg.modules.world.classic.WorldTypeClassic;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import net.dries007.tfc.util.climate.ClimateTFC;

import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.IntProp.AGE_4;

public class GeneratorCaveMoss extends WorldGenerator {

  private PlantType type;

  public void setGeneratedPlant(PlantType type) {
    this.type = type;
  }

  @Override
  public boolean generate(World worldIn, Random rng, BlockPos pos) {
    var plantBlock = (BlockPlantCreeping) BlocksPlant.PLANT.get(type);
    IBlockState state = plantBlock.getDefaultState();

    for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, pos) / 16; ++i) {
      BlockPos blockpos = pos.add(rng.nextInt(4) - rng.nextInt(4), rng.nextInt(4) - rng.nextInt(4), rng.nextInt(4) - rng.nextInt(4));

      if (type.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
          worldIn.isAirBlock(blockpos) &&
          pos.getY() < WorldTypeClassic.SEALEVEL - 3 &&
          worldIn.getLightFor(EnumSkyBlock.SKY, blockpos) < 14 &&
          plantBlock.canBlockStay(worldIn, blockpos, state)) {
        int plantAge = type.getAgeForWorldgen(rng, ClimateTFC.getActualTemp(worldIn, blockpos));
        setBlockAndNotifyAdequately(worldIn, blockpos, state.withProperty(AGE_4, plantAge));
      }
    }
    return true;
  }
}
