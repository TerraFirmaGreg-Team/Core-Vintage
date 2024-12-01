package net.dries007.tfc.objects.blocks;

import su.terrafirmagreg.api.base.tile.BaseTileTickCounter;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.device.object.item.ItemFireStarter;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.BoolProp.LIT;

public class BlockTorchTFC extends BlockTorch implements ICapabilitySize {

  public BlockTorchTFC() {
    setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.UP).withProperty(LIT, true));
    setHardness(0f);
    setLightLevel(0.9375F);
    setTickRandomly(true);
    setSoundType(SoundType.WOOD);

    OreDictionaryHelper.register(this, "torch");
  }

  @Override
  public @NotNull Weight getWeight(@NotNull ItemStack stack) {
    return Weight.LIGHT; // Stacksize = 32
  }

  @Override
  public @NotNull Size getSize(@NotNull ItemStack stack) {
    return Size.SMALL; // Can store anywhere
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
    if (!stateIn.getValue(LIT)) {
      return;
    }
    super.randomDisplayTick(stateIn, worldIn, pos, rand);
  }

  @Override
  @NotNull
  public IBlockState getStateFromMeta(int meta) {
    return super.getStateFromMeta(meta % 6).withProperty(LIT, meta >= 6);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return (state.getValue(LIT) ? 6 : 0) + super.getMetaFromState(state);
  }

  @Override
  @NotNull
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, FACING, LIT);
  }

  @Override
  public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
    if (worldIn.isRemote) {return;}
    TileUtils.getTile(worldIn, pos, BaseTileTickCounter.class)
             .filter(tile -> tile.getTicksSinceUpdate() > ConfigTFC.General.OVERRIDES.torchTime && ConfigTFC.General.OVERRIDES.torchTime > 0)
             .ifPresent(tile -> {
               worldIn.setBlockState(pos, state.withProperty(LIT, false));
               tile.resetCounter();
             });
  }

  @Override
  @NotNull
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return state.getValue(LIT) ? Item.getItemFromBlock(Blocks.TORCH) : Items.STICK;
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (worldIn.isRemote) {return true;}

    ItemStack stack = playerIn.getHeldItem(hand);
    if (state.getValue(LIT)) {
      if (OreDictUtils.contains(stack, "stickWood")) {
        stack.shrink(1);
        ItemHandlerHelper.giveItemToPlayer(playerIn, new ItemStack(Blocks.TORCH));
      }
    }
    if (BlockTorchTFC.canLight(stack)) {
      worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(LIT, true));
      TileUtils.getTile(worldIn, pos, BaseTileTickCounter.class).ifPresent(BaseTileTickCounter::resetCounter);
    }

    return true;
  }

  public static boolean canLight(ItemStack stack) {
    return stack.getItem() == Item.getItemFromBlock(Blocks.TORCH) || ItemFireStarter.canIgnite(stack);
  }

  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    // Set the initial counter value
    TileUtils.getTile(worldIn, pos, BaseTileTickCounter.class).ifPresent(BaseTileTickCounter::resetCounter);
    super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
  }

  @Override
  public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
    return state.getValue(LIT) ? super.getLightValue(state, world, pos) : 0;
  }

  @Override
  public boolean hasTileEntity(IBlockState state) {
    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(World world, IBlockState state) {
    return new BaseTileTickCounter();
  }
}
