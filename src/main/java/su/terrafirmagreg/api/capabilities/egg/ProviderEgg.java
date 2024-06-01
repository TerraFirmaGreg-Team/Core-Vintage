package su.terrafirmagreg.api.capabilities.egg;

import su.terrafirmagreg.api.util.NBTUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

public class ProviderEgg implements ICapabilityEgg {

    @Getter
    private boolean fertilized;
    @Getter
    private long hatchDay;
    private NBTTagCompound entitytag;

    public ProviderEgg() {
        this(null);
    }

    public ProviderEgg(@Nullable NBTTagCompound nbt) {
        deserializeNBT(nbt);
    }

    public ProviderEgg(boolean fertilized, long hatchDay, Entity entity) {
        this.fertilized = fertilized;
        this.hatchDay = hatchDay;
        this.entitytag = entity.serializeNBT();
    }

    @Nullable
    @Override
    public Entity getEntity(World world) {

        return entitytag != null ? EntityList.createEntityFromNBT(entitytag, world) : null;
    }

    public void setFertilized(@NotNull Entity entity, long hatchDay) {
        this.fertilized = true;
        this.entitytag = entity.serializeNBT();
        this.hatchDay = hatchDay;
    }

    @Override
    public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {

        return capability == CapabilityEgg.CAPABILITY;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {

        return hasCapability(capability, facing) ? (T) this : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        if (entitytag != null) {
            NBTUtils.setGenericNBTValue(nbt, "fertilized", fertilized);
            NBTUtils.setGenericNBTValue(nbt, "hatchDay", hatchDay);
            NBTUtils.setGenericNBTValue(nbt, "entity", entitytag);
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        if (nbt != null && nbt.hasKey("entity")) {
            fertilized = nbt.getBoolean("fertilized");
            hatchDay = nbt.getLong("hatchDay");
            entitytag = nbt.getCompoundTag("entity");
        } else {
            fertilized = false;
            entitytag = null;
            hatchDay = 0;
        }
    }
}
