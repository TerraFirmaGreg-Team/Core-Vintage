package su.terrafirmagreg.modules.device.object.item;

import su.terrafirmagreg.api.base.item.BaseItem;

import lombok.Getter;

@Getter
public class ItemSlingAmmo extends BaseItem {

  private final int tier;

  public ItemSlingAmmo(int tier, String name) {
    this.tier = tier;

    getSettings().registryKey("device/sling/ammo/" + name);
  }
}
