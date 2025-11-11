package su.terrafirmagreg.modules.wood.content.block;

import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockSapling;
import su.terrafirmagreg.framework.manager.content.provider.IProviderBlockColor;
import su.terrafirmagreg.framework.manager.content.provider.IProviderTile;
import su.terrafirmagreg.helper.GrassColorHelper;
import su.terrafirmagreg.modules.core.feature.calendar.spi.ICalendar;
import su.terrafirmagreg.modules.wood.api.IWoodEntry;
import su.terrafirmagreg.modules.wood.api.type.WoodType;
import su.terrafirmagreg.modules.wood.content.itemblock.ItemBlockWoodSapling;
import su.terrafirmagreg.modules.wood.content.tile.TileWoodSapling;

import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.util.IGrowingPlant;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.List;
import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.IntProp.STAGE_2;

@Getter
@SuppressWarnings("deprecation")
public class BlockWoodSapling extends BaseBlockSapling implements IWoodEntry, IGrowable, IGrowingPlant, IProviderTile, IProviderBlockColor {


  protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.1, 0, 0.1, 0.9, 0.9, 0.9);

  protected final WoodType type;

  public BlockWoodSapling(WoodType type) {
    this.type = type;

    getSettings()
      //.ignoresProperties(STAGE_2)
      .tile(TileWoodSapling.class)
      .sound(SoundType.PLANT)
      .itemBlock(ItemBlockWoodSapling::new)
      .hardness(0.0F)
      .fireInfo(5, 20)
      .addOreDict("sapling");

    setDefaultState(blockState.getBaseState()
      .withProperty(STAGE_2, 0));
  }

//  @Override
//  public IBlockState getStateFromMeta(int meta) {
//    return this.getDefaultState().withProperty(STAGE_5, meta);
//  }
//
//  @Override
//  public int getMetaFromState(IBlockState state) {
//    return state.getValue(STAGE_5);
//  }

  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    TileUtils.getTile(worldIn, pos, TileWoodSapling.class).ifPresent(TileWoodSapling::resetCounter);
    super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
  }

//  @Override
//  protected BlockStateContainer createBlockState() {
//    return new BlockStateContainer(this, STAGE_5);
//  }

  @Override
  public EnumOffsetType getOffsetType() {
    return EnumOffsetType.XZ;
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    super.addInformation(stack, worldIn, tooltip, flagIn);
    // TODO
    if (GuiScreen.isShiftKeyDown()) {
      tooltip.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.climate_info"));
      tooltip.add(TextFormatting.BLUE + I18n.format("tfc.tooltip.climate_info_rainfall", (int) type.getMinRain(), (int) type.getMaxRain()));
      tooltip.add(TextFormatting.GOLD + I18n.format("tfc.tooltip.climate_info_temperature", String.format("%.1f", type.getMinRain()), String.format("%.1f", type.getMaxRain())));
    } else {
      tooltip.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.hold_shift_for_climate_info"));
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
  public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, IBlockState blockState) {
    return false;
  }

  @Override
  public void grow(World world, Random random, BlockPos pos, IBlockState state) {
    TileUtils.getTile(world, pos, TileWoodSapling.class).ifPresent(tile -> {
      long days = tile.getTicksSinceUpdate() / ICalendar.TICKS_IN_DAY;
      if (days > this.type.getMinGrowthTime()) {
        this.type.makeTree(world, pos, random, false);
      }
    });

  }

  @Override
  public GrowthStatus getGrowingStatus(IBlockState state, World world, BlockPos pos) {
    return GrowthStatus.GROWING;
  }


  @Override
  public @Nullable TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileWoodSapling();
  }

  @Override
  public IBlockColor getBlockColor() {
    return GrassColorHelper::computeGrassColor;
  }


  @Override
  public IItemColor getItemColor() {
    return (s, i) -> this.getBlockColor().colorMultiplier(this.getDefaultState(), null, null, i);
  }


}
