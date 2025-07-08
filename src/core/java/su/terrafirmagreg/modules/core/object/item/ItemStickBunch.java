package su.terrafirmagreg.modules.core.object.item;


import su.terrafirmagreg.framework.manager.registry.base.item.spi.BaseItem;

public class ItemStickBunch extends BaseItem {

  public ItemStickBunch() {

    getSettings()
      .registryKey("stick_bunch")
      .oreDict("log_wood");
  }
}
