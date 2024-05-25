package su.terrafirmagreg.modules.device.objects.tiles;

import su.terrafirmagreg.api.capabilities.sharpness.CapabilitySharpness;
import su.terrafirmagreg.api.spi.tile.BaseTileInventory;
import su.terrafirmagreg.modules.device.init.ItemsDevice;
import su.terrafirmagreg.modules.device.objects.items.ItemGrindstone;

import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;


import lyeoj.tfcthings.init.TFCThingsSoundEvents;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.objects.blocks.BlockFluidWater;

import org.jetbrains.annotations.NotNull;

public class TileGrindstone extends BaseTileInventory implements ITickable {

    public static final int SLOT_GRINDSTONE = 0;
    public static final int SLOT_INPUT = 1;
    private int rotationTimer = 0;
    private boolean hasGrindstone;

    public TileGrindstone() {
        super(2);
    }

    public void readFromNBT(@NotNull NBTTagCompound nbt) {
        this.rotationTimer = nbt.getInteger("rotationTimer");
        this.hasGrindstone = this.inventory.getStackInSlot(SLOT_GRINDSTONE).getItem() instanceof ItemGrindstone;
        super.readFromNBT(nbt);
    }

    @NotNull
    public NBTTagCompound writeToNBT(@NotNull NBTTagCompound nbt) {
        nbt.setInteger("rotationTimer", this.rotationTimer);
        return super.writeToNBT(nbt);
    }

    public ItemStack getGrindstone() {
        return this.inventory.getStackInSlot(SLOT_GRINDSTONE);
    }

    public boolean hasGrindstone() {
        return this.hasGrindstone;
    }

    public int getRotationTimer() {
        return this.rotationTimer;
    }

    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return switch (slot) {
            case SLOT_GRINDSTONE -> stack.getItem() instanceof ItemGrindstone;
            case SLOT_INPUT -> CapabilitySharpness.has(stack);
            default -> false;
        };
    }

    public int getSlotLimit(int slot) {
        return 1;
    }

    public ItemStack insertOrSwapItem(int slot, ItemStack playerStack) {
        ItemStack grindstoneStack = this.inventory.getStackInSlot(slot);
        if (!grindstoneStack.isEmpty() &&
                (!playerStack.isStackable() || !grindstoneStack.isStackable() || grindstoneStack.getItem() != playerStack.getItem() ||
                        playerStack.getHasSubtypes() && playerStack.getMetadata() != grindstoneStack.getMetadata() ||
                        !ItemStack.areItemStackTagsEqual(playerStack, grindstoneStack))) {
            this.inventory.setStackInSlot(slot, playerStack);
            return grindstoneStack;
        } else {
            return this.inventory.insertItem(slot, playerStack, false);
        }
    }

    public void setAndUpdateSlots(int slot) {
        this.markForBlockUpdate();
        if (slot == 0) {
            this.hasGrindstone = this.inventory.getStackInSlot(SLOT_GRINDSTONE).getItem() instanceof ItemGrindstone;
        }

        super.setAndUpdateSlots(slot);
    }

    @Override
    public void update() {
        ItemStack inputStack = this.inventory.getStackInSlot(SLOT_INPUT);
        ItemStack grindstoneStack = this.inventory.getStackInSlot(SLOT_GRINDSTONE);
        if (shouldStartGrinding(inputStack, grindstoneStack)) {
            if (this.rotationTimer > 0) {
                --this.rotationTimer;
            }
            if (this.rotationTimer == 1) {
                sharpenItem(inputStack, grindstoneStack);
                world.playSound(null, pos, TFCThingsSoundEvents.WHETSTONE_SHARPEN, SoundCategory.BLOCKS, 0.2F,
                        0.6F + (world.rand.nextFloat() - world.rand.nextFloat()) / 16.0F);
                if (grindstoneStack.isEmpty()) {
                    for (int i = 0; i < 15; ++i) {
                        this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, (double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.875D,
                                (double) this.pos.getZ() + 0.5D, (this.world.rand.nextDouble() - this.world.rand.nextDouble()) / 4.0D,
                                this.world.rand.nextDouble() / 4.0D, (this.world.rand.nextDouble() - this.world.rand.nextDouble()) / 4.0D,
                                Item.getIdFromItem(ItemsDevice.GRINDSTONE_QUARTZ));
                    }
                    this.world.playSound(null, this.pos, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1.0F, 0.8F);
                    this.world.playSound(null, this.pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 0.7F, 0.6F);
                }
                this.setAndUpdateSlots(0);
            }
            if (this.rotationTimer == 0) {
                this.rotationTimer = 90;
                world.playSound(null, pos, TFCSounds.QUERN_USE, SoundCategory.BLOCKS, 0.2F,
                        0.8F + (world.rand.nextFloat() - world.rand.nextFloat()) / 16.0F);
            }
        } else {
            this.rotationTimer = 0;
        }

    }

    private BlockPos getFluidLocation() {
        int dir = getBlockMetadata();
        BlockPos check = pos.down();
        check = switch (dir) {
            case 0 -> check.east();
            case 1 -> check.south();
            case 2 -> check.west();
            default -> check.north();
        };
        return check;
    }

    public int getFlowDirection() {
        if (world.getBlockState(getFluidLocation()).getBlock() instanceof BlockFluidWater water) {
            if (getBlockMetadata() == 0 || getBlockMetadata() == 2) {
                double flow = water.getFlowVector(world, getFluidLocation()).z;
                if (flow > 0) {
                    return 2;
                } else if (flow < 0) {
                    return 1;
                }
            } else {
                double flow = water.getFlowVector(world, getFluidLocation()).x;
                if (flow > 0) {
                    return 3;
                } else if (flow < 0) {
                    return 4;
                }
            }
        }
        return 0;
    }

    private boolean shouldStartGrinding(ItemStack inputStack, ItemStack grindstoneStack) {
        if (inputStack.isEmpty() || grindstoneStack.isEmpty() || getFlowDirection() == 0) {
            return false;
        }
        if (CapabilitySharpness.has(inputStack)) {
            var capability = CapabilitySharpness.get(inputStack);
            ItemGrindstone grindstone = (ItemGrindstone) grindstoneStack.getItem();
            return inputStack.getMaxDamage() - inputStack.getItemDamage() > 1 && capability.getCharges() < grindstone.getMaxCharges();
        }
        return false;
    }

    private void sharpenItem(ItemStack inputStack, ItemStack grindstoneStack) {
        var capability = CapabilitySharpness.get(inputStack);
        ItemGrindstone grindstone = (ItemGrindstone) grindstoneStack.getItem();
        if (capability != null && capability.getCharges() < grindstone.getMaxCharges()) {
            for (int i = 0; i < grindstone.getTier(); i++) {
                if (capability.getCharges() >= grindstone.getMaxCharges())
                    break;
                capability.addCharge();
            }
            inputStack.damageItem(1, new EntityCow(this.world));
            grindstoneStack.damageItem(1, new EntityCow(this.world));
        }
    }
}
