package su.terrafirmagreg.modules.device.object.block;

import su.terrafirmagreg.api.base.object.block.spi.BaseBlockContainer;
import su.terrafirmagreg.api.data.NBTTags;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.framework.network.spi.GuiHandler;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.device.object.tile.TilePowderKeg;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.BoolProp.LIT;
import static su.terrafirmagreg.api.data.Properties.BoolProp.SEALED;

import net.dries007.tfc.objects.blocks.BlockTorchTFC;

/**
 * Powderkeg is an inventory that preserves the contents when sealed It can be picked up and keeps it's inventory Sealed state is stored in a block state property, and cached in the TE (for gui purposes)
 */
@SuppressWarnings("deprecation")
public class BlockPowderKeg extends BaseBlockContainer {

  private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);

  public BlockPowderKeg() {
    super(Settings.of(Material.WOOD));

    getSettings()
      .registryKey("powderkeg")
      .sound(SoundType.WOOD)
      .renderLayer(BlockRenderLayer.CUTOUT)
      .hardness(2F)
      .nonCube()
      .randomTicks()
      .capability(getCapabilitySize());

    setDefaultState(blockState.getBaseState()
      .withProperty(LIT, false)
      .withProperty(SEALED, false));
  }

  /**
   * Used to update the keg seal state and the TE, in the correct order
   */
  public static void togglePowderKegSeal(World world, BlockPos pos) {
    TileUtils.getTile(world, pos, TilePowderKeg.class).ifPresent(tile -> {
      IBlockState state = world.getBlockState(pos);
      boolean previousSealed = state.getValue(SEALED);
      world.setBlockState(pos, state.withProperty(SEALED, !previousSealed));
      tile.setSealed(!previousSealed);
    });
  }

  private CapabilityProviderSize getCapabilitySize() {

    return new CapabilityProviderSize() {
      public Weight getWeight(ItemStack stack) {
        return Weight.VERY_HEAVY;
      }

      public Size getSize(ItemStack stack) {
        return stack.getTagCompound() == null ? Size.VERY_LARGE : Size.HUGE; // Causes overburden if sealed
      }

      @Override
      public boolean canStack(ItemStack stack) {
        return stack.getTagCompound() == null;
      }
    };
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    NBTTagCompound nbt = stack.getTagCompound();
    if (nbt != null) {
      ItemStackHandler stackHandler = new ItemStackHandler();
      stackHandler.deserializeNBT(nbt.getCompoundTag(NBTTags.INVENTORY_TAG));
      int count = 0;
      int firstSlot = -1;
      for (int i = 0; i < stackHandler.getSlots(); i++) {
        if (firstSlot < 0 && !stackHandler.getStackInSlot(i).isEmpty()) {
          firstSlot = i;
        }
        count += stackHandler.getStackInSlot(i).getCount();
      }

      if (count == 0) {
        tooltip.add(I18n.format(ModUtils.localize("tooltip", "device.powderkeg.empty")));
      } else {
        ItemStack itemStack = stackHandler.getStackInSlot(firstSlot);
        tooltip.add(I18n.format(ModUtils.localize("tooltip", "device.powderkeg.amount"), count, itemStack.getItem().getItemStackDisplayName(itemStack)));
      }
    }
  }

  @Override
  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.MODEL;
  }

  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    TileUtils.getTile(worldIn, pos, TilePowderKeg.class).ifPresent(tile -> {
      if (!tile.isLit()) {
        super.breakBlock(worldIn, pos, state);
      }
    });
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(SEALED, meta == 1);
  }

  // Cannot use onExplosionDestroy like TNT because all state has already been lost at that point.

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(SEALED) ? 1 : 0;
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return BOUNDING_BOX;
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rng) {
    if (!state.getValue(LIT)) {
      return;
    }

    TileUtils.getTile(world, pos, TilePowderKeg.class).ifPresent(tile -> {
      int fuse = tile.getFuse();
      if (rng.nextInt(6) == 0 && fuse > 20) {
        world.playSound(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F,
          SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F,
          rng.nextFloat() * 1.3F + 0.3F / fuse, false);
      }
      world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
        pos.getX() + 0.625, pos.up().getY() + 0.125, pos.getZ() + 0.375,
        0.0D, 1.0D + 1.0D / fuse, 0.0D);
    });
  }

  /**
   * Called after a player destroys this Block - the position pos may no longer hold the state indicated.
   */
  public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state) {
    trigger(worldIn, pos, state, null);
  }

  /**
   * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid block,
   * etc.
   */
  @Override
  public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
    if (world.isBlockPowered(pos) || world.getBlockState(fromPos).getMaterial() == Material.FIRE) {
      onPlayerDestroy(world, pos, state.withProperty(LIT, true));
    } else if (state.getValue(LIT) && pos.up().equals(fromPos) && world.getBlockState(fromPos).getMaterial() == Material.WATER) {
      TileUtils.getTile(world, pos, TilePowderKeg.class).ifPresent(tile -> {
        world.setBlockState(pos, state.withProperty(LIT, false));
        tile.setLit(false);
      });
    } // do not care otherwise, as canStay may be violated by an explosion, which we want to trigger off of
  }

  public void trigger(World worldIn, BlockPos pos, IBlockState state, @Nullable EntityLivingBase igniter) {
    if (!worldIn.isRemote) {
      TileUtils.getTile(worldIn, pos, TilePowderKeg.class).ifPresent(tile -> {
        // lit state set before called
        if (state.getValue(SEALED) && state.getValue(LIT) && tile.getStrength() > 0) {
          worldIn.setBlockState(pos, state);
          tile.setLit(true);
          tile.setIgniter(igniter);
        }
      });

    }
  }

  /**
   * Called after the block is set in the Chunk data, but before the Tile Entity is set
   */
  @Override
  public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    super.onBlockAdded(worldIn, pos, state);
    if (worldIn.isBlockPowered(pos)) {
      onPlayerDestroy(worldIn, pos, state.withProperty(LIT, true));
    }
  }

  @Override
  public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune) {
    if (state.getBlock() == this && !state.getValue(SEALED)) {
      super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);
    }
  }

  @Override
  public boolean canPlaceBlockAt(World world, BlockPos pos) {
    return canStay(world, pos);
  }

  /**
   * Called when the block is right clicked by a player.
   */
  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (!worldIn.isRemote) {
      ItemStack heldItem = playerIn.getHeldItem(hand);
      TileUtils.getTile(worldIn, pos, TilePowderKeg.class).ifPresent(tile -> {
        if (heldItem.isEmpty() && state.getValue(LIT)) {
          worldIn.setBlockState(pos, state.withProperty(LIT, false));
          tile.setLit(false);
        } else if (heldItem.isEmpty() && playerIn.isSneaking()) {
          worldIn.playSound(null, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1.0F, 0.85F);
          togglePowderKegSeal(worldIn, pos);
        } else if (state.getValue(SEALED) && BlockTorchTFC.canLight(heldItem)) {
          if (!state.getValue(LIT)) {

            trigger(worldIn, pos, state.withProperty(LIT, true), playerIn);

            if (heldItem.getItem().isDamageable()) {
              heldItem.damageItem(1, playerIn);
            } else if (!playerIn.capabilities.isCreativeMode) {
              heldItem.shrink(1);
            }
          }
        } else {
          GuiHandler.openGui(worldIn, pos, playerIn);
        }
      });
    }
    return true;
  }

  /**
   * Called When an Entity Collided with the Block
   */
  @Override
  public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
    if (!worldIn.isRemote && entityIn instanceof EntityArrow entityarrow) {

      if (entityarrow.isBurning()) {
        trigger(worldIn, pos, worldIn.getBlockState(pos).withProperty(LIT, true), entityarrow.shootingEntity instanceof EntityLivingBase entityLivingBase ? entityLivingBase : null);
      }
    }
  }

  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    // If the keg was sealed, then copy the contents from the item
    if (!worldIn.isRemote) {
      NBTTagCompound nbt = stack.getTagCompound();
      if (nbt != null) {
        TileUtils.getTile(worldIn, pos, TilePowderKeg.class).ifPresent(tile -> {
          worldIn.setBlockState(pos, state.withProperty(SEALED, true));
          tile.readFromItemTag(nbt);
        });
      }
    }
  }

  /**
   * Return whether this block can drop from an explosion. STATELESS! :( Would drop from explosion if unsealed, but can't tell.
   * <p>
   * ^^^ See {@link this#dropBlockAsItemWithChance(World, BlockPos, IBlockState, float, int)}
   */
  @Override
  public boolean canDropFromExplosion(Explosion explosionIn) {
    return true;
  }

  @Override
  public BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, SEALED, LIT);
  }


  @Override
  public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    // Only drop the keg if it's not sealed, since the keg with contents will be already dropped by the TE
    if (!state.getValue(SEALED)) {
      super.getDrops(drops, world, pos, state, fortune);
    }
  }

  /**
   * Called when this Block is destroyed by an Explosion
   */
  @Override
  public void onBlockExploded(World worldIn, BlockPos pos, Explosion explosionIn) {
    TileUtils.getTile(worldIn, pos, TilePowderKeg.class).ifPresent(tile -> {
      // explode even if not sealed cause gunpowder
      if (!worldIn.isRemote && tile.getStrength() > 0) {
        trigger(worldIn, pos, worldIn.getBlockState(pos).withProperty(SEALED, true).withProperty(LIT, true), null);
      }
    });
    super.onBlockExploded(worldIn, pos, explosionIn);
  }

  @Override
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    return TileUtils.getTile(world, pos, TilePowderKeg.class).map(tile -> tile.getItemStack(state)).orElse(new ItemStack(state.getBlock()));

  }

  private boolean canStay(IBlockAccess world, BlockPos pos) {
    boolean solid = world.getBlockState(pos.down()).getBlockFaceShape(world, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID;
    return solid || world.getBlockState(pos.down()).getBlock() instanceof BlockPowderKeg;
  }

  @Override
  public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
    return BlockFaceShape.UNDEFINED;
  }

  @Override
  public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
    return state.getValue(LIT) ? 14 : 0;
  }

  @Override
  public Class<TilePowderKeg> getTileClass() {
    return TilePowderKeg.class;
  }

  @Override
  public @Nullable TilePowderKeg createNewTileEntity(World worldIn, int meta) {
    return new TilePowderKeg();
  }


}
