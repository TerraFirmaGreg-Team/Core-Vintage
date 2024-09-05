package su.terrafirmagreg.modules.device.objects.items;

import su.terrafirmagreg.api.base.item.BaseItem;


import lombok.Getter;

@Getter
public class ItemSlingAmmo extends BaseItem {

  private final int type;

  public ItemSlingAmmo(int type, String name) {
    this.type = type;

    getSettings().registryKey("device/sling/ammo/" + name);
  }
}
