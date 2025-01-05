package su.terrafirmagreg.temp.modules.ambiental.api;

import su.terrafirmagreg.temp.modules.ambiental.modifier.TempModifier;

import net.minecraft.entity.player.EntityPlayer;

import java.util.Optional;

@FunctionalInterface
public interface IEnvironmentalTemperatureProvider {

  Optional<TempModifier> getModifier(EntityPlayer player);
}
