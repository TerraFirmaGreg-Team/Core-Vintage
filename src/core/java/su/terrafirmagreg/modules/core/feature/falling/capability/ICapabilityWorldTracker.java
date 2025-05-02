package su.terrafirmagreg.modules.core.feature.falling.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public interface ICapabilityWorldTracker extends ICapabilitySerializable<NBTTagCompound> {

  void addCollapseData(CollapseData collapse);

  void tick(World world);
}
