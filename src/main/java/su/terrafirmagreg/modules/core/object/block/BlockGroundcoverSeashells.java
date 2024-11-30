package su.terrafirmagreg.modules.core.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockGroundcover;
import su.terrafirmagreg.modules.soil.object.block.BlockSoilFarmland;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dries007.tfc.objects.items.ItemsTFCF;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

@SuppressWarnings("deprecation")
public class BlockGroundcoverSeashells extends BaseBlockGroundcover {

  int[] chance = {38, 5, 38, 5, 5, 5, 3, 1};
  int[] amount = {2, 1, 2, 1, 1, 1, 1, 1};
  int index = 0;

  public BlockGroundcoverSeashells() {
    super(Settings.of(Material.GROUND));

    getSettings()
      .registryKey("core/groundcover/seashells")
      .sound(SoundType.STONE)
      .oreDict("seashell")
      .oreDict("seashells");
  }

  private Item getWeightedDrop(int chance, int index, int currentNumber) {
    this.index = index;
    if (chance <= currentNumber) {
      Item[] drops = {
        ItemsTFCF.CLAM, ItemsTFCF.LIVE_CLAM,
        ItemsTFCF.SCALLOP, ItemsTFCF.LIVE_SCALLOP,
        ItemsTFCF.LIVE_STARFISH, ItemsTFCF.CONCH,
        ItemsTFCF.PEARL, ItemsTFCF.BLACK_PEARL
      };
      return drops[index];
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

  @NotNull
  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    int chance = rand.nextInt(100) + 1;
    return getWeightedDrop(chance, 0, this.chance[0]);
  }

}
