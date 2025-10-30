package su.terrafirmagreg.modules.rock.content.item;

import su.terrafirmagreg.framework.manager.content.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.feature.rocktype.types.IRockEntry;

import lombok.Getter;

@Getter
public class ItemRockBrick extends BaseItem implements IRockEntry {
  
  protected final RockType type;

  public ItemRockBrick(RockType type) {
    super(ItemSettings.of()
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.LIGHT))
      .addOreDict("brick")
      .addOreDict("brick", type)
      .addOreDict("brick", type.getCategory())
    );

    this.type = type;
  }
}
