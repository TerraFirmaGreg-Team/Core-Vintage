package su.terrafirmagreg.modules.core.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockGroundcover;
import su.terrafirmagreg.modules.soil.object.block.BlockSoilFarmland;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

@SuppressWarnings("deprecation")
public class BlockGroundcoverBones extends BaseBlockGroundcover {

  int[] chance = {90, 10};
  int[] amount = {2, 2};
  int index = 0;

  public BlockGroundcoverBones() {
    super(Settings.of(Material.GROUND));

    getSettings()
      .registryKey("core/groundcover/bones")
      .sound(SoundType.STONE)
      .oreDict("bone")
      .oreDict("bones");
  }

  private Item getWeightedDrop(int chance, int index, int currentNumber) {
    this.index = index;
    if (chance <= currentNumber) {
      return Items.BONE;
    } else {
      return getWeightedDrop(chance, index + 1, currentNumber + this.chance[index + 1]);
    }
  }

  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    if (!worldIn.isSideSolid(pos.down(), EnumFacing.UP) && !(worldIn.getBlockState(pos.down()).getBlock() instanceof BlockSoilFarmland)) {
      worldIn.setBlockToAir(pos);
    }
  }

  @Override
  public int quantityDropped(Random random) {
    return random.nextInt(amount[index]) + 1;
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    int chance = rand.nextInt(100) + 1;
    return getWeightedDrop(chance, 0, this.chance[0]);
  }

}
