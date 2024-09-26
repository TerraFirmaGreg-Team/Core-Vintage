package su.terrafirmagreg.modules.animal.object.item;

import su.terrafirmagreg.api.base.item.BaseItem;

public class ItemAnimalMisc extends BaseItem {

  public ItemAnimalMisc(String name, Settings settings) {
    super(settings);

    getSettings()
      .registryKey("animal/" + name);
  }

}
