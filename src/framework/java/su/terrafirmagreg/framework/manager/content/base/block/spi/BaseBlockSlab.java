package su.terrafirmagreg.framework.manager.content.base.block.spi;

import su.terrafirmagreg.api.data.LocalizeKeys;
import su.terrafirmagreg.api.data.enums.EnumDefault;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.content.base.block.api.IBlockEntry;
import su.terrafirmagreg.framework.manager.content.base.item.spi.BaseItemBlock;
import su.terrafirmagreg.framework.manager.content.provider.IProviderBlockColor;
import su.terrafirmagreg.framework.manager.content.provider.IProviderBlockPlacement;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import lombok.Getter;

import java.util.Random;
import java.util.function.Supplier;

import static su.terrafirmagreg.api.data.Properties.EnumProp.DEFAULT;

@SuppressWarnings("deprecation")
@Getter
public abstract class BaseBlockSlab extends BlockSlab implements IBlockEntry, IProviderBlockPlacement, IProviderBlockColor {

  protected final BlockSettings settings;
  protected final Block modelBlock;
  protected BlockSlab doubleSlab;
  protected BlockSlab singleSlab;

  /**
   * Создает плиту на основе блока
   */
  public BaseBlockSlab(Block model) {
    this(BlockSettings.of(model));

  }

  /**
   * Создает плиту с кастомными настройками
   */
  public BaseBlockSlab(BlockSettings settings) {
    super(settings.getMaterial());
    this.settings = settings;
    this.modelBlock = settings.getBlock();

    getSettings()
      .ignoresProperties(DEFAULT)
      .itemBlock(isDouble() ? null : BaseItemBlock::new)
      .customResource(settings.getResource(), (isDouble() ? "_slab_double" : "_slab"))
      .renderLayer(isDouble() ? BlockRenderLayer.CUTOUT : BlockRenderLayer.SOLID)
      .useNeighborBrightness();

    var state = getBlockState().getBaseState();
    if (!isDouble()) {

      state = state.withProperty(HALF, EnumBlockHalf.BOTTOM);
    }

    setDefaultState(state.withProperty(DEFAULT, EnumDefault.DEFAULT));

    this.fullBlock = this.settings.isOpaque();
    this.lightOpacity = this.fullBlock ? 255 : 0;
    this.translucent = this.settings.isTranslucent();
    this.useNeighborBrightness = this.settings.isUseNeighborBrightness();
  }


  @Override
  public String getTranslationKey(int meta) {

    return getSingleSlab().getTranslationKey();
  }

  @Override
  public String getTranslationKey() {

    return ModUtils.localize(LocalizeKeys.BLOCK, this.getRegistryName());
  }

  public BlockSlab getDoubleSlab() {

    return BlockUtils.BLOCK_TO_DOUBLE_SLAB.get(modelBlock);
  }

  public BlockSlab getSingleSlab() {

    return BlockUtils.BLOCK_TO_SINGLE_SLAB.get(modelBlock);
  }

  @Override
  public IBlockColor getBlockColor() {
    return modelBlock instanceof IProviderBlockColor provider ? provider.getBlockColor() : null;
  }

  @Override
  public IItemColor getItemColor() {
    return modelBlock instanceof IProviderBlockColor provider ? provider.getItemColor() : null;
  }

  public static class Single extends BaseBlockSlab {


    public Single(Block model) {
      super(model);
      this.singleSlab = this;

      BlockUtils.BLOCK_TO_SINGLE_SLAB.put(model, this);
    }

    @Override
    public boolean isDouble() {
      return false;
    }
  }

  public static class Double extends BaseBlockSlab {


    public Double(Block model) {
      super(model);
      this.doubleSlab = this;

      BlockUtils.BLOCK_TO_DOUBLE_SLAB.put(model, this);
    }

    @Override
    public boolean isDouble() {
      return true;
    }

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
    return Item.getItemFromBlock(getSingleSlab());
  }

  @Override
  public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
    return new ItemStack(getSingleSlab());
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return this.isDouble() ? new BlockStateContainer(this, DEFAULT) : new BlockStateContainer(this, HALF, DEFAULT);
  }

  /**
   * Called when a Block is right-clicked with this Item
   */
  @Override
  public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, Supplier<EnumActionResult> resultSupplier) {

    if (!stack.isEmpty() && player.canPlayerEdit(pos.offset(facing), facing, stack)) {
      final var singleSlab = getSingleSlab();
      Comparable<?> comparable = singleSlab.getTypeForItem(stack);
      IBlockState iblockstate = worldIn.getBlockState(pos);

      if (iblockstate.getBlock() == singleSlab) {
        IProperty<?> iproperty = singleSlab.getVariantProperty();
        Comparable<?> comparable1 = iblockstate.getValue(iproperty);
        BlockSlab.EnumBlockHalf blockslab$enumblockhalf = iblockstate.getValue(BlockSlab.HALF);

        if ((facing == EnumFacing.UP && blockslab$enumblockhalf == BlockSlab.EnumBlockHalf.BOTTOM || facing == EnumFacing.DOWN && blockslab$enumblockhalf == BlockSlab.EnumBlockHalf.TOP) && comparable1 == comparable) {
          IBlockState iblockstate1 = this.makeState(iproperty, comparable1);
          AxisAlignedBB axisalignedbb = iblockstate1.getCollisionBoundingBox(worldIn, pos);

          if (axisalignedbb != Block.NULL_AABB && worldIn.checkNoEntityCollision(axisalignedbb.offset(pos)) && worldIn.setBlockState(pos, iblockstate1, 11)) {
            SoundType soundtype = this.getDoubleSlab().getSoundType(iblockstate1, worldIn, pos, player);
            worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
            stack.shrink(1);

            if (player instanceof EntityPlayerMP) {
              CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos, stack);
            }
          }

          return EnumActionResult.SUCCESS;
        }
      }

      return this.tryPlace(player, stack, worldIn, pos.offset(facing), comparable) ? EnumActionResult.SUCCESS : resultSupplier.get();
    } else {
      return EnumActionResult.FAIL;
    }
  }

  @SideOnly(Side.CLIENT)
  @Override
  public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack, Supplier<Boolean> resultSupplier) {
    final var singleSlab = getSingleSlab();
    BlockPos blockpos = pos;
    IProperty<?> iproperty = singleSlab.getVariantProperty();
    Comparable<?> comparable = singleSlab.getTypeForItem(stack);
    IBlockState iblockstate = worldIn.getBlockState(pos);

    if (iblockstate.getBlock() == singleSlab) {
      boolean flag = iblockstate.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP;

      if ((side == EnumFacing.UP && !flag || side == EnumFacing.DOWN && flag) && comparable == iblockstate.getValue(iproperty)) {
        return true;
      }
    }

    pos = pos.offset(side);
    IBlockState iblockstate1 = worldIn.getBlockState(pos);
    return iblockstate1.getBlock() == singleSlab && comparable == iblockstate1.getValue(iproperty) || resultSupplier.get();
  }

  private boolean tryPlace(EntityPlayer player, ItemStack stack, World worldIn, BlockPos pos, Object itemSlabType) {
    IBlockState iblockstate = worldIn.getBlockState(pos);
    final var singleSlab = getSingleSlab();
    final var doubleSlab = getDoubleSlab();

    if (iblockstate.getBlock() == singleSlab) {
      Comparable<?> comparable = iblockstate.getValue(singleSlab.getVariantProperty());

      if (comparable == itemSlabType) {
        IBlockState iblockstate1 = this.makeState(singleSlab.getVariantProperty(), comparable);
        AxisAlignedBB axisalignedbb = iblockstate1.getCollisionBoundingBox(worldIn, pos);

        if (axisalignedbb != Block.NULL_AABB && worldIn.checkNoEntityCollision(axisalignedbb.offset(pos)) && worldIn.setBlockState(pos, iblockstate1, 11)) {
          SoundType soundtype = doubleSlab.getSoundType(iblockstate1, worldIn, pos, player);
          worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
          stack.shrink(1);
        }

        return true;
      }
    }

    return false;
  }

  protected <T extends Comparable<T>> IBlockState makeState(IProperty<T> property, Comparable<?> comparable) {
    return this.getDoubleSlab().getDefaultState().withProperty(property, (T) comparable);
  }
}
