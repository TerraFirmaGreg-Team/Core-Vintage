package su.terrafirmagreg.modules.rock.object.item;

import su.terrafirmagreg.framework.manager.registry.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.feature.rocktype.types.IRockEntry;

import lombok.Getter;

@Getter
public class ItemRockBrick extends BaseItem implements IRockEntry {

  public static final String NAME = "brick";
  protected final RockType type;

  public ItemRockBrick(RockType type) {
    super(ItemSettings.of()
      .registryKey(type.getRegistryKey(NAME))
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.LIGHT))
      .addOreDict(NAME)
      .addOreDict(NAME, type)
      .addOreDict(NAME, type.getCategory())
    );

    this.type = type;
  }
}
