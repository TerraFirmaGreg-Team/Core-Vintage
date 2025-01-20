package su.terrafirmagreg.modules.core.feature.ambiental.provider;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierEquipment;

import java.util.Optional;

@FunctionalInterface
public interface IAmbientalProviderEquipment extends IAmbientalProviderBase {

  Optional<ModifierEquipment> getModifier(EntityPlayer player, ItemStack stack);

}
