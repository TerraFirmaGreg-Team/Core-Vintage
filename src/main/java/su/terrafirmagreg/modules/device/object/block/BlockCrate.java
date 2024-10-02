package su.terrafirmagreg.modules.device.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockContainer;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.device.object.tile.TileCrate;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.data.Properties.BoolProp.SEALED;

/**
 * Crate is an inventory that preserves the contents when sealed It can be picked up and keeps it's inventory Sealed state is stored in a block state property,
 * and cached in the TE (for gui purposes)
 */
@SuppressWarnings("deprecation")
public class BlockCrate extends BaseBlockContainer {

  private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.05D, 0.0D, 0.05D, 0.95D,
                                                                      0.875D, 0.95D);
  private static final AxisAlignedBB BOUNDING_BOX_SEALED = new AxisAlignedBB(0.05D, 0.0D, 0.05D,
                                                                             0.95D, 0.875D, 0.95D);

  public BlockCrate() {
    super(Settings.of(Material.CIRCUITS));

    getSettings()
      .registryKey("device/crate")
      .sound(SoundType.WOOD)
      .hardness(2F)
      .nonCube()
      .weight(Weight.VERY_HEAVY);

    setDefaultState(blockState.getBaseState()
                              .withProperty(SEALED, false));
  }

  /**
   * Used to update the vessel seal state and the TE, in the correct order
   */
  public static void toggleCrateSeal(World world, BlockPos pos) {
    TileUtils.getTile(world, pos, TileCrate.class).ifPresent(tile -> {
      IBlockState state = world.getBlockState(pos);
      boolean previousSealed = state.getValue(SEALED);
      world.setBlockState(pos, state.withProperty(SEALED, !previousSealed));
      if (previousSealed) {
        tile.onUnseal();
      } else {
        tile.onSealed();
      }
    });
  }

  @Override
  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.MODEL;
  }

  @Override
  public void breakBlock(World world, BlockPos pos, IBlockState state) {
    TileUtils.getTile(world, pos, TileCrate.class).ifPresent(tile -> tile.onBreakBlock(world, pos, state));
    super.breakBlock(world, pos, state);
  }

  @Override
  public Class<TileCrate> getTileClass() {
    return TileCrate.class;
  }

  @Override
  public Size getSize(ItemStack stack) {
    return stack.getTagCompound() == null ? Size.VERY_LARGE
                                          : Size.HUGE; // Causes overburden if sealed
  }

  @Override
  public boolean canStack(ItemStack stack) {
    return stack.getTagCompound() == null;
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(SEALED, meta == 1);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(SEALED) ? 1 : 0;
  }

  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return state.getValue(SEALED) ? BOUNDING_BOX_SEALED : BOUNDING_BOX;
  }

  @Override
  public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn,
                              BlockPos fromPos) {
    if (!canStay(world, pos)) {
      world.destroyBlock(pos, true);
    }
  }

  @Override
  public boolean canPlaceBlockAt(World world, BlockPos pos) {
    return canStay(world, pos);
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (!world.isRemote) {
      ItemStack heldItem = playerIn.getHeldItem(hand);
      TileUtils.getTile(world, pos, TileCrate.class).ifPresent(tile -> {
        if (heldItem.isEmpty() && playerIn.isSneaking()) {
          world.playSound(null, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1.0F, 0.85F);
          toggleCrateSeal(world, pos);
        } else {
          GuiHandler.openGui(world, pos, playerIn);
        }
      });
    }
    return true;
  }

  @Override
  public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    // If the barrel was sealed, then copy the contents from the item
    if (!world.isRemote) {
      NBTTagCompound nbt = stack.getTagCompound();
      if (nbt != null) {
        TileUtils.getTile(world, pos, TileCrate.class).ifPresent(tile -> {
          world.setBlockState(pos, state.withProperty(SEALED, true));
          tile.readFromItemTag(nbt);
        });
      }
    }
  }

  @Override
  public BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, SEALED);
  }

  @Override
  public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos,
                       IBlockState state, int fortune) {
    // Only drop the barrel if it's not sealed, since the barrel with contents will be already dropped by the TE
    if (!state.getValue(SEALED)) {
      super.getDrops(drops, world, pos, state, fortune);
    }
  }

  @Override
  public void onBlockExploded(World world, BlockPos pos, Explosion explosion) {
    // Unseal the barrel if an explosion destroys it, so it drops it's contents
    world.setBlockState(pos, world.getBlockState(pos).withProperty(SEALED, false));
    super.onBlockExploded(world, pos, explosion);
  }

  private boolean canStay(IBlockAccess world, BlockPos pos) {
    return world.getBlockState(pos.down())
                .getBlockFaceShape(world, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID;
  }

  @Nullable
  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileCrate();
  }
}
