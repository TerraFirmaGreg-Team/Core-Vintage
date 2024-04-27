package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.spi.block.BlockBase;
import su.terrafirmagreg.api.spi.tile.ITEBlock;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.device.objects.tiles.TEPowderKeg;

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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
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


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.blocks.BlockTorchTFC;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

import static su.terrafirmagreg.api.util.PropertyUtils.LIT;
import static su.terrafirmagreg.api.util.PropertyUtils.SEALED;

/**
 * Powderkeg is an inventory that preserves the contents when sealed It can be picked up and keeps it's inventory Sealed state is stored in a block
 * state property, and cached in the TE (for gui purposes)
 */

public class BlockPowderKeg extends BlockBase implements ITEBlock {

    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);

    public BlockPowderKeg() {
        super(Material.WOOD);
        setSoundType(SoundType.WOOD);
        setHardness(2F);
        setTickRandomly(true);

        setDefaultState(blockState.getBaseState()
                .withProperty(LIT, false)
                .withProperty(SEALED, false));
    }

    /**
     * Used to update the keg seal state and the TE, in the correct order
     */
    public static void togglePowderKegSeal(World world, BlockPos pos) {
        var tile = TileUtils.getTile(world, pos, TEPowderKeg.class);
        if (tile != null) {
            IBlockState state = world.getBlockState(pos);
            boolean previousSealed = state.getValue(SEALED);
            world.setBlockState(pos, state.withProperty(SEALED, !previousSealed));
            tile.setSealed(!previousSealed);
        }
    }

    public void trigger(World worldIn, BlockPos pos, IBlockState state, @Nullable EntityLivingBase igniter) {
        if (!worldIn.isRemote) {
            var tile = TileUtils.getTile(worldIn, pos, TEPowderKeg.class);
            if (tile != null && state.getValue(SEALED) && state.getValue(LIT) && tile.getStrength() > 0) //lit state set before called
            {
                worldIn.setBlockState(pos, state);
                tile.setLit(true);
                tile.setIgniter(igniter);
            }
        }
    }

    @Override
    public @NotNull Size getSize(ItemStack stack) {
        return stack.getTagCompound() == null ? Size.VERY_LARGE : Size.HUGE; // Causes overburden if sealed
    }

    @Override
    public @NotNull Weight getWeight(@NotNull ItemStack stack) {
        return Weight.VERY_HEAVY; // Stacksize = 1
    }

    // Cannot use onExplosionDestroy like TNT because all state has already been lost at that point.

    @Override
    public boolean canStack(@NotNull ItemStack stack) {
        return stack.getTagCompound() == null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isTopSolid(@NotNull IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullBlock(@NotNull IBlockState state) {
        return false;
    }

    @Override
    @NotNull
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(SEALED, meta == 1);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(SEALED) ? 1 : 0;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isBlockNormalCube(@NotNull IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isNormalCube(@NotNull IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(@NotNull IBlockState state) {
        return false;
    }

    @NotNull
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(@NotNull IBlockState state, @NotNull IBlockAccess source, @NotNull BlockPos pos) {
        return BOUNDING_BOX;
    }

    @SuppressWarnings("deprecation")
    @Override
    @NotNull
    public BlockFaceShape getBlockFaceShape(@NotNull IBlockAccess worldIn, @NotNull IBlockState state, @NotNull BlockPos pos,
                                            @NotNull EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(@NotNull IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState state, @NotNull World world, @NotNull BlockPos pos, @NotNull Random rng) {
        if (!state.getValue(LIT))
            return;

        var tile = TileUtils.getTile(world, pos, TEPowderKeg.class);
        if (tile != null) {
            int fuse = tile.getFuse();
            if (rng.nextInt(6) == 0 && fuse > 20) {
                world.playSound(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F,
                        rng.nextFloat() * 1.3F + 0.3F / fuse, false);
            }
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.625, pos.up()
                    .getY() + 0.125, pos.getZ() + 0.375, 0.0D, 1.0D + 1.0D / fuse, 0.0D);
        }
    }

    /**
     * Called after a player destroys this Block - the position pos may no longer hold the state indicated.
     */
    public void onPlayerDestroy(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state) {
        trigger(worldIn, pos, state, null);
    }

    /**
     * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor change. Cases may include
     * when redstone power is updated, cactus blocks popping off due to a neighboring solid block, etc.
     */
    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(@NotNull IBlockState state, World world, @NotNull BlockPos pos, @NotNull Block blockIn, @NotNull BlockPos fromPos) {
        if (world.isBlockPowered(pos) || world.getBlockState(fromPos).getMaterial() == Material.FIRE) {
            onPlayerDestroy(world, pos, state.withProperty(LIT, true));
        } else if (state.getValue(LIT) && pos.up().equals(fromPos) && world.getBlockState(fromPos)
                .getMaterial() == Material.WATER) {
            var tile = TileUtils.getTile(world, pos, TEPowderKeg.class);
            if (tile != null) {
                world.setBlockState(pos, state.withProperty(LIT, false));
                tile.setLit(false);
            }
        } // do not care otherwise, as canStay may be violated by an explosion, which we want to trigger off of
    }

    /**
     * Called after the block is set in the Chunk data, but before the Tile Entity is set
     */
    public void onBlockAdded(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);
        if (worldIn.isBlockPowered(pos)) {
            onPlayerDestroy(worldIn, pos, state.withProperty(LIT, true));
        }
    }

    @Override
    public void breakBlock(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state) {
        var tile = TileUtils.getTile(worldIn, pos, TEPowderKeg.class);
        if (tile != null && !tile.isLit()) {
            tile.onBreakBlock(worldIn, pos, state);
            super.breakBlock(worldIn, pos, state);
        }
    }

    @Override
    @NotNull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean canPlaceBlockAt(@NotNull World world, @NotNull BlockPos pos) {
        return canStay(world, pos);
    }

    /**
     * Called when the block is right clicked by a player.
     */
    @Override
    public boolean onBlockActivated(World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityPlayer playerIn,
                                    @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            ItemStack heldItem = playerIn.getHeldItem(hand);
            var tile = TileUtils.getTile(worldIn, pos, TEPowderKeg.class);
            if (tile != null) {
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
                    GuiHandler.openGui(worldIn, pos, playerIn, GuiHandler.Type.POWDERKEG);
                }
            }
        }
        return true;
    }

    /**
     * Called When an Entity Collided with the Block
     */
    public void onEntityCollision(World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull Entity entityIn) {
        if (!worldIn.isRemote && entityIn instanceof EntityArrow entityarrow) {

            if (entityarrow.isBurning()) {
                trigger(worldIn, pos, worldIn.getBlockState(pos).withProperty(LIT, true),
                        entityarrow.shootingEntity instanceof EntityLivingBase ? (EntityLivingBase) entityarrow.shootingEntity : null);
            }
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityLivingBase placer,
                                @NotNull ItemStack stack) {
        // If the keg was sealed, then copy the contents from the item
        if (!worldIn.isRemote) {
            NBTTagCompound nbt = stack.getTagCompound();
            if (nbt != null) {
                var tile = TileUtils.getTile(worldIn, pos, TEPowderKeg.class);
                if (tile != null) {
                    worldIn.setBlockState(pos, state.withProperty(SEALED, true));
                    tile.readFromItemTag(nbt);
                }
            }
        }
    }

    /**
     * Return whether this block can drop from an explosion. STATELESS! :( Would drop from explosion if unsealed, but can't tell.
     * <p>
     * ^^^ See {@link this#dropBlockAsItemWithChance(World, BlockPos, IBlockState, float, int)}
     */
    @Override
    public boolean canDropFromExplosion(@NotNull Explosion explosionIn) {
        return true;
    }

    @Override
    public void dropBlockAsItemWithChance(@NotNull World world, @NotNull BlockPos pos, IBlockState state, float chance, int fortune) {
        if (state.getBlock() == this && !state.getValue(SEALED)) {
            super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);
        }
    }

    @Override
    @NotNull
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, SEALED, LIT);
    }

    @Override
    public int getLightValue(IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos) {
        return state.getValue(LIT) ? 14 : 0;
    }

    @Override
    public boolean isNormalCube(@NotNull IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isSideSolid(@NotNull IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos, @NotNull EnumFacing side) {
        return false;
    }

    @Override
    public boolean hasTileEntity(@NotNull IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@NotNull World world, @NotNull IBlockState state) {
        return new TEPowderKeg();
    }

    @Override
    public void getDrops(@NotNull NonNullList<ItemStack> drops, @NotNull IBlockAccess world, @NotNull BlockPos pos, IBlockState state, int fortune) {
        // Only drop the keg if it's not sealed, since the keg with contents will be already dropped by the TE
        if (!state.getValue(SEALED)) {
            super.getDrops(drops, world, pos, state, fortune);
        }
    }

    /**
     * Called when this Block is destroyed by an Explosion
     */
    @Override
    public void onBlockExploded(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull Explosion explosionIn) {
        var tile = TileUtils.getTile(worldIn, pos, TEPowderKeg.class);
        if (!worldIn.isRemote && tile != null && tile.getStrength() > 0) // explode even if not sealed cause gunpowder
        {
            trigger(worldIn, pos, worldIn.getBlockState(pos).withProperty(SEALED, true).withProperty(LIT, true), null);
        } else {
            super.onBlockExploded(worldIn, pos, explosionIn);
        }
    }

    @Override
    @NotNull
    public ItemStack getPickBlock(@NotNull IBlockState state, @NotNull RayTraceResult target, @NotNull World world,
                                  @NotNull BlockPos pos, @NotNull EntityPlayer player) {
        var tile = TileUtils.getTile(world, pos, TEPowderKeg.class);
        if (tile != null) {
            return tile.getItemStack(state);
        }
        return new ItemStack(state.getBlock());
    }

    private boolean canStay(IBlockAccess world, BlockPos pos) {
        boolean solid = world.getBlockState(pos.down())
                .getBlockFaceShape(world, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID;
        return solid || world.getBlockState(pos.down()).getBlock() instanceof BlockPowderKeg;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt != null) {
            ItemStackHandler stackHandler = new ItemStackHandler();
            stackHandler.deserializeNBT(nbt.getCompoundTag("inventory"));
            int count = 0;
            int firstSlot = -1;
            for (int i = 0; i < stackHandler.getSlots(); i++) {
                if (firstSlot < 0 && !stackHandler.getStackInSlot(i).isEmpty()) {
                    firstSlot = i;
                }
                count += stackHandler.getStackInSlot(i).getCount();
            }

            if (count == 0) {
                tooltip.add(I18n.format(ModUtils.name("tooltip.powderkeg_empty")));
            } else {
                ItemStack itemStack = stackHandler.getStackInSlot(firstSlot);
                tooltip.add(I18n.format(ModUtils.name("tooltip.powderkeg_amount"), count, itemStack.getItem()
                        .getItemStackDisplayName(itemStack)));
            }
        }
    }

    @Override
    public @NotNull String getName() {
        return "device/powderkeg";
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TEPowderKeg.class;
    }
}
