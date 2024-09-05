package su.terrafirmagreg.modules.core.features.ambiental.provider;

import su.terrafirmagreg.modules.core.features.ambiental.modifiers.ModifierBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;


import java.util.Optional;

@FunctionalInterface
public interface IAmbientalEquipmentProvider
    extends IAmbientalBaseProvider {

  Optional<ModifierBase> getModifier(EntityPlayer player, ItemStack stack);

}
