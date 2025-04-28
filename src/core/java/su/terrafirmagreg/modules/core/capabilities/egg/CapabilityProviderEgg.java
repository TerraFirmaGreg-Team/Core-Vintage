package su.terrafirmagreg.modules.core.capabilities.egg;

import lombok.Getter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.util.NBTUtils;

@Getter
public class CapabilityProviderEgg implements ICapabilityEgg {

    public static final String TAG_FERTILIZED = "fertilized";
    public static final String TAG_HATCH_DAY = "hatchDay";
    public static final String TAG_ENTITY_TAG = "entityTag";


    private boolean fertilized;
    private long hatchDay;
    private NBTTagCompound entityTag;

    public CapabilityProviderEgg() {
        this(null);
    }

    public CapabilityProviderEgg(@Nullable NBTTagCompound nbt) {
        deserializeNBT(nbt);
    }

    public CapabilityProviderEgg(boolean fertilized, long hatchDay, Entity entity) {
        this.fertilized = fertilized;
        this.hatchDay = hatchDay;
        this.entityTag = entity.serializeNBT();
    }

    @Nullable
    @Override
    public Entity getEntity(World world) {

        return entityTag != null ? EntityList.createEntityFromNBT(entityTag, world) : null;
    }

    public void setFertilized(@NotNull Entity entity, long hatchDay) {
        this.fertilized = true;
        this.entityTag = entity.serializeNBT();
        this.hatchDay = hatchDay;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        if (entityTag != null) {
            NBTUtils.setGenericNBTValue(nbt, TAG_FERTILIZED, fertilized);
            NBTUtils.setGenericNBTValue(nbt, TAG_HATCH_DAY, hatchDay);
            NBTUtils.setGenericNBTValue(nbt, TAG_ENTITY_TAG, entityTag);
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        if (nbt != null && nbt.hasKey(TAG_ENTITY_TAG)) {
            fertilized = nbt.getBoolean(TAG_FERTILIZED);
            hatchDay = nbt.getLong(TAG_HATCH_DAY);
            entityTag = nbt.getCompoundTag(TAG_ENTITY_TAG);
        } else {
            fertilized = false;
            entityTag = null;
            hatchDay = 0;
        }
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


}
