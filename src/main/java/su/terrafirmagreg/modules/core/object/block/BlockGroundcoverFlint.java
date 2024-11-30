package su.terrafirmagreg.modules.core.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockGroundcover;
import su.terrafirmagreg.modules.soil.object.block.BlockSoilFarmland;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.dries007.tfcflorae.client.GuiHandler;
import net.dries007.tfcflorae.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

@SuppressWarnings("deprecation")
public class BlockGroundcoverFlint extends BaseBlockGroundcover {

  Item[] drops = {Items.FLINT};
  int[] chance = {100};
  int[] amount = {2};
  int index = 0;

  public BlockGroundcoverFlint() {
    super(Settings.of(Material.GROUND));

    getSettings()
      .registryKey("core/groundcover/flint")
      .sound(SoundType.STONE)
      .oreDict("flint");
  }

  private Item getWeightedDrop(int chance, int index, int currentNumber) {
    this.index = index;
    if (chance <= currentNumber) {
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
    int chance = rand.nextInt(100);
    return getWeightedDrop(chance, 0, this.chance[0]);
  }

  @NotNull
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @NotNull EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (!world.isRemote && !player.isSneaking() && stack.getCount() > 0) {
      GuiHandler.openGui(world, player.getPosition(), player, GuiHandler.Type.FLINT);
    }
    return new ActionResult<>(EnumActionResult.SUCCESS, stack);
  }


  public void onRightClick(PlayerInteractEvent.RightClickItem event) {
    EnumHand hand = event.getHand();
    if (OreDictionaryHelper.doesStackMatchOre(event.getItemStack(), "flint")
        && hand == EnumHand.MAIN_HAND) {
      EntityPlayer player = event.getEntityPlayer();
      World world = event.getWorld();
      if (!world.isRemote && !player.isSneaking()) {
        GuiHandler.openGui(world, player.getPosition(), player, GuiHandler.Type.FLINT);
      }
    }
  }
}
