package su.terrafirmagreg.modules.core.feature.ambiental.provider;

import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierBase;

import net.minecraft.entity.player.EntityPlayer;

import java.util.Optional;

//Add an example of this into TemperatureRegistry for general modifiers
@FunctionalInterface
public interface IAmbientalProviderEnvironmental extends IAmbientalProviderBase {

  Optional<ModifierBase> getModifier(EntityPlayer player);
}
