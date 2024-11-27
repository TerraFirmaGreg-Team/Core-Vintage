package su.terrafirmagreg.modules.wood.object.generator;

import su.terrafirmagreg.api.helper.BlockHelper;
import su.terrafirmagreg.modules.wood.api.generator.ITreeGenerator;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.init.BlocksWood;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodSapling;

import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

import static net.minecraft.block.BlockLeaves.DECAYABLE;
import static net.minecraft.block.BlockLog.LOG_AXIS;
import static su.terrafirmagreg.api.data.Properties.BoolProp.PLACED;

public class GeneratorTreeBushes implements ITreeGenerator {

  @Override
  public void generateTree(TemplateManager manager, World world, BlockPos pos, WoodType type, Random rand, boolean isWorldGen) {
    IBlockState leaves = BlocksWood.LEAVES.get(type).getDefaultState().withProperty(DECAYABLE, true);

    // Has to fake being placed, otherwise the log will just poof out of existence. todo: better fix for this.
    checkAndPlace(BlocksWood.LOG.get(type)
                                .getDefaultState()
                                .withProperty(PLACED, true)
                                .withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE), world, pos);
    checkAndPlace(leaves, world, pos.add(0, 1, 0));

    for (EnumFacing face : EnumFacing.HORIZONTALS) {
      if (rand.nextFloat() < 0.9) {
        checkAndPlace(leaves, world, pos.offset(face));
        checkAndPlace(leaves, world, pos.offset(face).add(0, -1, 0));
        if (rand.nextFloat() < 0.7) {
          checkAndPlace(leaves, world, pos.offset(face).add(0, 1, 0));
        }

        if (rand.nextFloat() < 0.5) {
          checkAndPlace(leaves, world, pos.offset(face).offset(face.rotateY()));
        }
      }

    }
  }

  @Override
  public boolean canGenerateTree(World world, BlockPos pos, WoodType type) {
    // Check if there is soil beneath
    if (!BlockHelper.isSoil(world.getBlockState(pos.down()))) {
      return false;
    }

    // Check the position for liquids, etc.
    if (world.getBlockState(pos).getMaterial().isLiquid() ||
        !world.getBlockState(pos).getMaterial().isReplaceable()) {

      if (!(world.getBlockState(pos) instanceof BlockWoodSapling)) {
        return false;
      }
    }

    // Check if there is sufficient light level
    return world.getLightFromNeighbors(pos) >= 7;
  }

  private void checkAndPlace(IBlockState state, World world, BlockPos pos) {
    if (world.getBlockState(pos).getMaterial().isReplaceable()) {
      world.setBlockState(pos, state);
    }
  }
}
