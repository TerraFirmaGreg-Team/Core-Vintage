package net.dries007.tfc.compat.waila.interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;


import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.IWailaRegistrar;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Does the direct "translation" from IWailaEntity to Hwyla
 */
public class HwylaEntityInterface implements IWailaEntityProvider, IWailaPlugin {

  protected final IWailaEntity internal;

  public HwylaEntityInterface(IWailaEntity internal) {
    this.internal = internal;
  }

  @Override
  public void register(IWailaRegistrar registrar) {
    // Register providers accordingly to each implementation
    for (Class<?> clazz : internal.getLookupClass()) {
      registrar.registerBodyProvider(this, clazz);
      // Register to update NBT data on all entities.
      registrar.registerNBTProvider(this, clazz);
      if (internal.overrideTitle()) {
        registrar.registerHeadProvider(this, clazz);
      }
    }
  }

  @NotNull
  @Override
  public List<String> getWailaHead(Entity entity, List<String> currentTooltip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
    currentTooltip.add(TextFormatting.WHITE + internal.getTitle(accessor.getEntity(), accessor.getNBTData()));
    return currentTooltip;
  }

  @NotNull
  @Override
  public List<String> getWailaBody(Entity entity, List<String> currentTooltip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
    currentTooltip.addAll(internal.getTooltip(entity, accessor.getNBTData()));
    return currentTooltip;
  }

  @NotNull
  @Override
  public NBTTagCompound getNBTData(EntityPlayerMP player, Entity ent, NBTTagCompound tag, World world) {
    return ent.writeToNBT(tag);
  }
}
