package su.terrafirmagreg.modules.wood.content.item;


import su.terrafirmagreg.framework.manager.content.base.item.spi.BaseItem;
import su.terrafirmagreg.framework.manager.content.provider.IProviderItemColor;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;

import net.minecraft.client.renderer.color.IItemColor;

import lombok.Getter;

@Getter
public class ItemWoodWheel extends BaseItem implements IWoodEntry, IProviderItemColor {

  protected final WoodType type;

  public ItemWoodWheel(WoodType type) {
    super(ItemSettings.of()
      .customResource(type.getResource("wheel"))
      .addOreDict("wheel")
      .capability(CapabilityProviderSize.of(Size.NORMAL, Weight.HEAVY))
    );

    this.type = type;
  }

  @Override
  public IItemColor getItemColor() {
    return (s, i) -> this.getType().getColor();
  }
}
