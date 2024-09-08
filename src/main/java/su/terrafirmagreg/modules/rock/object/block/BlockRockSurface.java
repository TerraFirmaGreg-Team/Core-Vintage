package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;
import su.terrafirmagreg.modules.rock.init.ItemsRock;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.Nullable;

import java.util.Random;

import static su.terrafirmagreg.data.Properties.HORIZONTAL;

@SuppressWarnings("deprecation")
public class BlockRockSurface extends BlockRock {

  private static final AxisAlignedBB STONE_AABB = new AxisAlignedBB(
          2.0 / 16.0, 0.0 / 16.0, 2.0 / 16.0,
          14.0 / 16.0, 2.0 / 16.0, 14.0 / 16.0
  );

  public BlockRockSurface(RockBlockVariant variant, RockType type) {
    super(variant, type);

    getSettings()
            .hardness(0.1f)
            .useNeighborBrightness()
            .replaceable()
            .nonCube();

    setDefaultState(blockState.getBaseState()
            .withProperty(HORIZONTAL, EnumFacing.NORTH));
  }

  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    if (!worldIn.isSideSolid(pos.down(), EnumFacing.UP)) {
      worldIn.destroyBlock(pos, false);
    }
  }

  @Override
  public boolean addLandingEffects(IBlockState state, WorldServer world, BlockPos pos, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles) {
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

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY,
          float hitZ) {
    var itemStack = new ItemStack(ItemsRock.LOOSE.get(type));

    if (player.addItemStackToInventory(itemStack)) {
      world.setBlockToAir(pos);

      player.swingArm(EnumHand.MAIN_HAND);
      player.playSound(SoundEvents.ENTITY_ITEMFRAME_REMOVE_ITEM, 1.0f, 1.0f);
    }

    return true;
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return ItemsRock.LOOSE.get(type);
  }

  @Override
  public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    drops.add(new ItemStack(ItemsRock.LOOSE.get(type)));
  }

  @Override
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    return new ItemStack(ItemsRock.LOOSE.get(type));
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return STONE_AABB;
  }

  @Override
  public @Nullable AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    return STONE_AABB;
  }

  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    IBlockState state = worldIn.getBlockState(pos);
    IBlockState stateDown = worldIn.getBlockState(pos.down());

    return stateDown.isSideSolid(worldIn, pos, EnumFacing.DOWN) && state.getBlock().equals(Blocks.AIR);
  }

  @Override
  public Block.EnumOffsetType getOffsetType() {
    return Block.EnumOffsetType.XZ;
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(HORIZONTAL, EnumFacing.byHorizontalIndex(meta));
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(HORIZONTAL).getHorizontalIndex();
  }

  @Override
  public BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, HORIZONTAL);
  }
}
