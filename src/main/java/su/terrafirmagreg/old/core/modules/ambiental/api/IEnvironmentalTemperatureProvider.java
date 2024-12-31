package su.terrafirmagreg.old.core.modules.ambiental.api;

import su.terrafirmagreg.old.core.modules.ambiental.modifier.TempModifier;

import net.minecraft.entity.player.EntityPlayer;

import java.util.Optional;

@FunctionalInterface
public interface IEnvironmentalTemperatureProvider {

  Optional<TempModifier> getModifier(EntityPlayer player);
}
