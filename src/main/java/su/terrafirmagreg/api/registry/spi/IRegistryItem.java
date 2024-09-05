package su.terrafirmagreg.api.registry.spi;

import su.terrafirmagreg.api.base.item.spi.IItemSettings;

import net.minecraft.item.Item;


import java.util.Collection;

public interface IRegistryItem
    extends IRegistryBase {

  default <T extends Item> void items(Collection<T> collection) {
    for (var item : collection) {
      if (item instanceof IItemSettings provider) {
        this.item(item, provider.getRegistryKey());
      }
    }
  }

  default <T extends Item & IItemSettings> T item(T item) {

    return this.item(item, item.getRegistryKey());
  }

  /**
   * Registers an item to the game. This will also set the unlocalized name, and creative tab if {@link #tab} has been set. The item will also be
   * cached in {@link #items}.
   *
   * @param item The item to register.
   * @param name The name to register the item with.
   */
  default <T extends Item> T item(T item, String name) {

    item.setRegistryName(this.getModID(), name);
    item.setTranslationKey(
        this.getModID() + "." + name.toLowerCase().replace("_", ".").replaceAll("/", "."));
    if (this.getTab() != null) {
      item.setCreativeTab(this.getTab());
    }

    this.getRegistry().getItems().add(item);

    return item;
  }

}
