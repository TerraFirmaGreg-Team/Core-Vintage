package su.terrafirmagreg.framework.manager.registry.base.block.spi;

import su.terrafirmagreg.api.data.LocalizeKeys;
import su.terrafirmagreg.api.data.enums.EnumDefault;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.registry.base.block.api.IBlockEntry;
import su.terrafirmagreg.framework.manager.registry.base.item.spi.BaseItemSlab;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import lombok.Getter;

import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.EnumProp.DEFAULT;

@Getter
@SuppressWarnings("deprecation")
public abstract class BaseBlockSlab extends BlockSlab implements IBlockEntry {

  protected final BlockSettings settings;

  public BaseBlockSlab(Block model) {
    this(BlockSettings.of(model));
  }

  public BaseBlockSlab(BlockSettings settings) {
    super(settings.getMaterial());

    this.settings = settings;

    getSettings()
      .ignoresProperties(DEFAULT)
      .itemBlock(isDouble() ? null : BaseItemSlab::new)
      .renderLayer(isDouble() ? BlockRenderLayer.CUTOUT : BlockRenderLayer.SOLID)
      .useNeighborBrightness();

    var state = getBlockState().getBaseState();
    if (!isDouble()) {
      state = state.withProperty(HALF, EnumBlockHalf.BOTTOM);
    }
    setDefaultState(state.withProperty(DEFAULT, EnumDefault.DEFAULT));
  }

  @Override
  public String getTranslationKey(int meta) {
    return getTranslationKey();
  }

  @Override
  public String getTranslationKey() {
    return ModUtils.localize(LocalizeKeys.BLOCK, this.getRegistryName());
  }

  public abstract boolean isDouble();

  @Override
  public IProperty<?> getVariantProperty() {
    return DEFAULT; // why is this not null-tolerable ...
  }

  @Override
  public Comparable<?> getTypeForItem(ItemStack stack) {
    return EnumDefault.DEFAULT;
  }

  @Override
  public boolean getUseNeighborBrightness(IBlockState state) {
    return getSettings().isUseNeighborBrightness();
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    IBlockState iblockstate = this.getDefaultState().withProperty(DEFAULT, EnumDefault.DEFAULT);

    if (!this.isDouble()) {
      iblockstate = iblockstate.withProperty(BlockSlab.HALF, (meta & 8) == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
    }

    return iblockstate;
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    int i = 0;

    if (!this.isDouble() && state.getValue(BlockSlab.HALF) == EnumBlockHalf.TOP) {
      i |= 8;
    }

    return i;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public BlockRenderLayer getRenderLayer() {
    return this.settings.getRenderLayer();
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return getHalfSlab().asItem();
  }

  @Override
  public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
    return new ItemStack(getHalfSlab());
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return this.isDouble() ? new BlockStateContainer(this, DEFAULT) : new BlockStateContainer(this, HALF, DEFAULT);
  }

  public abstract BaseBlockSlab getHalfSlab();

  public abstract BaseBlockSlab getDoubleSlab();
//
//  @Override
//  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
//    ItemStack stack = player.getHeldItem(hand);
//
//    if (!stack.isEmpty() && player.canPlayerEdit(pos, EnumFacing.UP, stack)) {
//      Comparable<?> comparable = this.getHalfSlab().getTypeForItem(stack);
//      IBlockState iblockstate = world.getBlockState(pos);
//
//      if (iblockstate.getBlock() == this.getHalfSlab()) {
//        IProperty<?> iproperty = this.getHalfSlab().getVariantProperty();
//        Comparable<?> comparable1 = iblockstate.getValue(iproperty);
//        BlockSlab.EnumBlockHalf blockslab$enumblockhalf = (BlockSlab.EnumBlockHalf) iblockstate.getValue(BlockSlab.HALF);
//
//        if ((blockslab$enumblockhalf == BlockSlab.EnumBlockHalf.BOTTOM ||
//             blockslab$enumblockhalf == BlockSlab.EnumBlockHalf.TOP) && comparable1 == comparable) {
//
//          IBlockState newState = this.makeState(iproperty, comparable1);
//          AxisAlignedBB aabb = newState.getCollisionBoundingBox(world, pos);
//
//          if (aabb != Block.NULL_AABB && world.checkNoEntityCollision(aabb.offset(pos))) {
//            world.setBlockState(pos, newState, 11);
//            SoundType soundtype = this.getDoubleSlab().getSoundType(newState, world, pos, player);
//            world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS,
//              (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
//            stack.shrink(1);
//
//            if (player instanceof EntityPlayerMP) {
//              CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos, stack);
//            }
//            return true;
//          }
//        }
//      }
//
//      return this.tryPlace(player, stack, world, pos, comparable);
//    }
//    return false;
//  }
//
//  protected <T extends Comparable<T>> IBlockState makeState(IProperty<T> p_185055_1_, Comparable<?> p_185055_2_) {
//    return this.getDoubleSlab().getDefaultState().withProperty(p_185055_1_, (T) p_185055_2_);
//  }
//
//  private boolean tryPlace(EntityPlayer player, ItemStack stack, World world, BlockPos pos, Object itemSlabType) {
//    IBlockState iblockstate = world.getBlockState(pos);
//
//    if (iblockstate.getBlock() == this.getHalfSlab()) {
//      Comparable<?> comparable = iblockstate.getValue(this.getHalfSlab().getVariantProperty());
//
//      if (comparable == itemSlabType) {
//        IBlockState newState = this.makeState(this.getHalfSlab().getVariantProperty(), comparable);
//        AxisAlignedBB aabb = newState.getCollisionBoundingBox(world, pos);
//
//        if (aabb != Block.NULL_AABB && world.checkNoEntityCollision(aabb.offset(pos))) {
//          world.setBlockState(pos, newState, 11);
//          SoundType soundtype = this.getDoubleSlab().getSoundType(newState, world, pos, player);
//          world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS,
//            (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
//          stack.shrink(1);
//          return true;
//        }
//      }
//    }
//    return false;
//  }

}
