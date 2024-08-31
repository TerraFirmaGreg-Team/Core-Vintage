package su.terrafirmagreg.modules.core.capabilities.worldtracker;

import su.terrafirmagreg.modules.core.capabilities.worldtracker.spi.CollapseData;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public interface ICapabilityWorldTracker extends ICapabilitySerializable<NBTTagCompound> {

    void addCollapseData(CollapseData collapse);

    void tick(World world);
}
