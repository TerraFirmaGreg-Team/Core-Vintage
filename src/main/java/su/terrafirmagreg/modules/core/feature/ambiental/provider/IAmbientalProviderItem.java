package su.terrafirmagreg.modules.core.feature.ambiental.provider;

import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.Optional;

//Add an example of this into TemperatureRegistry for items you didn't create personally
@FunctionalInterface
public interface IAmbientalProviderItem extends IAmbientalProviderBase {

  Optional<ModifierBase> getModifier(EntityPlayer player, ItemStack stack);

}
