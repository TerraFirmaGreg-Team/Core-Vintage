package su.terrafirmagreg.modules.device.object.block;

import su.terrafirmagreg.api.base.object.block.spi.BaseBlock;
import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.init.BlocksDevice;
import su.terrafirmagreg.modules.device.object.item.ItemFireStarter;
import su.terrafirmagreg.modules.device.object.tile.TileCharcoalForge;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.dries007.tfc.client.TFCSounds;

import org.jetbrains.annotations.Nullable;

import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.BoolProp.LIT;
import static su.terrafirmagreg.api.data.Properties.IntProp.TYPE;

@SuppressWarnings("deprecation")
public class BlockCharcoalPile extends BaseBlock {

  public static final Material CHARCOAL_MATERIAL = new Material(MapColor.BROWN);


  private static final AxisAlignedBB[] PILE_AABB = new AxisAlignedBB[]{
    new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D),
    new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D),
    new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D),
    new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D),
    new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D),
    new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D),
    new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D),
    new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D),
    new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)
  };

  public BlockCharcoalPile() {
    super(Settings.of(CHARCOAL_MATERIAL));

    getSettings()
      .registryKey("charcoal_pile")
      .hardness(1.0F)
      .harvestLevel(ToolClasses.SHOVEL, 0)
      .sound(TFCSounds.CHARCOAL_PILE);

    setDefaultState(getBlockState().getBaseState()
      .withProperty(TYPE, 1));
  }

  @Override
  public boolean isTopSolid(IBlockState state) {
    return state.getValue(TYPE) == 8;
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(TYPE, meta + 1);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(TYPE) - 1;
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return PILE_AABB[state.getValue(TYPE)];
  }

  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    if (worldIn.isRemote) {return;}

    // Try to drop the rock down
    IBlockState stateUnder = worldIn.getBlockState(pos.down());
    if (stateUnder.getBlock() instanceof BlockCharcoalPile) {
      int layersAt = state.getValue(TYPE);
      int layersUnder = stateUnder.getValue(TYPE);
      if (layersUnder < 8) {
        if (layersUnder + layersAt <= 8) {
          worldIn.setBlockState(pos.down(),
            stateUnder.withProperty(TYPE, layersAt + layersUnder));
          worldIn.setBlockToAir(pos);
        } else {
          worldIn.setBlockState(pos.down(), stateUnder.withProperty(TYPE, 8));
          worldIn.setBlockState(pos, state.withProperty(TYPE, layersAt + layersUnder - 8));
        }
      }
      return;
    }
    if (!stateUnder.isSideSolid(worldIn, pos, EnumFacing.UP)) {
      this.dropBlockAsItem(worldIn, pos, state, 0);
      worldIn.setBlockToAir(pos);
    }
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return Items.COAL;
  }

  @Override
  public int damageDropped(IBlockState state) {
    return 1;
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
    ItemStack stack = player.getHeldItem(hand);
    if (state.getValue(TYPE) >= 7 && BlockCharcoalForge.isValid(world, pos) && ItemFireStarter.onIgnition(stack)) {
      if (!world.isRemote) {
        world.setBlockState(pos, BlocksDevice.CHARCOAL_FORGE.get().getDefaultState().withProperty(LIT, true));
        TileUtils.getTile(world, pos, TileCharcoalForge.class).ifPresent(TileCharcoalForge::onCreate);
      }
      return true;
    }
    return false;
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, TYPE);
  }

  @Override
  public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
    this.onBlockHarvested(world, pos, state, player);

    if (player.isCreative()) {
      return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    if (!world.isRemote) {
      int layers = state.getValue(TYPE);
      if (layers == 1) {
        world.setBlockToAir(pos);
      } else {
        world.setBlockState(pos, state.withProperty(TYPE, layers - 1));
      }
    }
    return true;
  }

  @Override
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    return new ItemStack(Items.COAL, 1, 1);
  }

  @Override
  public boolean isFullCube(IBlockState state) {
    return state.getValue(TYPE) == 8;
  }

  @Override
  public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
    return face == EnumFacing.DOWN || state.getValue(TYPE) == 8 ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
  }

  @Override
  public @Nullable AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    return PILE_AABB[state.getValue(TYPE)];
  }

  @Override
  public boolean isOpaqueCube(IBlockState state) {
    return state.getValue(TYPE) == 8;
  }

}
