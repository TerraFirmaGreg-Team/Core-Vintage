package su.terrafirmagreg.modules.device.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockHorse;
import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.api.registry.provider.IProviderBlockColor;
import su.terrafirmagreg.api.registry.provider.IProviderTile;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.device.client.render.TESRQuernHorse;
import su.terrafirmagreg.modules.device.object.tile.TileQuernHorse;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import se.gory_moon.horsepower.util.Localization;
import se.gory_moon.horsepower.util.color.ColorGetter;
import se.gory_moon.horsepower.util.color.Colors;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import static su.terrafirmagreg.api.data.Properties.BoolProp.FILLED;

@SuppressWarnings("deprecation")
public class BlockQuernHorse extends BaseBlockHorse implements IProviderTile, IProviderBlockColor {


  private static final AxisAlignedBB COLLISION_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 8D / 16D, 1.0D);
  private static final AxisAlignedBB BOUNDING_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 13D / 16D, 1.0D);

  public BlockQuernHorse() {
    super(Settings.of(Material.ROCK));

    getSettings()
      .registryKey("device/quern/horse")
      .sound(SoundType.STONE)
      .nonFullCube()
      .nonOpaque()
      .size(Size.LARGE)
      .weight(Weight.HEAVY)
      .hardness(1.5F)
      .resistance(10F)
      .harvestLevel(ToolClasses.PICKAXE, 1);
  }

  public static void setState(boolean filled, World world, BlockPos pos) {
    var iBlockState = world.getBlockState(pos);

    world.setBlockState(pos, iBlockState.withProperty(FILLED, filled), 3);
    var tile = TileUtils.getTile(world, pos, TileQuernHorse.class);
    tile.ifPresent(tileQuernHorse -> {
      tileQuernHorse.validate();
      world.setTileEntity(pos, tileQuernHorse);
      tileQuernHorse.validate();
    });
  }

  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    var tile = TileUtils.getTile(worldIn, pos, TileQuernHorse.class);
    tile.ifPresent(tileQuernHorse -> tileQuernHorse.onBreakBlock(worldIn, pos, state));
    worldIn.updateComparatorOutputLevel(pos, this);
    super.breakBlock(worldIn, pos, state);
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(FILLED, meta == 1);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(FILLED) ? 1 : 0;
  }

  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return BOUNDING_AABB;
  }

  @Override
  public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    if (!worldIn.isRemote) {
      worldIn.setBlockState(pos, state.withProperty(FILLED, state.getValue(FILLED)), 2);
    }
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, FILLED);
  }

  @Override
  public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced) {
    tooltip.add(Localization.ITEM.HORSE_GRINDSTONE.SIZE.translate(Colors.WHITE.toString(), Colors.LIGHTGRAY.toString()));
    tooltip.add(Localization.ITEM.HORSE_GRINDSTONE.LOCATION.translate(Colors.WHITE.toString(), Colors.LIGHTGRAY.toString()));
    tooltip.add(Localization.ITEM.HORSE_GRINDSTONE.USE.translate());
  }

  @Nullable
  @Override
  public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    return COLLISION_AABB;
  }

  @Override
  public Class<TileQuernHorse> getTileClass() {
    return TileQuernHorse.class;
  }

  @Override
  public @Nullable TileEntitySpecialRenderer<?> getTileRenderer() {
    return new TESRQuernHorse();
  }

  @Override
  public @Nullable TileQuernHorse createNewTileEntity(World worldIn, int meta) {
    return new TileQuernHorse();
  }

  @Override
  public IBlockColor getBlockColor() {
    return (state, worldIn, pos, tintIndex) -> {
      var tile = TileUtils.getTile(worldIn, pos, TileQuernHorse.class);
      return tile.map(tileQuernHorse -> {
        ItemStack outputStack = tileQuernHorse.getStackInSlot(1);
        ItemStack secondaryStack = tileQuernHorse.getStackInSlot(2);
        if (outputStack.getCount() < secondaryStack.getCount()) {
          outputStack = secondaryStack;
        }
        if (!OreDictionary.itemMatches(tileQuernHorse.renderStack, outputStack, true)) {
          tileQuernHorse.renderStack = outputStack;
          if (!outputStack.isEmpty()) {
            tileQuernHorse.grindColor = ColorGetter.getColors(outputStack, 2).get(0);
          } else {
            tileQuernHorse.grindColor = null;
          }
          tileQuernHorse.renderStack = outputStack;
        }

        if (tileQuernHorse.grindColor != null) {
          return tileQuernHorse.grindColor.getRGB();
        }
        return -1;
      }).orElse(-1);
    };
  }
}
