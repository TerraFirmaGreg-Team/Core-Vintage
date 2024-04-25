package tfctech.objects.tileentities;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.te.TEInventory;


import su.terrafirmagreg.api.lib.MathConstants;


import tfctech.TFCTech;
import tfctech.api.recipes.WireDrawingRecipe;
import tfctech.client.TechSounds;
import tfctech.network.PacketTileEntityUpdate;
import tfctech.objects.items.metal.ItemTechMetal;
import tfctech.registry.TechRegistries;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("WeakerAccess")
public class TEWireDrawBench extends TEInventory implements ITickable {

    private int progress = 0;
    private int lastProgress = 0;
    private boolean working = false;
    private int cachedWireColor = 0x00000000;

    public TEWireDrawBench() {
        super(2);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        switch (slot) {
            case 0:
                return stack.getItem() instanceof ItemTechMetal && ((ItemTechMetal) stack.getItem()).getType() == ItemTechMetal.ItemType.DRAW_PLATE;
            case 1:
                WireDrawingRecipe recipe = TechRegistries.WIRE_DRAWING.getValuesCollection()
                        .stream()
                        .filter(x -> x.matches(stack))
                        .findFirst()
                        .orElse(null);
                return recipe != null;
        }
        return false;
    }

    public boolean hasDrawPlate() {
        return inventory.getStackInSlot(0) != ItemStack.EMPTY;
    }

    public boolean hasWire() {
        WireDrawingRecipe recipe = TechRegistries.WIRE_DRAWING.getValuesCollection()
                .stream()
                .filter(x -> x.matches(inventory.getStackInSlot(1)))
                .findFirst()
                .orElse(null);
        return recipe != null;
    }

    public boolean startWork(EntityPlayer player) {
        if (canWork()) {
            if (progress == 0) {
                WireDrawingRecipe recipe = TechRegistries.WIRE_DRAWING.getValuesCollection()
                        .stream()
                        .filter(x -> x.matches(inventory.getStackInSlot(1)))
                        .findFirst()
                        .orElse(null);
                Metal.Tier workableTier = ((ItemTechMetal) inventory.getStackInSlot(0)
                        .getItem()).getMetal(inventory.getStackInSlot(0))
                        .getTier();
                if (recipe == null) {
                    player.sendStatusMessage(new TextComponentTranslation("tooltip.tfctech.wiredraw.no_recipe"), true);
                    return false;
                } else if (!recipe.getTier().isAtMost(workableTier)) {
                    world.playSound(null, pos, TechSounds.WIREDRAW_TONGS_FALL, SoundCategory.BLOCKS, 1.0F, 2.0F);
                    player.sendStatusMessage(new TextComponentTranslation("tooltip.tfctech.wiredraw.low_tier"), true);
                    return true;
                }
            }
            if (!world.isRemote) {
                world.playSound(null, pos, TechSounds.WIREDRAW_DRAWING, SoundCategory.BLOCKS, 1.0F, 1.0F);
                working = true;
                setAndUpdateSlots(0);
            }
            return true;
        }
        return false;
    }

    public ItemStack insertWire(@NotNull ItemStack stack, boolean simulate) {
        if (!hasWire() && hasDrawPlate() && isItemValid(1, stack)) {
            ItemStack output = inventory.insertItem(1, stack, simulate);
            if (!simulate) {
                cachedWireColor = 0x00000000;
                TechRegistries.WIRE_DRAWING.getValuesCollection().stream()
                        .filter(x -> x.matches(stack))
                        .findFirst().ifPresent(x -> cachedWireColor = x.getWireColor());
                setAndUpdateSlots(0);
            }
            return output;
        }
        return stack;
    }

    public ItemStack insertDrawPlate(@NotNull ItemStack stack, boolean simulate) {
        if (!hasDrawPlate() && isItemValid(0, stack)) {
            ItemStack output = inventory.insertItem(0, stack, simulate);
            if (!simulate) {
                setAndUpdateSlots(0);
            }
            return output;
        }
        return stack;
    }

    @Override
    public void setAndUpdateSlots(int slot) {
        TFCTech.getNetwork()
                .sendToAllTracking(new PacketTileEntityUpdate(this),
                        new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));
        super.setAndUpdateSlots(slot);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        cachedWireColor = nbt.getInteger("wireColor");
        working = nbt.getBoolean("working");
        progress = nbt.getInteger("progress");
        lastProgress = progress;
        super.readFromNBT(nbt);
    }

    @NotNull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("wireColor", cachedWireColor);
        nbt.setBoolean("working", working);
        nbt.setInteger("progress", progress);
        return super.writeToNBT(nbt);
    }

    @NotNull
    public ItemStack extractItem(int slot, boolean simulate) {
        if (slot < 0 || slot > 1 || (progress > 0 && progress < 100)) {
            return ItemStack.EMPTY;
        }
        if (slot == 1 && !simulate) {
            cachedWireColor = 0x00000000;
            progress = 0;
        }
        ItemStack output = inventory.extractItem(slot, 64, simulate);
        if (!simulate) {
            setAndUpdateSlots(slot);
        }
        return output;
    }

    @Nullable
    public Metal getDrawPlateMetal() {
        ItemStack stack = inventory.getStackInSlot(0);
        if (stack.getItem() instanceof ItemTechMetal) {
            return ((ItemTechMetal) stack.getItem()).getMetal(stack);
        }
        return null;
    }

    public int getProgress() {
        return this.progress;
    }

    public int getLastProgress() {
        return lastProgress;
    }

    public int getWireColor() {
        return inventory.getStackInSlot(1) != ItemStack.EMPTY ? cachedWireColor : 0x00000000;
    }

    public EnumFacing getRotation() {
        return world.getBlockState(pos).getValue(BlockHorizontal.FACING);
    }

    @Override
    public void update() {
        lastProgress = progress;
        if (working) {
            if (++progress % 25 == 0) {
                working = false;
                if (progress >= 100 && !world.isRemote) {
                    world.playSound(null, pos, TechSounds.WIREDRAW_TONGS_FALL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    if (inventory.getStackInSlot(0).attemptDamageItem(32, MathConstants.RNG, null)) {
                        inventory.setStackInSlot(0, ItemStack.EMPTY);
                    }
                    TechRegistries.WIRE_DRAWING.getValuesCollection()
                            .stream()
                            .filter(x -> x.matches(inventory.getStackInSlot(1)))
                            .findFirst()
                            .ifPresent(recipe -> inventory.setStackInSlot(1, recipe.getOutput()));
                    setAndUpdateSlots(1);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    @NotNull
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    private boolean canWork() {
        return hasDrawPlate() && hasWire() && !working && progress < 100;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }
}
