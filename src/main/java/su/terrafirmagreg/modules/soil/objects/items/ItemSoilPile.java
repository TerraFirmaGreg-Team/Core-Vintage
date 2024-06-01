package su.terrafirmagreg.modules.soil.objects.items;

import su.terrafirmagreg.api.spi.item.BaseItem;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.item.ISoilItem;
import su.terrafirmagreg.modules.soil.api.types.variant.item.SoilItemVariant;

import su.terrafirmagreg.api.capabilities.size.spi.Size;

import su.terrafirmagreg.api.capabilities.size.spi.Weight;


import lombok.Getter;

@Getter
public class ItemSoilPile extends BaseItem implements ISoilItem {

    private final SoilItemVariant variant;
    private final SoilType type;

    public ItemSoilPile(SoilItemVariant variant, SoilType type) {
        this.variant = variant;
        this.type = type;

        getSettings()
                .size(Size.SMALL)
                .weight(Weight.VERY_LIGHT)
                .addOreDict(variant);
    }

}
