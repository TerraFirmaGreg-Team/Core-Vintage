package su.terrafirmagreg.modules.core.object.item;


import su.terrafirmagreg.framework.manager.registry.base.item.spi.BaseItem;

public class ItemStickBunch extends BaseItem {

  public ItemStickBunch() {
    super(ItemSettings.of()
      .registryKey("stick_bunch")
      .addOreDict("log_wood")
    );
  }
}
