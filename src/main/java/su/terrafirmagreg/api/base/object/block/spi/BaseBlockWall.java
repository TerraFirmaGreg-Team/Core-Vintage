package su.terrafirmagreg.api.base.object.block.spi;

import su.terrafirmagreg.api.base.object.block.api.IBlockSettings;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
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
public abstract class BaseBlockWall extends BlockWall implements IBlockSettings {

  protected final Settings settings;
  private final Block modelBlock;
  private final IBlockState modelState;

  public BaseBlockWall(Block block) {
    super(block);

    this.settings = Settings.of(block);
    this.modelBlock = block;
    this.modelState = block.getDefaultState();

    getSettings()
      .ignoresProperties(VARIANT);
  }

  @Override
  public String getLocalizedName() {
    return I18n.translateToLocal(this.getTranslationKey() + ".name");
  }

  @Override
  public String getTranslationKey() {
    return ModUtils.localize("block", this.getRegistryName());
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

}
