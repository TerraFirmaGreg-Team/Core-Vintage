package su.terrafirmagreg.modules.device.objects.items;

import su.terrafirmagreg.api.spi.item.BaseItem;

import net.minecraft.item.ItemStack;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import lombok.Getter;

public class ItemSlingAmmo extends BaseItem {

    @Getter
    private final int type;
    private final String name;

    public ItemSlingAmmo(int type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public Size getSize(ItemStack itemStack) {
        return Size.SMALL;
    }

    @Override
    public Weight getWeight(ItemStack itemStack) {
        return Weight.LIGHT;
    }

    @Override
    public String getName() {
        return "device/sling/ammo/" + name;
    }
}
