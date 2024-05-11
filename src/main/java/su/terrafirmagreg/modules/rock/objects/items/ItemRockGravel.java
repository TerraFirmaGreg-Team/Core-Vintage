package su.terrafirmagreg.modules.rock.objects.items;

import su.terrafirmagreg.api.spi.item.BaseItem;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.item.IRockItem;
import su.terrafirmagreg.modules.rock.api.types.variant.item.RockItemVariant;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import lombok.Getter;

@Getter
public class ItemRockGravel extends BaseItem implements IRockItem {

    private final RockItemVariant variant;
    private final RockType type;

    public ItemRockGravel(RockItemVariant variant, RockType type) {
        this.variant = variant;
        this.type = type;

        getSettings()
                .size(Size.SMALL)
                .weight(Weight.LIGHT)
                .addOreDict(variant)
                .addOreDict(variant, type.getRockCategory());
    }
}
