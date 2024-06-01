package su.terrafirmagreg.modules.wood.objects.items;

import su.terrafirmagreg.api.spi.item.BaseItem;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.item.IWoodItem;
import su.terrafirmagreg.modules.wood.api.types.variant.item.WoodItemVariant;

import net.minecraft.client.renderer.color.IItemColor;

import su.terrafirmagreg.api.capabilities.size.spi.Size;

import su.terrafirmagreg.api.capabilities.size.spi.Weight;


import lombok.Getter;

@Getter
public class ItemWoodWheel extends BaseItem implements IWoodItem {

    private final WoodItemVariant variant;
    private final WoodType type;

    public ItemWoodWheel(WoodItemVariant variant, WoodType type) {
        this.variant = variant;
        this.type = type;

        getSettings()
                .weight(Weight.HEAVY)
                .size(Size.NORMAL)
                .addOreDict(variant)
                .addOreDict(variant, type);
    }

    @Override
    public IItemColor getItemColor() {
        return (s, i) -> this.getType().getColor();
    }
}
