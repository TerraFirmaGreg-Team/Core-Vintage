package su.terrafirmagreg.modules.device.objects.items;

import su.terrafirmagreg.modules.rock.objects.items.ItemRockLoose;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.metal.ItemIngot;

import org.jetbrains.annotations.NotNull;

public class ItemSlingReinforced extends ItemSling {

    public ItemSlingReinforced() {
        this.setMaxDamage(256);
    }

    @Override
    public @NotNull String getName() {
        return "device/sling/reinforced";
    }

    @Override
    protected boolean isStone(ItemStack stack) {
        if (stack.getItem() instanceof ItemRockLoose) {
            return true;
        } else if (stack.getItem() instanceof ItemIngot ingot) {
            return ingot.getMetal(stack) == Metal.UNKNOWN;
        } else return stack.getItem() instanceof ItemSlingAmmo;
    }

}
