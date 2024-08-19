package su.terrafirmagreg.modules.core.features.ambiental.provider;

import su.terrafirmagreg.modules.core.features.ambiental.modifiers.ModifierBase;

import net.minecraft.entity.player.EntityPlayer;


import java.util.Optional;

//Add an example of this into TemperatureRegistry for general modifiers
@FunctionalInterface
public interface ITemperatureEnvironmentalProvider extends ITemperatureProvider {

    Optional<ModifierBase> getModifier(EntityPlayer player);
}
