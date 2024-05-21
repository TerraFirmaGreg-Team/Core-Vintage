package su.terrafirmagreg.api.features.ambiental.modifiers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ModifierTile extends ModifierBlock {

    public ModifierTile(String name) {
        super(name);

    }

    public ModifierTile(String name, float change, float potency) {
        super(name, change, potency);

    }

    public ModifierTile(String name, float change, float potency, boolean affectedByDistance) {
        super(name, change, potency, affectedByDistance);

    }

    public static boolean hasProtection(EntityPlayer player) {
        ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        return !stack.isEmpty();
    }

    public static void computeModifiers(EntityPlayer player, ModifierStorage storage) {
        ModifierBlock.computeModifiers(player, storage);
    }

}
