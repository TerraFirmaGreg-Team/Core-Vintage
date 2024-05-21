package su.terrafirmagreg.api.features.ambiental.provider;

import su.terrafirmagreg.api.features.ambiental.modifiers.ModifierBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;


import java.util.Optional;

@FunctionalInterface
public interface ITemperatureEquipmentProvider extends ITemperatureProvider {

    Optional<ModifierBase> getModifier(EntityPlayer player, ItemStack stack);

}
