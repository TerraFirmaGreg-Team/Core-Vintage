package su.terrafirmagreg.modules.agriculture.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockBush;
import su.terrafirmagreg.api.data.DamageSources;
import su.terrafirmagreg.api.data.enums.EnumBushSize;
import su.terrafirmagreg.api.helper.BlockHelper;
import su.terrafirmagreg.api.registry.provider.IProviderTile;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.agriculture.ConfigAgriculture;
import su.terrafirmagreg.modules.agriculture.api.types.berrybush.BerryBushType;
import su.terrafirmagreg.modules.agriculture.api.types.berrybush.spi.IBerryBushBlock;
import su.terrafirmagreg.modules.agriculture.object.tile.TileBerryBush;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;
import su.terrafirmagreg.modules.core.feature.climate.Climate;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.BoolProp.FRUITING;

@Getter
@SuppressWarnings("deprecation")
public class BlockBerryBush extends BaseBlockBush implements IBerryBushBlock, IProviderTile {

  private static final AxisAlignedBB SMALL_SIZE_AABB = new AxisAlignedBB(0D, 0.0D, 0, 1D, 0.25D, 1D);
  private static final AxisAlignedBB MEDIUM_SIZE_AABB = new AxisAlignedBB(0D, 0.0D, 0, 1D, 0.5D, 1D);

  protected final BerryBushType type;

  public BlockBerryBush(BerryBushType type) {
    super(Settings.of(Material.PLANTS));

    this.type = type;

    getSettings()
      .registryKey("agriculture/berry_bush/" + type)
      .hardness(1.0F)
      .randomTicks()
      .sound(SoundType.PLANT)
      .renderLayer(BlockRenderLayer.CUTOUT_MIPPED)
      .nonOpaque()
      .noSuffocating();

    BlockUtils.setFireInfo(this, 30, 60);
    setDefaultState(blockState.getBaseState().withProperty(FRUITING, false));
  }

  @Override
  public boolean isFullCube(IBlockState state) {
    return type.getSize() == EnumBushSize.LARGE;
  }

  @Override
  public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
    if (type.getSize() == EnumBushSize.LARGE && face == EnumFacing.UP) {
      return BlockFaceShape.SOLID;
    } else {
      return BlockFaceShape.UNDEFINED;
    }
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(FRUITING, meta == 1);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(FRUITING) ? 1 : 0;
  }

  @Override
  public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
    return true;
  }

  @Override
  public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
    if (world.isRemote) {
      return;
    }
    TileUtils.getTile(world, pos, TileBerryBush.class).ifPresent(tile -> {
      float temp = Climate.getActualTemp(world, pos);
      float rainfall = ProviderChunkData.getRainfall(world, pos);
      long hours = tile.getTicksSinceUpdate() / ICalendar.TICKS_IN_HOUR;
      if (hours > (type.getGrowthTime() * ConfigAgriculture.BLOCKS.BERRY_BUSH.growthTimeModifier) && type.isValidForGrowth(temp, rainfall)) {
        if (type.isHarvestMonth(Calendar.CALENDAR_TIME.getMonthOfYear())) {
          //Fruiting
          world.setBlockState(pos, world.getBlockState(pos).withProperty(FRUITING, true));
        }
        tile.resetCounter();
      }
    });
  }

  @Override
  public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    TileUtils.getTile(worldIn, pos, TileBerryBush.class).ifPresent(TileBerryBush::resetCounter);
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (worldIn.isRemote) {return true;}
    if (worldIn.getBlockState(pos).getValue(FRUITING)) {
      ItemHandlerHelper.giveItemToPlayer(playerIn, type.getFoodDrop());
      worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(FRUITING, false));
      TileUtils.getTile(worldIn, pos, TileBerryBush.class).ifPresent(TileBerryBush::resetCounter);
    }
    return false;
  }

  @Override
  public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
    if (!(entityIn instanceof EntityPlayer entityPlayer && entityPlayer.isCreative())) {
      // Entity motion is reduced (like leaves).
      entityIn.motionX *= ConfigAgriculture.BLOCKS.BERRY_BUSH.movementModifier;
      if (entityIn.motionY < 0) {
        entityIn.motionY *= ConfigAgriculture.BLOCKS.BERRY_BUSH.movementModifier;
      }
      entityIn.motionZ *= ConfigAgriculture.BLOCKS.BERRY_BUSH.movementModifier;
      if (type.isSpiky() && entityIn instanceof EntityLivingBase) {
        entityIn.attackEntityFrom(DamageSources.BERRYBUSH, 1.0F);
      }
    }
  }

  @Override
  public BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, FRUITING);
  }

  @Override
  public boolean canPlaceBlockAt(World worldIn, @NotNull BlockPos pos) {
    if (super.canPlaceBlockAt(worldIn, pos)) {
      return canStay(worldIn, pos);
    }
    return false;
  }

  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    if (!canStay(worldIn, pos)) {
      worldIn.destroyBlock(pos, true);
    }
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return switch (type.getSize()) {
      case SMALL -> SMALL_SIZE_AABB;
      case MEDIUM -> MEDIUM_SIZE_AABB;
      default -> FULL_BLOCK_AABB;
    };
  }

  private boolean canStay(IBlockAccess world, BlockPos pos) {
    IBlockState below = world.getBlockState(pos.down());
    if (type.getSize() == EnumBushSize.LARGE && below.getBlock() instanceof BlockBerryBush blockBerryBush && blockBerryBush.type == this.type) {
      return BlockHelper.isGrowableSoil(world.getBlockState(pos.down(2))); // Only stack once
    }
    return BlockHelper.isGrowableSoil(below);
  }

  @Override
  public GrowthStatus getGrowingStatus(IBlockState state, World world, BlockPos pos) {
    float temp = Climate.getActualTemp(world, pos);
    float rainfall = ProviderChunkData.getRainfall(world, pos);
    boolean canGrow = type.isValidForGrowth(temp, rainfall);
    if (state.getValue(FRUITING)) {
      return GrowthStatus.FULLY_GROWN;
    } else if (canGrow && type.isHarvestMonth(Calendar.CALENDAR_TIME.getMonthOfYear())) {
      return GrowthStatus.GROWING;
    }
    return canGrow ? GrowthStatus.CAN_GROW : GrowthStatus.NOT_GROWING;
  }

  @Override
  public @Nullable TileBerryBush createNewTileEntity(World worldIn, int meta) {
    return new TileBerryBush();
  }

  @Override
  public Class<TileBerryBush> getTileClass() {
    return TileBerryBush.class;
  }
}
