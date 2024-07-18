package su.terrafirmagreg.modules.rock.objects.items;

import su.terrafirmagreg.api.capabilities.size.spi.Size;
import su.terrafirmagreg.api.capabilities.size.spi.Weight;
import su.terrafirmagreg.api.spi.item.BaseItem;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.item.IRockItem;
import su.terrafirmagreg.modules.rock.api.types.variant.item.RockItemVariant;


import lombok.Getter;

@Getter
public abstract class ItemRock extends BaseItem implements IRockItem {

    private final RockItemVariant variant;
    private final RockType type;

    public ItemRock(RockItemVariant variant, RockType type) {
        this.variant = variant;
        this.type = type;

        getSettings()
                .size(Size.SMALL)
                .weight(Weight.LIGHT)
                .addOreDict(variant)
                .addOreDict(variant, type.getRockCategory());
    }
}
