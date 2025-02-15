package net.dries007.tfc.objects.blocks;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.dries007.tfc.objects.items.TFCThingsConfigurableItem;
import net.dries007.tfc.objects.te.TEGemDisplay;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfcthings.main.ConfigTFCThings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockGemDisplay extends Block implements ICapabilitySize, TFCThingsConfigurableItem {

  public static final PropertyDirection FACING = BlockHorizontal.FACING;
  public static final PropertyBool TOP = PropertyBool.create("top");

  public BlockGemDisplay(String rock) {
    super(Material.ROCK);
    this.setRegistryName("gem_display/" + rock);
    this.setTranslationKey("gem_display_" + rock);
    this.setSoundType(SoundType.STONE);
    this.setHarvestLevel(ToolClasses.PICKAXE, 0);
    this.setHardness(1.0F);
    this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.EAST).withProperty(TOP, Boolean.valueOf(true)));
  }

  @Nonnull
  @Override
  public Size getSize(@Nonnull ItemStack itemStack) {
    return Size.LARGE;
  }

  @Nonnull
  @Override
  public Weight getWeight(@Nonnull ItemStack itemStack) {
    return Weight.HEAVY;
  }

  public boolean isFullCube(IBlockState state) {
    return false;
  }

  @Nonnull
  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.MODEL;
  }

  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, FACING, TOP);
  }

  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta)).withProperty(TOP, meta / 4 % 2 != 0);
  }

  public int getMetaFromState(IBlockState state) {
    return state.getValue(FACING).getHorizontalIndex() + (state.getValue(TOP) ? 4 : 0);
  }

  public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
  }

  @Nonnull
  public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
    return BlockFaceShape.UNDEFINED;
  }

  public boolean isOpaqueCube(IBlockState state) {
    return false;
  }

  public boolean hasTileEntity(IBlockState state) {
    return true;
  }

  @Nullable
  public TileEntity createTileEntity(World world, IBlockState state) {
    return new TEGemDisplay();
  }

  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (!worldIn.isRemote) {
      TEGemDisplay te = Helpers.getTE(worldIn, pos, TEGemDisplay.class);
      if (te != null) {
        return te.onRightClick(playerIn, hand);
      }
    }

    return true;
  }

  public void breakBlock(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
    TEGemDisplay te = Helpers.getTE(worldIn, pos, TEGemDisplay.class);
    if (te != null) {
      te.onBreakBlock();
    }
    super.breakBlock(worldIn, pos, state);
  }

  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    if (fromPos.equals(pos.up())) {
      if (worldIn.getBlockState(fromPos).getBlock() instanceof BlockAir) {
        state = state.withProperty(TOP, Boolean.valueOf(true));
      } else {
        state = state.withProperty(TOP, Boolean.valueOf(false));
      }
      worldIn.setBlockState(pos, state, 2);
    }
  }

  @Override
  public boolean hasComparatorInputOverride(IBlockState state) {
    return true;
  }

  @Override
  public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
    TEGemDisplay te = (TEGemDisplay) world.getTileEntity(pos);
    return (int) Math.floor(15 * ((double) te.getSize() / (double) te.getMaxStackSize()));
  }

  @Override
  public boolean isEnabled() {
    return ConfigTFCThings.Items.MASTER_ITEM_LIST.enableGemDisplays;
  }
}
