package su.terrafirmagreg.modules.device.object.block;

import com.google.common.collect.ImmutableMap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.Nullable;

import su.terrafirmagreg.api.base.object.block.spi.BaseBlock;
import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.framework.registry.api.provider.IProviderBlockState;
import su.terrafirmagreg.framework.registry.api.provider.IProviderTile;
import su.terrafirmagreg.modules.device.client.render.TESRPitKiln;
import su.terrafirmagreg.modules.device.object.item.ItemFireStarter;
import su.terrafirmagreg.modules.device.object.tile.TilePitKiln;

import java.util.Random;

import static net.dries007.tfc.objects.blocks.BlockPlacedItem.PLACED_ITEM_AABB;
import static su.terrafirmagreg.api.data.Properties.BoolProp.FULL;
import static su.terrafirmagreg.api.data.Properties.BoolProp.LIT;

@SuppressWarnings("deprecation")
public class BlockPitKiln extends BaseBlock implements IProviderTile, IProviderBlockState {

  private static final AxisAlignedBB[] AABB_LEVELS = new AxisAlignedBB[]{
    PLACED_ITEM_AABB,
    new AxisAlignedBB(0, 0, 0, 1, 0.0625, 1),
    new AxisAlignedBB(0, 0, 0, 1, 0.125, 1),
    new AxisAlignedBB(0, 0, 0, 1, 0.1875, 1),
    new AxisAlignedBB(0, 0, 0, 1, 0.25, 1),
    new AxisAlignedBB(0, 0, 0, 1, 0.3125, 1),
    new AxisAlignedBB(0, 0, 0, 1, 0.375, 1),
    new AxisAlignedBB(0, 0, 0, 1, 0.4375, 1),
    new AxisAlignedBB(0, 0, 0, 1, 0.5, 1),
    new AxisAlignedBB(0, 0, 0, 1, 0.75, 1),
    FULL_BLOCK_AABB
  };

  public BlockPitKiln() {
    super(Settings.of(Material.CIRCUITS));

    getSettings()
      .registryKey("pit_kiln")
      .nonFullCube()
      .noItemBlock()
      .nonOpaque()
      .harvestLevel(ToolClasses.AXE, 0)
      .hardness(0.5f);

    setDefaultState(getBlockState().getBaseState()
      .withProperty(FULL, false)
      .withProperty(LIT, false));
  }

  @Override
  public boolean isTopSolid(IBlockState state) {
    return state.getValue(FULL);
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(LIT, (meta & 1) > 0).withProperty(FULL, (meta & 2) > 0);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return (state.getValue(LIT) ? 1 : 0) + (state.getValue(FULL) ? 2 : 0);
  }

  @Override
  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return TileUtils.getTile(source, pos, TilePitKiln.class).map(tile -> {
      int height = tile.getStrawCount();
      if (tile.getLogCount() > 4) {
        height = 10; // Full block
      } else if (tile.getLogCount() > 0) {
        height = 9; // 75% of block
      }
      return AABB_LEVELS[height];
    }).orElse(PLACED_ITEM_AABB);
  }

  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    TileUtils.getTile(worldIn, pos, TilePitKiln.class).ifPresent(tile -> {
      if (blockIn == Blocks.FIRE) {
        tile.tryLight();
      }
      if (!worldIn.isSideSolid(pos.down(), EnumFacing.UP)) {
        if (tile.isLit()) {
          tile.emptyFuelContents();
        }
        worldIn.destroyBlock(pos, true);
      }
    });
    super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
  }

  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    TileUtils.getTile(worldIn, pos, TilePitKiln.class).ifPresent(tile -> tile.onBreakBlock(worldIn, pos, state));
    super.breakBlock(worldIn, pos, state);
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return Items.AIR;
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    return TileUtils.getTile(worldIn, pos, TilePitKiln.class).map(tile -> {
      // Skip interacting if using a fire starter (wait for fire in #neighborChanged)
      if (ItemFireStarter.canIgnite(playerIn.getHeldItem(hand))) {
        return false;
      }
      return tile.onRightClick(playerIn, playerIn.getHeldItem(hand), hitX < 0.5, hitZ < 0.5);
    }).orElse(false);
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, FULL, LIT);
  }

  @Override
  public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
    return state.getActualState(world, pos).getValue(FULL);
  }

  @Override
  public boolean isBurning(IBlockAccess world, BlockPos pos) {
    return BlockUtils.getActualState(world, pos).getValue(LIT);
  }

  @Override
  public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
    return 0;
  }

  @Override
  public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
    return world.getBlockState(pos).getActualState(world, pos).getValue(LIT) ? 120 : 0; // Twice as much as the highest vanilla level (60)
  }

  @Override
  public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
    return BlockUtils.getActualState(world, pos).getValue(LIT);
  }

  @Override
  public boolean addLandingEffects(IBlockState state, WorldServer worldObj, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles) {
    return true;
  }

  @Override
  public boolean addRunningEffects(IBlockState state, World world, BlockPos pos, Entity entity) {
    return true;
  }

  @SideOnly(Side.CLIENT)
  @Override
  public boolean addHitEffects(IBlockState state, World worldObj, RayTraceResult target, ParticleManager manager) {
    return true;
  }

  @SideOnly(Side.CLIENT)
  @Override
  public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
    return true;
  }

  @Nullable
  @Override
  public PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EntityLiving entity) {
    return state.getValue(LIT) && (entity == null || !entity.isImmuneToFire()) ? PathNodeType.DAMAGE_FIRE : null;
  }

  @Override
  public @Nullable TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TilePitKiln();
  }

  @Override
  public Class<TilePitKiln> getTileClass() {
    return TilePitKiln.class;
  }

  @Override
  public TileEntitySpecialRenderer<?> getTileRenderer() {
    return new TESRPitKiln();
  }

  @Override
  public IStateMapper getStateMapper() {
    return blockIn -> ImmutableMap.of(this.getDefaultState(), new ModelResourceLocation(ModUtils.id("empty")));
  }
}
