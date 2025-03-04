package su.terrafirmagreg.modules.device.object.block;


import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.modules.device.init.BlocksDevice;
import su.terrafirmagreg.modules.device.object.item.ItemFireStarter;
import su.terrafirmagreg.modules.device.object.tile.TileLogPile;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.client.TFCGuiHandler;
import net.dries007.tfc.objects.blocks.property.ILightableBlock;
import net.dries007.tfc.objects.te.TEInventory;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.OreDictionaryHelper;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockLogPile extends Block implements ILightableBlock {

  private static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class, EnumFacing.Axis.X, EnumFacing.Axis.Z);

  public BlockLogPile() {
    super(Material.WOOD);

    setHardness(2.0F);
    setSoundType(SoundType.WOOD);
    setTickRandomly(true);
    setHarvestLevel(ToolClasses.AXE, 0);
    this.setDefaultState(this.getDefaultState().withProperty(AXIS, EnumFacing.Axis.X).withProperty(LIT, false));
  }

  private static boolean isValidCoverBlock(IBlockState offsetState, World world, BlockPos pos, EnumFacing side) {
    if (offsetState.getBlock() instanceof BlockLogPile || offsetState.getBlock() == BlocksDevice.CHARCOAL_PILE.get()) {
      return true;
    } else if (offsetState.getMaterial() == Material.GLASS && ConfigTFC.Devices.CHARCOAL_PIT.canAcceptGlass) {
      return offsetState.getBlockFaceShape(world, pos, side) == BlockFaceShape.SOLID || offsetState.isSideSolid(world, pos, side);
    }
    return !offsetState.getMaterial().getCanBurn() && (offsetState.getBlockFaceShape(world, pos, side) == BlockFaceShape.SOLID)
           || offsetState.isSideSolid(world, pos, side);
  }

  @Override
  @SuppressWarnings("deprecation")
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(AXIS, meta == 0 ? EnumFacing.Axis.Z : EnumFacing.Axis.X).withProperty(LIT, meta >= 2);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return (state.getValue(AXIS) == EnumFacing.Axis.Z ? 0 : 1) + (state.getValue(LIT) ? 2 : 0);
  }

  @Override
  public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
    if (!worldIn.isRemote && state.getValue(LIT)) {
      for (EnumFacing side : EnumFacing.values()) {
        final BlockPos offsetPos = pos.offset(side);
        IBlockState offsetState = worldIn.getBlockState(offsetPos);
        if (isValidCoverBlock(offsetState, worldIn, offsetPos, side.getOpposite())) {
          if (offsetState.getBlock() instanceof BlockLogPile && !offsetState.getValue(LIT)) {
            worldIn.setBlockState(offsetPos, offsetState.withProperty(LIT, true));
          }
        } else {
          worldIn.setBlockState(pos, Blocks.FIRE.getDefaultState());
        }
      }
    }
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
    if (stateIn.getValue(LIT)) {
      worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + rand.nextFloat(), pos.getY() + 1, pos.getZ() + rand.nextFloat(),
        0f, 0.1f + 0.1f * rand.nextFloat(), 0f);
      if (worldIn.getTotalWorldTime() % 80 == 0) {
        worldIn.playSound(
          (double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 0.5F, 0.6F, false);
      }
    }
  }

  @Override
  @SuppressWarnings("deprecation")
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    if (worldIn.getBlockState(pos.up()).getBlock() == Blocks.FIRE) {
      worldIn.setBlockState(pos, state.withProperty(LIT, true));
      TileLogPile te = Helpers.getTE(worldIn, pos, TileLogPile.class);
      if (te != null) {
        te.light();
      }
    }
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
    TileLogPile te = Helpers.getTE(world, pos, TileLogPile.class);
    if (te != null) {
      // Special Interactions
      // 1. Try and put a log inside (happens on right click event when sneaking)
      // 2. Try and light the TE
      // 3. Open the GUI
      ItemStack stack = player.getHeldItem(hand);
      if (!state.getValue(LIT) && side == EnumFacing.UP && world.getBlockState(pos.up()).getBlock().isReplaceable(world, pos)
          && ItemFireStarter.onIgnition(stack)) {
        // Light the Pile
        if (!world.isRemote) {
          world.setBlockState(pos, state.withProperty(LIT, true));
          te.light();
          world.setBlockState(pos.up(), Blocks.FIRE.getDefaultState());
          world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 1.0F, 1.0F);
        }
        return true;
      }
      if (OreDictionaryHelper.doesStackMatchOre(stack, "logWood")) {
        // Copy from InteractionManager since this is called first when player is not sneaking
        if (!player.isSneaking()) {
          if (te.insertLog(stack.copy())) {
            if (!world.isRemote) {
              world.playSound(null, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
              stack.shrink(1);
              player.setHeldItem(hand, stack);
            }
            return true;
          }
        } else {
          int inserted = te.insertLogs(stack.copy());
          if (inserted > 0) {
            if (!world.isRemote) {
              world.playSound(null, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
              stack.shrink(inserted);
              player.setHeldItem(hand, stack);
            }
            return true;
          }
        }
      }

      if (!player.isSneaking() && !state.getValue(LIT)) {
        if (!world.isRemote) {
          TFCGuiHandler.openGui(world, pos, player, TFCGuiHandler.Type.LOG_PILE);
        }
        return true;
      }
    }
    return false;
  }

  @Override
  @SuppressWarnings("deprecation")
  public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    if (placer.getHorizontalFacing().getAxis().isHorizontal()) {
      return getDefaultState().withProperty(AXIS, placer.getHorizontalFacing().getAxis());
    }
    return getDefaultState();
  }

  @Override
  public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
    // This can't use breakBlock as it needs to not drop when broken in order to create a charcoal pile
    if (!worldIn.isRemote && te instanceof TEInventory) {
      ((TEInventory) te).onBreakBlock(worldIn, pos, state);
    }
    super.breakBlock(worldIn, pos, state);
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, AXIS, LIT);
  }

  @Override
  public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
    return 60;
  }

  @Override
  public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
    return 30;
  }

  @Override
  public boolean hasTileEntity(IBlockState state) {
    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(World world, IBlockState state) {
    return new TileLogPile();
  }

  @Override
  public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    drops.clear();
  }

  @Override
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    TileLogPile te = Helpers.getTE(world, pos, TileLogPile.class);
    if (te != null) {
      return te.getLog().copy();
    }
    return ItemStack.EMPTY;
  }

}
