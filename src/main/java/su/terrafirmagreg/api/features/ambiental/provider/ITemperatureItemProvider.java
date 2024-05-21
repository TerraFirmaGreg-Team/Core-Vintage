package su.terrafirmagreg.api.features.ambiental.provider;

import su.terrafirmagreg.api.features.ambiental.modifiers.ModifierBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;


import java.util.Optional;

//Add an example of this into TemperatureRegistry for items you didn't create personally
@FunctionalInterface
public interface ITemperatureItemProvider extends ITemperatureProvider {

    Optional<ModifierBase> getModifier(EntityPlayer player, ItemStack stack);

}
