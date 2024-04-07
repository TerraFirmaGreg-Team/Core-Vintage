package su.terrafirmagreg.modules.wood.objects.tiles;

import su.terrafirmagreg.api.util.NBTUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.RangedWrapper;

import org.apache.commons.lang3.tuple.Pair;
import se.gory_moon.horsepower.Configs;
import se.gory_moon.horsepower.HPEventHandler;
import se.gory_moon.horsepower.recipes.HPRecipeBase;
import se.gory_moon.horsepower.recipes.HPRecipes;
import se.gory_moon.horsepower.tileentity.TileEntityHPBase;

import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class TEWoodManualChopper extends TileEntityHPBase {

    private final IItemHandler handlerSide;
    private int currentItemChopAmount;
    private int totalItemChopAmount;

    public TEWoodManualChopper() {
        super(2);
        handlerSide = new RangedWrapper(new InvWrapper(inventory), 0, 1);
    }

    public static int getBaseAmount(ItemStack axe, EntityPlayer player) {
        int baseAmount = 100;
        int harvestLevel = axe.getItem().getHarvestLevel(axe, "axe", player, null);
        if (harvestLevel > -1 && HPEventHandler.harvestPercentages.get(harvestLevel) != null) {
            baseAmount = HPEventHandler.harvestPercentages.get(harvestLevel).getLeft();
        }
        for (Map.Entry<ItemStack, Pair<Integer, Integer>> entry : HPEventHandler.choppingAxes.entrySet()) {
            if (entry.getKey().isItemEqual(axe)) {
                return entry.getValue().getLeft();
            }
        }
        return baseAmount;
    }

    public static int getChance(ItemStack axe, EntityPlayer player) {
        int chance = 0;
        int harvestLevel = axe.getItem().getHarvestLevel(axe, "axe", player, null);
        if (harvestLevel > -1 && HPEventHandler.harvestPercentages.get(harvestLevel) != null) {
            chance = HPEventHandler.harvestPercentages.get(harvestLevel).getRight();
        }
        for (Map.Entry<ItemStack, Pair<Integer, Integer>> entry : HPEventHandler.choppingAxes.entrySet()) {
            if (entry.getKey().isItemEqual(axe)) {
                return entry.getValue().getRight();
            }
        }
        return chance;
    }

    public boolean chop(EntityPlayer player, ItemStack held) {
        if (canWork()) {
            currentItemChopAmount++;

            if (currentItemChopAmount >= totalItemChopAmount) {
                currentItemChopAmount = 0;

                totalItemChopAmount = HPRecipes.instance().getChoppingTime(getStackInSlot(0), true);
                chopItem(player, held);
                return true;
            }
            markDirty();
        }
        return false;
    }

    @Override
    public HPRecipeBase getRecipe() {
        return HPRecipes.instance().getChoppingBlockRecipe(getStackInSlot(0), true);
    }

    @Override
    public ItemStack getRecipeItemStack() {
        return HPRecipes.instance().getChopperResult(getStackInSlot(0), true);
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return index == 0 && HPRecipes.instance()
                .hasChopperRecipe(stack, true) && getStackInSlot(1).isEmpty() && getStackInSlot(0).isEmpty();
    }

    @Override
    public int getField(int id) {
        return switch (id) {
            case 0 -> totalItemChopAmount;
            case 1 -> currentItemChopAmount;
            default -> 0;
        };
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                totalItemChopAmount = value;
                break;
            case 1:
                currentItemChopAmount = value;
        }
    }

    @Override
    public int getFieldCount() {
        return 3;
    }

    @Override
    public String getName() {
        return "container.manual_chopper";
    }

    @Override
    public int getOutputSlot() {
        return 1;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = getStackInSlot(index);
        super.setInventorySlotContents(index, stack);

        if (index == 1 && getStackInSlot(1).isEmpty()) {
            markDirty();
        }

        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        if (index == 0 && !flag) {
            totalItemChopAmount = HPRecipes.instance().getChoppingTime(stack, true);
            currentItemChopAmount = 0;
            markDirty();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        if (getStackInSlot(0).getCount() > 0) {
            currentItemChopAmount = nbt.getInteger("chopTime");
            totalItemChopAmount = nbt.getInteger("totalChopTime");
        } else {
            currentItemChopAmount = 0;
            totalItemChopAmount = 1;
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTUtils.setGenericNBTValue(nbt, "chopTime", currentItemChopAmount);
        NBTUtils.setGenericNBTValue(nbt, "totalChopTime", totalItemChopAmount);

        return super.writeToNBT(nbt);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return super.hasCapability(capability, facing) || (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing != null);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        T cap = super.getCapability(capability, facing);
        return cap != null ? cap : (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) ? (T) handlerSide : null;
    }

    private void chopItem(EntityPlayer player, ItemStack held) {
        if (canWork()) {
            ItemStack input = getStackInSlot(0);
            if (!getWorld().isRemote) {
                ItemStack result = getRecipeItemStack();
                ItemStack output = getStackInSlot(1);

                double baseAmount = ((double) getBaseAmount(held, player)) / 100D;
                int chance = getChance(held, player);

                result = result.copy();
                result.setCount((int) Math.ceil((double) result.getCount() * baseAmount));
                if (chance >= 100 || world.rand.nextInt(100) < chance)
                    result.grow(1);

                if (Configs.general.choppingBlockDrop) {
                    InventoryHelper.spawnItemStack(getWorld(), getPos().getX(), getPos().getY() + 0.5, getPos().getZ(), result);
                } else {
                    if (output.isEmpty()) {
                        setInventorySlotContents(1, result);
                    } else if (output.getItem() == result.getItem()) {
                        output.grow(result.getCount());
                    }
                }
            }
            getWorld().playSound(player, getPos(), SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
            input.shrink(1);
            markDirty();
        }
    }
}
