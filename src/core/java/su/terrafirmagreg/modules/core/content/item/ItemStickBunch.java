package su.terrafirmagreg.modules.core.content.item;


import su.terrafirmagreg.framework.manager.content.base.item.spi.BaseItem;

public class ItemStickBunch extends BaseItem {

  public ItemStickBunch() {
    super(ItemSettings.of()
      .addOreDict("log_wood")
    );
  }
}
