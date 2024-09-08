package su.terrafirmagreg.modules.core.feature.ambiental.provider;

import su.terrafirmagreg.modules.core.feature.ambiental.modifiers.ModifierBase;

import net.minecraft.entity.player.EntityPlayer;


import java.util.Optional;

//Add an example of this into TemperatureRegistry for general modifiers
@FunctionalInterface
public interface IAmbientalEnvironmentalProvider
        extends IAmbientalBaseProvider {

  Optional<ModifierBase> getModifier(EntityPlayer player);
}
