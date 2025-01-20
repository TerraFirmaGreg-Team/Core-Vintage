package su.terrafirmagreg.modules.core.feature.ambiental.provider;

import net.minecraft.entity.player.EntityPlayer;

import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierEnvironmental;

import java.util.Optional;

//Add an example of this into TemperatureRegistry for general modifiers
@FunctionalInterface
public interface IAmbientalProviderEnvironmental extends IAmbientalProviderBase {

  Optional<ModifierEnvironmental> getModifier(EntityPlayer player);
}
