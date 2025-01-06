package su.terrafirmagreg.modules.core.capabilities.ambiental;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public interface ICapabilityAmbiental extends ICapabilitySerializable<NBTTagCompound> {

  float getTemperature();

  void setTemperature(float newTemp);

  EntityPlayer getPlayer();

  float getChange();

  float getChangeSpeed();

  float getTarget();

  float getPotency();

  void update();
}
