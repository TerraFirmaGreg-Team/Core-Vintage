package su.terrafirmagreg.modules.core.feature.ambiental.provider;

import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.Optional;

@FunctionalInterface
public interface IAmbientalProviderEquipment extends IAmbientalProviderBase {

  Optional<ModifierBase> getModifier(EntityPlayer player, ItemStack stack);

}
