package su.terrafirmagreg.modules.soil.objects.items;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.api.base.item.BaseItem;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.item.ISoilItem;
import su.terrafirmagreg.modules.soil.api.types.variant.item.SoilItemVariant;


import lombok.Getter;

@Getter
public abstract class ItemSoil extends BaseItem implements ISoilItem {

    private final SoilItemVariant variant;
    private final SoilType type;

    public ItemSoil(SoilItemVariant variant, SoilType type) {
        this.variant = variant;
        this.type = type;

        getSettings()
                .weight(Weight.LIGHT)
                .size(Size.SMALL)
                .addOreDict(variant);
    }

}
