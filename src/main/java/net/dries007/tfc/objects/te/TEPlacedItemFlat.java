package net.dries007.tfc.objects.te;

import su.terrafirmagreg.data.lib.MathConstants;
import su.terrafirmagreg.api.util.StackUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.ConfigTFC;

import org.jetbrains.annotations.NotNull;

public class TEPlacedItemFlat extends TEBase {

    private byte rotation;
    private ItemStack inventory;

    public TEPlacedItemFlat() {
        rotation = (byte) MathConstants.RNG.nextInt(4);
        inventory = ItemStack.EMPTY;
    }

    public void onBreakBlock(BlockPos pos) {
        if (!world.isRemote && !inventory.isEmpty()) {
            StackUtils.spawnItemStack(world, pos, getStack());
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        rotation = nbt.hasKey("rotation") ? nbt.getByte("rotation") : 0;
        inventory = new ItemStack(nbt.getCompoundTag("stack"));
        super.readFromNBT(nbt);
    }

    @Override
    @NotNull
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setByte("rotation", rotation);
        nbt.setTag("stack", inventory.serializeNBT());
        return super.writeToNBT(nbt);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return ConfigTFC.Client.RENDER.placedItemFlatDistance * ConfigTFC.Client.RENDER.placedItemFlatDistance;
    }

    @Override
    @NotNull
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(getPos(), getPos().add(1D, 1D, 1D));
    }

    public byte getRotation() {
        return rotation;
    }

    public ItemStack getStack() {
        return inventory;
    }

    public void setStack(ItemStack stack) {
        this.inventory = stack;
        markDirty();
    }
}
