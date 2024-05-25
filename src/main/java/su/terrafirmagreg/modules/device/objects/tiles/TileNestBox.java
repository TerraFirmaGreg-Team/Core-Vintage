package su.terrafirmagreg.modules.device.objects.tiles;

import su.terrafirmagreg.api.capabilities.egg.CapabilityEgg;
import su.terrafirmagreg.api.spi.gui.provider.IContainerProvider;
import su.terrafirmagreg.api.spi.tile.BaseTileInventory;
import su.terrafirmagreg.modules.animal.api.type.IAnimal;
import su.terrafirmagreg.modules.device.client.gui.GuiNestBox;
import su.terrafirmagreg.modules.device.objects.containers.ContainerNestBox;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;


import net.dries007.tfc.api.capability.inventory.IItemHandlerSidedCallback;
import net.dries007.tfc.api.capability.inventory.ItemHandlerSidedWrapper;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.CalendarTFC;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TileNestBox extends BaseTileInventory
        implements ITickable, IItemHandlerSidedCallback, IContainerProvider<ContainerNestBox, GuiNestBox> {

    private static final int NUM_SLOTS = 4;
    private final IItemHandler inventoryWrapperExtract;

    public TileNestBox() {
        super(NUM_SLOTS);
        this.inventoryWrapperExtract = new ItemHandlerSidedWrapper(this, inventory, EnumFacing.DOWN);
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            for (int i = 0; i < inventory.getSlots(); i++) {
                ItemStack stack = inventory.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    var cap = CapabilityEgg.get(stack);
                    if (cap != null && cap.getHatchDay() > 0 && cap.getHatchDay() <= CalendarTFC.PLAYER_TIME.getTotalDays()) {
                        Entity baby = cap.getEntity(this.world);
                        if (baby != null) {
                            if (baby instanceof IAnimal) {
                                ((IAnimal) baby).setBirthDay((int) CalendarTFC.PLAYER_TIME.getTotalDays());
                            }
                            baby.setLocationAndAngles(this.pos.getX(), this.pos.getY() + 0.5D, this.pos.getZ(), 0.0F, 0.0F);
                            world.spawnEntity(baby);
                            inventory.setStackInSlot(i, ItemStack.EMPTY);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return stack.getItem() == Items.EGG;
    }

    public void insertEgg(ItemStack stack) {
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (inventory.insertItem(i, stack, false).isEmpty()) {
                return;
            }
        }
    }

    public boolean hasFreeSlot() {
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (inventory.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasBird() {
        return getBird() != null;
    }

    public void seatOnThis(EntityLiving bird) {
        Helpers.sitOnBlock(this.world, this.pos, bird, 0.0D);
    }

    @Nullable
    public Entity getBird() {
        return Helpers.getSittingEntity(this.world, this.pos);
    }

    @Override
    public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
        return (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing == EnumFacing.DOWN) || super.hasCapability(capability, facing);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
        return (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing == EnumFacing.DOWN) ?
                (T) inventoryWrapperExtract : super.getCapability(capability, facing);
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, EnumFacing side) {
        return false;
    }

    @Override
    public boolean canExtract(int slot, EnumFacing side) {
        return side == EnumFacing.DOWN;
    }

    @Override
    public ContainerNestBox getContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
        return new ContainerNestBox(inventoryPlayer, this);
    }

    @Override
    public GuiNestBox getGuiContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
        return new GuiNestBox(getContainer(inventoryPlayer, world, state, pos), inventoryPlayer);
    }
}
