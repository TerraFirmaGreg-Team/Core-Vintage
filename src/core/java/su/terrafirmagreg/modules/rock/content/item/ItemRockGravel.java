package su.terrafirmagreg.modules.rock.content.item;

import su.terrafirmagreg.framework.manager.content.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.feature.rocktype.types.IRockEntry;

import lombok.Getter;

@Getter
public class ItemRockGravel extends BaseItem implements IRockEntry {
  
  protected final RockType type;

  public ItemRockGravel(RockType type) {
    super(ItemSettings.of()
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.LIGHT))
      .addOreDict("gravel_layer")
      .addOreDict("gravel_layer", type)
      .addOreDict("gravel_layer", type.getCategory())
    );

    this.type = type;
  }
}
