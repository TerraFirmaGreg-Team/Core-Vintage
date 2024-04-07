package su.terrafirmagreg.modules.device.objects.tiles;

import su.terrafirmagreg.api.gui.IContainerProvider;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.modules.device.client.gui.GuiQuern;
import su.terrafirmagreg.modules.device.data.ItemsDevice;
import su.terrafirmagreg.modules.device.objects.container.ContainerQuern;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import lombok.Getter;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.recipes.quern.QuernRecipe;
import net.dries007.tfc.objects.te.TEInventory;
import net.dries007.tfc.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

import static net.minecraft.init.SoundEvents.*;

public class TEQuern extends TEInventory implements ITickable, IContainerProvider<ContainerQuern, GuiQuern> {

    public static final int SLOT_HANDSTONE = 0;
    public static final int SLOT_INPUT = 1;
    public static final int SLOT_OUTPUT = 2;

    @Getter
    private int rotationTimer;
    private boolean hasHandstone;

    public TEQuern() {
        super(3);
        rotationTimer = 0;
    }

    public ItemStack insertOrSwapItem(int slot, ItemStack playerStack) {
        ItemStack quernStack = inventory.getStackInSlot(slot);

        if (quernStack.isEmpty() || (playerStack.isStackable() && quernStack.isStackable() && quernStack.getItem() == playerStack.getItem() &&
                (!playerStack.getHasSubtypes() || playerStack.getMetadata() == quernStack.getMetadata()) &&
                ItemStack.areItemStackTagsEqual(playerStack, quernStack))) {
            return inventory.insertItem(slot, playerStack, false);
        }
        inventory.setStackInSlot(slot, playerStack);
        return quernStack;
    }

    @Override
    public int getSlotLimit(int slot) {
        return slot == SLOT_HANDSTONE ? 1 : 64;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        switch (slot) {
            case SLOT_HANDSTONE:
                return OreDictionaryHelper.doesStackMatchOre(stack, "handstone");
            case SLOT_INPUT:
                return QuernRecipe.get(stack) != null;
            default:
                return false;
        }
    }

    @Override
    public void setAndUpdateSlots(int slot) {
        markForBlockUpdate();
        if (slot == SLOT_HANDSTONE) {
            hasHandstone = OreDictionaryHelper.doesStackMatchOre(inventory.getStackInSlot(SLOT_HANDSTONE), "handstone");
        }
        super.setAndUpdateSlots(slot);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        rotationTimer = nbt.getInteger("rotationTimer");
        super.readFromNBT(nbt);
        hasHandstone = OreDictionaryHelper.doesStackMatchOre(inventory.getStackInSlot(SLOT_HANDSTONE), "handstone");
    }

    @Override
    @NotNull
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTUtils.setGenericNBTValue(nbt, "rotationTimer", rotationTimer);
        return super.writeToNBT(nbt);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return super.canInteractWith(player) && rotationTimer == 0;
    }

    public boolean isGrinding() {
        return rotationTimer > 0;
    }

    public boolean hasHandstone() {
        return hasHandstone;
    }

    public void grind() {
        this.rotationTimer = 90;
        markForBlockUpdate();
    }

    @Override
    public void update() {
        if (rotationTimer > 0) {
            rotationTimer--;

            if (rotationTimer == 0) {
                grindItem();
                world.playSound(null, pos, ENTITY_ARMORSTAND_FALL, SoundCategory.BLOCKS, 1.0f, 0.8f);
                inventory.getStackInSlot(SLOT_HANDSTONE).damageItem(1, new EntityCow(world));

                if (inventory.getStackInSlot(SLOT_HANDSTONE).isEmpty()) {
                    for (int i = 0; i < 15; i++) {
                        world.spawnParticle(EnumParticleTypes.ITEM_CRACK, pos.getX() + 0.5D, pos.getY() + 0.875D, pos.getZ() + 0.5D,
                                (world.rand.nextDouble() - world.rand.nextDouble()) / 4, world.rand.nextDouble() / 4,
                                (world.rand.nextDouble() - world.rand.nextDouble()) / 4, Item.getIdFromItem(ItemsDevice.HANDSTONE));
                    }
                    world.playSound(null, pos, BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1.0f, 0.8f);
                    world.playSound(null, pos, ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0f, 0.6f);
                }

                setAndUpdateSlots(SLOT_HANDSTONE);
            }
        }
    }

    @NotNull
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(getPos(), getPos().add(1, 2, 1));
    }

    private void grindItem() {
        ItemStack inputStack = inventory.getStackInSlot(SLOT_INPUT);
        if (!inputStack.isEmpty()) {
            QuernRecipe recipe = QuernRecipe.get(inputStack);
            if (recipe != null && !world.isRemote) {
                inputStack.shrink(recipe.getIngredients().get(0).getAmount());
                ItemStack outputStack = recipe.getOutputItem(inputStack);
                outputStack = inventory.insertItem(SLOT_OUTPUT, outputStack, false);
                inventory.setStackInSlot(SLOT_OUTPUT,
                        CapabilityFood.mergeItemStacksIgnoreCreationDate(inventory.getStackInSlot(SLOT_OUTPUT), outputStack));
                if (!outputStack.isEmpty()) {
                    // Still having leftover items, dumping in world
                    InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY() + 1, pos.getZ(), outputStack);
                }
            }
        }
    }

    @Override
    public ContainerQuern getContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
        return new ContainerQuern(inventoryPlayer, this);
    }

    @Override
    public GuiQuern getGuiContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
        return null;
    }
}
