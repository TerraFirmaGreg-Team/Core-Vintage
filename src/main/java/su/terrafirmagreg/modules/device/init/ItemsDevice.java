package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.framework.registry.api.IRegistryManager;
import su.terrafirmagreg.modules.device.object.item.ItemFireStarter;
import su.terrafirmagreg.modules.device.object.item.ItemFlaskLeather;
import su.terrafirmagreg.modules.device.object.item.ItemFlaskLeatherBroken;
import su.terrafirmagreg.modules.device.object.item.ItemFlaskLeatherUnfinished;
import su.terrafirmagreg.modules.device.object.item.ItemFlaskMetal;
import su.terrafirmagreg.modules.device.object.item.ItemFlaskMetalBroken;
import su.terrafirmagreg.modules.device.object.item.ItemFlaskMetalUnfinished;

import net.minecraft.item.Item;

import java.util.function.Supplier;

public final class ItemsDevice {

  public static Supplier<ItemFireStarter> FIRESTARTER;
  public static Supplier<ItemFlaskLeatherUnfinished> LEATHER_FLASK_UNFINISHED;
  public static Supplier<ItemFlaskLeather> LEATHER_FLASK;
  public static Supplier<ItemFlaskLeatherBroken> LEATHER_FLASK_BROKEN;
  public static Supplier<ItemFlaskMetalUnfinished> METAL_FLASK_UNFINISHED;
  public static Supplier<ItemFlaskMetal> METAL_FLASK;
  public static Supplier<ItemFlaskMetalBroken> METAL_FLASK_BROKEN;
  public static Supplier<Item> SLING;
  public static Supplier<Item> SLING_REINFORCED;
  public static Supplier<Item> SLING_AMMO;
  public static Supplier<Item> SLING_AMMO_SPREAD;
  public static Supplier<Item> SLING_AMMO_LIGHT;
  public static Supplier<Item> SLING_AMMO_FIRE;
  public static Supplier<Item> HANDSTONE;
  public static Supplier<Item> GRINDSTONE_QUARTZ;
  public static Supplier<Item> GRINDSTONE_STEEL;
  public static Supplier<Item> GRINDSTONE_DIAMOND;

  public static void onRegister(IRegistryManager registry) {

    FIRESTARTER = registry.item(new ItemFireStarter());

    LEATHER_FLASK_UNFINISHED = registry.item(new ItemFlaskLeatherUnfinished());
    LEATHER_FLASK = registry.item(new ItemFlaskLeather());
    LEATHER_FLASK_BROKEN = registry.item(new ItemFlaskLeatherBroken());

    METAL_FLASK_UNFINISHED = registry.item(new ItemFlaskMetalUnfinished());
    METAL_FLASK = registry.item(new ItemFlaskMetal());
    METAL_FLASK_BROKEN = registry.item(new ItemFlaskMetalBroken());
  }
}
