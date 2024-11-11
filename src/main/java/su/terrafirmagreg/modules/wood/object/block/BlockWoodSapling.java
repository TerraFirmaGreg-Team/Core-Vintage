package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.api.base.item.BaseItemBlock;
import su.terrafirmagreg.api.registry.provider.IProviderTile;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.soil.client.GrassColorHandler;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;
import su.terrafirmagreg.modules.wood.object.itemblock.ItemBlockWoodSapling;
import su.terrafirmagreg.modules.wood.object.tile.TileWoodSapling;

import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.util.IGrowingPlant;
import net.dries007.tfc.util.calendar.ICalendar;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.List;
import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.IntProp.STAGE_5;

@Getter
@SuppressWarnings("deprecation")
public class BlockWoodSapling extends BlockBush implements IWoodBlock, IGrowable, IGrowingPlant, IProviderTile {


  protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.1, 0, 0.1, 0.9, 0.9, 0.9);

  protected final Settings settings;
  protected final WoodBlockVariant variant;
  protected final WoodType type;

  public BlockWoodSapling(WoodBlockVariant variant, WoodType type) {
    this.variant = variant;
    this.type = type;
    this.settings = Settings.of(Material.PLANTS);

    getSettings()
      .registryKey(type.getRegistryKey(variant))
      .ignoresProperties(STAGE_5)
      .sound(SoundType.PLANT)
      .hardness(0.0F)
      .oreDict(variant)
      .oreDict(variant, type);

    setDefaultState(blockState.getBaseState()
                              .withProperty(STAGE_5, 0));

    BlockUtils.setFireInfo(this, 5, 20);
  }


  @Override
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(STAGE_5, meta);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(STAGE_5);
  }

  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    var tile = TileUtils.getTile(worldIn, pos, TileWoodSapling.class);
    tile.ifPresent(TileWoodSapling::resetCounter);
    super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, STAGE_5);
  }

  @Override
  public EnumOffsetType getOffsetType() {
    return EnumOffsetType.XZ;
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    super.addInformation(stack, worldIn, tooltip, flagIn);
    this.type.addInfo(stack, worldIn, tooltip, flagIn);
  }

  @Override
  public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
    super.updateTick(world, pos, state, random);

    if (!world.isRemote) {
      var tile = TileUtils.getTile(world, pos, TileWoodSapling.class);
      tile.ifPresent(tileWoodSapling -> {
        long days = tileWoodSapling.getTicksSinceUpdate() / ICalendar.TICKS_IN_DAY;
        if (days > this.type.getMinGrowthTime()) {
          grow(world, random, pos, state);
        }
      });
    }
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return SAPLING_AABB;
  }

  @Override
  public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
    return EnumPlantType.Plains;
  }

  @Override
  public boolean canGrow(World world, BlockPos blockPos, IBlockState blockState, boolean b) {
    return true;
  }

  @Override
  public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, IBlockState blockState) {
    return false;
  }

  @Override
  public void grow(World world, Random random, BlockPos blockPos, IBlockState blockState) {
    // this.type.makeTree(world, blockPos, random, false);
  }

  @Override
  public GrowthStatus getGrowingStatus(IBlockState state, World world, BlockPos pos) {
    return GrowthStatus.GROWING;
  }


  @Override
  public @Nullable BaseItemBlock getItemBlock() {
    return new ItemBlockWoodSapling(this);
  }

  @Override
  public @Nullable TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileWoodSapling();
  }

  @Override
  public Class<TileWoodSapling> getTileClass() {
    return TileWoodSapling.class;
  }

  @Override
  public IBlockColor getBlockColor() {
    return GrassColorHandler::computeGrassColor;
  }


  @Override
  public IItemColor getItemColor() {
    return (s, i) -> this.getBlockColor().colorMultiplier(this.getDefaultState(), null, null, i);
  }


}
