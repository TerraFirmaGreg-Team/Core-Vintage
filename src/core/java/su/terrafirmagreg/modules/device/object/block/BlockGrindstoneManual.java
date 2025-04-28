package su.terrafirmagreg.modules.device.object.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.device.object.tile.TileGrindstone;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.objects.items.TFCThingsConfigurableItem;
import net.dries007.tfcthings.main.ConfigTFCThings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockGrindstoneManual extends Block implements ICapabilitySize, TFCThingsConfigurableItem {

  public static final PropertyDirection FACING = BlockHorizontal.FACING;

  public BlockGrindstoneManual() {
    super(Material.WOOD);
    this.setRegistryName("grindstone_base");
    this.setTranslationKey("grindstone_base");
    this.setSoundType(SoundType.WOOD);
    this.setCreativeTab(CreativeTabsTFC.CT_MISC);
    this.setHardness(1.5f);
    this.setHarvestLevel(ToolClasses.PICKAXE, 0);
    this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
  }

  public boolean hasTileEntity(IBlockState state) {
    return true;
  }

  @Nullable
  public TileEntity createTileEntity(World world, IBlockState state) {
    return new TileGrindstone();
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, FACING);
  }

  @Nonnull
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta % 4));
  }

  public int getMetaFromState(IBlockState state) {
    return state.getValue(FACING).getHorizontalIndex();
  }

  public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
  }

  public boolean isOpaqueCube(IBlockState state) {
    return false;
  }

  public boolean isFullCube(IBlockState state) {
    return false;
  }

  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    IBlockState iblockstate = worldIn.getBlockState(pos.down());
    Block block = iblockstate.getBlock();

    if (block != Blocks.BARRIER) {
      BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP);
      return blockfaceshape == BlockFaceShape.SOLID || iblockstate.getBlock().isLeaves(iblockstate, worldIn, pos.down());
    } else {
      return false;
    }
  }

  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (hand.equals(EnumHand.MAIN_HAND)) {
      TileGrindstone teGrindstone = Helpers.getTE(world, pos, TileGrindstone.class);
      if (teGrindstone != null) {
        ItemStack heldStack = playerIn.getHeldItem(hand);
        IItemHandler inventory = teGrindstone.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (inventory != null) {

          int slot =
            inventory.getStackInSlot(TileGrindstone.SLOT_GRINDSTONE).isEmpty() && inventory.getStackInSlot(TileGrindstone.SLOT_INPUT).isEmpty()
            ? TileGrindstone.SLOT_GRINDSTONE : TileGrindstone.SLOT_INPUT;

          if (slot == TileGrindstone.SLOT_INPUT) {
            if (inventory.isItemValid(slot, heldStack)) {
              playerIn.setHeldItem(EnumHand.MAIN_HAND, teGrindstone.insertOrSwapItem(slot, heldStack));
              teGrindstone.setAndUpdateSlots(slot);
              return true;
            } else {
              if (!inventory.getStackInSlot(slot).isEmpty()) {
                ItemHandlerHelper.giveItemToPlayer(playerIn, inventory.extractItem(slot, inventory.getStackInSlot(slot).getCount(), false));
                return true;
              }
            }
          }

          if (slot == TileGrindstone.SLOT_GRINDSTONE && inventory.getStackInSlot(slot).isEmpty() && inventory.isItemValid(slot, heldStack)) {
            playerIn.setHeldItem(EnumHand.MAIN_HAND, teGrindstone.insertOrSwapItem(slot, heldStack));
            teGrindstone.setAndUpdateSlots(slot);
            return true;
          }
        }
      }
    }

    return false;
  }

  public void breakBlock(World world, BlockPos pos, IBlockState state) {
    TileGrindstone grindstone = Helpers.getTE(world, pos, TileGrindstone.class);
    if (grindstone != null) {
      grindstone.onBreakBlock(world, pos, state);
    }
    super.breakBlock(world, pos, state);
  }

  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP)) {
      this.dropBlockAsItem(worldIn, pos, state, 0);
      worldIn.setBlockToAir(pos);
    }
  }

  public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
    return BlockFaceShape.UNDEFINED;
  }

  @Nonnull
  @Override
  public Size getSize(@Nonnull ItemStack itemStack) {
    return Size.LARGE;
  }

  @Nonnull
  @Override
  public Weight getWeight(@Nonnull ItemStack itemStack) {
    return Weight.MEDIUM;
  }

  @Override
  public boolean isEnabled() {
    return ConfigTFCThings.Items.MASTER_ITEM_LIST.enableGrindstones;
  }
}
