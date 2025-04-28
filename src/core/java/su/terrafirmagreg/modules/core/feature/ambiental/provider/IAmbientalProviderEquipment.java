package su.terrafirmagreg.modules.core.feature.ambiental.provider;

import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierEquipment;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.Optional;

@FunctionalInterface
public interface IAmbientalProviderEquipment extends IAmbientalProvider {

  Optional<ModifierEquipment> getModifier(EntityPlayer player, ItemStack stack);

}
