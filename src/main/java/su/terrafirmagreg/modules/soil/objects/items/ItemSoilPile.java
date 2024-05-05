package su.terrafirmagreg.modules.soil.objects.items;

import su.terrafirmagreg.api.spi.item.BaseItem;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.item.ISoilItem;
import su.terrafirmagreg.modules.soil.api.types.variant.item.SoilItemVariant;

import net.minecraft.item.ItemStack;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import lombok.Getter;

@Getter
public class ItemSoilPile extends BaseItem implements ISoilItem {

    private final SoilItemVariant variant;
    private final SoilType type;

    public ItemSoilPile(SoilItemVariant variant, SoilType type) {

        this.variant = variant;
        this.type = type;
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, variant);
    }

    @Override
    public Size getSize(ItemStack stack) {
        return Size.SMALL;
    }

    @Override
    public Weight getWeight(ItemStack stack) {
        return Weight.VERY_LIGHT;
    }

}
