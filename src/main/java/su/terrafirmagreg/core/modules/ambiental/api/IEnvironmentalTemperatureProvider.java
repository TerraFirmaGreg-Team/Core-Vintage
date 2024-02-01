package su.terrafirmagreg.core.modules.ambiental.api;

import net.minecraft.entity.player.EntityPlayer;

import su.terrafirmagreg.core.modules.ambiental.modifier.TempModifier;

import java.util.Optional;

@FunctionalInterface
public interface IEnvironmentalTemperatureProvider {

    Optional<TempModifier> getModifier(EntityPlayer player);
}
