package su.terrafirmagreg.modules.soil.object.item;

import su.terrafirmagreg.api.library.types.type.IType;
import su.terrafirmagreg.framework.manager.registry.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;
import su.terrafirmagreg.modules.soil.feature.soiltype.spi.type.SoilType;

import lombok.Getter;

@Getter
public abstract class ItemSoil extends BaseItem implements IType<SoilType> {

  protected final SoilType type;

  public ItemSoil(SoilType type) {

    this.type = type;

    getSettings()
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.LIGHT));
  }

}
