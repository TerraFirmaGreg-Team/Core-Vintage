package su.terrafirmagreg.api.capabilities.sharpness;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
public class ProviderSharpness implements ICapabilitySharpness {

    protected ItemStack container;

    public ProviderSharpness() {
        this(null);
    }

    public ProviderSharpness(ItemStack container) {
        this.container = container;
    }

    public int getCharges() {
        NBTTagCompound tag = container.getTagCompound();
        if (tag != null && tag.hasKey("sharpness")) {
            return tag.getInteger("sharpness");
        }
        return 0;
    }

    public void setCharges(int charges) {
        if (!container.hasTagCompound()) {
            container.setTagCompound(new NBTTagCompound());
        }
        container.getTagCompound().setInteger("sharpness", charges);
    }

    public void addCharge() {
        setCharges(getCharges() + 1);
    }

    public void removeCharge() {
        if (getCharges() > 0) {
            setCharges(getCharges() - 1);
        }
    }

    @Override
    public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilitySharpness.CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
        return hasCapability(capability, facing) ? (T) this : null;
    }
}
