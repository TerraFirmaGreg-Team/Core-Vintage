package su.terrafirmagreg.framework.manager.content.base.block.spi;

import su.terrafirmagreg.api.data.LocalizeKeys;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.content.base.block.api.IBlockEntry;
import su.terrafirmagreg.framework.manager.content.provider.IProviderBlockColor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import lombok.Getter;

import java.util.Random;


@Getter
@SuppressWarnings("deprecation")
public class BaseBlockWall extends BlockWall implements IBlockEntry, IProviderBlockColor {


  protected final BlockSettings settings;
  protected final Block modelBlock;
  protected final IBlockState modelState;

  public BaseBlockWall(Block block) {
    this(BlockSettings.of(block));

  }

  public BaseBlockWall(BlockSettings settings) {
    super(settings.getBlock());

    this.settings = settings;
    this.modelBlock = settings.getBlock();
    this.modelState = settings.getBlock().getDefaultState();

    getSettings()
      .ignoresProperties(VARIANT)
      .customResource(settings.getResource(), "_wall")
      .renderLayer(BlockRenderLayer.CUTOUT)
      .addOreDict("wall");

    this.fullBlock = this.settings.isOpaque();
    this.lightOpacity = this.fullBlock ? 255 : 0;
    this.translucent = this.settings.isTranslucent();
    this.useNeighborBrightness = this.settings.isUseNeighborBrightness();

    BlockUtils.BLOCK_TO_WALL.put(settings.getBlock(), this);
  }


  @Override
  public String getLocalizedName() {
    return I18n.translateToLocal(this.getTranslationKey() + ".name");
  }

  @Override
  public String getTranslationKey() {
    return ModUtils.localize(LocalizeKeys.BLOCK, this.getRegistryName());
  }

  @Override
  public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
    items.add(new ItemStack(this));
  }

  @Override
  public int damageDropped(IBlockState state) {
    return 0;
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState();
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return 0;
  }

  @Override
  public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    return this.modelBlock.getMapColor(this.modelState, worldIn, pos);
  }

  @Override
  public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    this.modelBlock.updateTick(worldIn, pos, state, rand);
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
    this.modelBlock.randomDisplayTick(stateIn, worldIn, pos, rand);
  }

  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    this.modelBlock.breakBlock(worldIn, pos, this.modelState);
  }

  @Override
  public float getExplosionResistance(Entity exploder) {
    return this.modelBlock.getExplosionResistance(exploder);
  }

  @Override
  public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
    this.modelBlock.onExplosionDestroy(worldIn, pos, explosionIn);
  }

  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    return this.modelBlock.canPlaceBlockAt(worldIn, pos);
  }

  @Override
  public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
    this.modelBlock.onEntityWalk(worldIn, pos, entityIn);
  }

  @Override
  public Vec3d modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3d motion) {
    return this.modelBlock.modifyAcceleration(worldIn, pos, entityIn, motion);
  }


  @Override
  public IBlockColor getBlockColor() {
    return modelBlock instanceof IProviderBlockColor provider ? provider.getBlockColor() : null;
  }

  @Override
  public IItemColor getItemColor() {
    return modelBlock instanceof IProviderBlockColor provider ? provider.getItemColor() : null;
  }
}
