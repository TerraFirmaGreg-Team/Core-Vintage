package su.terrafirmagreg.modules.rock.object.item;

import su.terrafirmagreg.api.base.item.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.item.IRockItem;
import su.terrafirmagreg.modules.rock.api.types.variant.item.RockItemVariant;


import lombok.Getter;

@Getter
public abstract class ItemRock extends BaseItem implements IRockItem {

  protected final RockItemVariant variant;
  protected final RockType type;

  public ItemRock(RockItemVariant variant, RockType type) {
    this.variant = variant;
    this.type = type;

    getSettings()
            .registryKey(variant.getRegistryKey(type))
            .size(Size.SMALL)
            .weight(Weight.LIGHT)
            .oreDict(variant)
            .oreDict(variant, type)
            .oreDict(variant, type.getCategory());
  }
}
