package se.gory_moon.horsepower.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemLead;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import se.gory_moon.horsepower.HorsePowerMod;
import se.gory_moon.horsepower.tileentity.TileEntityHPBase;
import se.gory_moon.horsepower.tileentity.TileEntityHPHorseBase;
import se.gory_moon.horsepower.util.Utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public abstract class BlockHPBase extends Block implements IItemSize {

    public static final AxisAlignedBB EMPTY_AABB = new AxisAlignedBB(0, 0, 0, 0, 0, 0);
    protected static boolean keepInventory = false;

    public BlockHPBase(Material materialIn) {
        super(materialIn);
        setCreativeTab(HorsePowerMod.creativeTab);
    }

    public @NotNull Size getSize(@NotNull ItemStack stack) {
        return Size.LARGE;
    }

    public @NotNull Weight getWeight(@NotNull ItemStack stack) {
        return Weight.HEAVY;
    }

    public abstract void emptiedOutput(World world, BlockPos pos);

    public int getSlot(IBlockState state, float hitX, float hitY, float hitZ) {
        return -1;
    }

    public void onWorkerAttached(EntityPlayer playerIn, EntityCreature creature) {
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!keepInventory && !worldIn.isRemote) {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityHPBase) {
                worldIn.updateComparatorOutputLevel(pos, this);
            }
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                    float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getHeldItem(hand);
        TileEntityHPBase te = (TileEntityHPBase) worldIn.getTileEntity(pos);
        TileEntityHPHorseBase teH = null;
        if (te == null) return false;
        if (te instanceof TileEntityHPHorseBase)
            teH = (TileEntityHPHorseBase) te;
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        EntityCreature creature = null;
        if (teH != null) {
            ArrayList<Class<? extends EntityCreature>> clazzes = Utils.getCreatureClasses();
            search:
            for (Class<? extends Entity> clazz : clazzes) {
                for (Object entity : worldIn.getEntitiesWithinAABB(clazz,
                        new AxisAlignedBB((double) x - 7.0D, (double) y - 7.0D, (double) z - 7.0D, (double) x + 7.0D, (double) y + 7.0D,
                                (double) z + 7.0D))) {
                    if (entity instanceof EntityCreature tmp) {
                        if ((tmp.getLeashed() && tmp.getLeashHolder() == playerIn)) {
                            creature = tmp;
                            break search;
                        }
                    }
                }
            }
        }
        if (teH != null && ((stack.getItem() instanceof ItemLead && creature != null) || creature != null)) {
            if (!teH.hasWorker()) {
                creature.clearLeashed(true, false);
                teH.setWorker(creature);
                onWorkerAttached(playerIn, creature);
                return true;
            } else {
                return false;
            }
        } else if (!stack.isEmpty() && te.isItemValidForSlot(0, stack)) {
            ItemStack itemStack = te.getStackInSlot(0);
            boolean flag = false;

            if (itemStack.isEmpty()) {
                te.setInventorySlotContents(0, stack.copy());
                stack.setCount(stack.getCount() - te.getInventoryStackLimit(stack));
                flag = true;
            } else if (TileEntityHPBase.canCombine(itemStack, stack)) {
                int i = Math.min(te.getInventoryStackLimit(stack), stack.getMaxStackSize()) - itemStack.getCount();
                int j = Math.min(stack.getCount(), i);
                stack.shrink(j);
                itemStack.grow(j);
                flag = j > 0;
            }

            if (flag)
                return true;
        }

        int slot = getSlot(state.getBlock().getExtendedState(state, worldIn, pos), hitX, hitY, hitZ);
        ItemStack result = ItemStack.EMPTY;
        if (slot > -1) {
            result = te.removeStackFromSlot(slot);
        } else if (slot > -2) {
            result = te.removeStackFromSlot(1);
            if (result.isEmpty()) {
                result = te.removeStackFromSlot(2);
                if (result.isEmpty() && stack.isEmpty() && hand != EnumHand.OFF_HAND) {
                    result = te.removeStackFromSlot(0);
                }
            }
            if (!result.isEmpty())
                emptiedOutput(worldIn, pos);
        }

        if (result.isEmpty()) {
            if (!stack.isEmpty())
                return false;
            if (teH != null)
                teH.setWorkerToPlayer(playerIn);
        }

        if (!result.isEmpty())
            ItemHandlerHelper.giveItemToPlayer(playerIn, result, EntityEquipmentSlot.MAINHAND.getSlotIndex());

        te.markDirty();
        return true;
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        super.onBlockHarvested(worldIn, pos, state, player);

        if (!player.capabilities.isCreativeMode && !worldIn.isRemote) {
            TileEntityHPBase te = getTileEntity(worldIn, pos);

            if (te != null) {
                InventoryHelper.dropInventoryItems(worldIn, pos, te.getInventory());
                if (te instanceof TileEntityHPHorseBase && ((TileEntityHPHorseBase) te).hasWorker())
                    InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.LEAD));
            }
        }
    }

    @Override
    public boolean removedByPlayer(@NotNull IBlockState state, @NotNull World world, @NotNull BlockPos pos, @NotNull EntityPlayer player,
                                   boolean willHarvest) {
        // we pull up a few calls to this point in time because we still have the TE here
        // the execution otherwise is equivalent to vanilla order
        this.onPlayerDestroy(world, pos, state);
        onBlockHarvested(world, pos, state, player);
        if (willHarvest) {
            this.harvestBlock(world, player, pos, state, world.getTileEntity(pos), player.getHeldItemMainhand());
        }

        world.setBlockToAir(pos);
        // return false to prevent the above called functions to be called again
        return false;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        try {
            return (TileEntity) getTileClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @NotNull
    public abstract Class<?> getTileClass();

    protected <T extends TileEntityHPBase> T getTileEntity(IBlockAccess worldIn, BlockPos pos) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return (tileentity != null && getTileClass().isAssignableFrom(tileentity.getClass())) ? (T) tileentity : null;
    }
}
