package net.dries007.tfc.compat.waila.interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IEntityDisplayOverride;
import mcjty.theoneprobe.api.IProbeHitEntityData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoEntityProvider;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.api.TextStyleClass;
import net.dries007.tfc.TerraFirmaCraft;

import java.util.List;

/**
 * Does the direct "translation" from IWailaEntity to The One Probe
 */
public class TOPEntityInterface implements IProbeInfoEntityProvider, IEntityDisplayOverride {

  protected final IWailaEntity internal;

  public TOPEntityInterface(IWailaEntity internal) {
    this.internal = internal;
  }

  @Override
  public String getID() {
    return "top.tfc." + internal.getClass().getName();
  }

  @Override
  public void addProbeEntityInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world, Entity entity, IProbeHitEntityData hitData) {
    if (entity == null) {
      return;
    }
    if (!isLookingAtProvider(entity)) {
      return;
    }

    NBTTagCompound nbt = entity.writeToNBT(new NBTTagCompound());

    List<String> tooltip = internal.getTooltip(entity, nbt);
    for (String string : tooltip) {
      info.horizontal(info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER)).text(string);
    }
  }

  @Override
  public boolean overrideStandardInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world, Entity entity, IProbeHitEntityData hitData) {
    if (entity == null) {
      return false;
    }
    if (!isLookingAtProvider(entity)) {
      return false;
    }
    NBTTagCompound nbt = entity.writeToNBT(new NBTTagCompound());

    String title = internal.getTitle(entity, nbt);

    if (title.isEmpty()) {
      return false;
    } else {
      info.horizontal(info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
        .vertical()
        .text(TextStyleClass.NAME + title)
        .text(TextStyleClass.MODNAME + TerraFirmaCraft.MOD_NAME);
      return true;
    }
  }

  public boolean overridesHeadInfo() {
    return internal.overrideTitle();
  }

  protected boolean isLookingAtProvider(Entity entity) {
    for (Class<?> clazz : internal.getLookupClass()) {
      if (clazz.isInstance(entity)) {
        return true;
      }
    }
    return false;
  }
}
