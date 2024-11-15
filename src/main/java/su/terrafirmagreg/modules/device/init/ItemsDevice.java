package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.device.object.item.ItemCraftingTool;
import su.terrafirmagreg.modules.device.object.item.ItemDeviceMisc;
import su.terrafirmagreg.modules.device.object.item.ItemFireStarter;
import su.terrafirmagreg.modules.device.object.item.ItemGrindstone;
import su.terrafirmagreg.modules.device.object.item.ItemLeatherFlask;
import su.terrafirmagreg.modules.device.object.item.ItemMetalFlask;
import su.terrafirmagreg.modules.device.object.item.ItemSling;
import su.terrafirmagreg.modules.device.object.item.ItemSlingAmmo;
import su.terrafirmagreg.modules.device.object.item.ItemSlingReinforced;

public final class ItemsDevice {

  public static ItemFireStarter FIRESTARTER;
  public static ItemDeviceMisc LEATHER_SIDE;
  public static ItemLeatherFlask LEATHER_FLASK;
  public static ItemDeviceMisc BROKEN_LEATHER_FLASK;
  public static ItemDeviceMisc UNFINISHED_FLASK;
  public static ItemMetalFlask IRON_FLASK;
  public static ItemDeviceMisc BROKEN_IRON_FLASK;
  public static ItemSling SLING;
  public static ItemSlingReinforced SLING_REINFORCED;
  public static ItemSlingAmmo SLING_AMMO;
  public static ItemSlingAmmo SLING_AMMO_SPREAD;
  public static ItemSlingAmmo SLING_AMMO_LIGHT;
  public static ItemSlingAmmo SLING_AMMO_FIRE;
  public static ItemCraftingTool HANDSTONE;
  public static ItemGrindstone GRINDSTONE_QUARTZ;
  public static ItemGrindstone GRINDSTONE_STEEL;
  public static ItemGrindstone GRINDSTONE_DIAMOND;

  public static void onRegister(RegistryManager registryManager) {

    FIRESTARTER = registryManager.item(new ItemFireStarter());

    LEATHER_SIDE = registryManager.item(
      new ItemDeviceMisc("flask/leather/side", Size.SMALL, Weight.LIGHT));
    LEATHER_FLASK = registryManager.item(new ItemLeatherFlask());
    BROKEN_LEATHER_FLASK = registryManager.item(
      new ItemDeviceMisc("flask/leather/broken", Size.SMALL, Weight.LIGHT));

    UNFINISHED_FLASK = registryManager.item(
      new ItemDeviceMisc("flask/metal/unfinished", Size.SMALL, Weight.LIGHT));
    IRON_FLASK = registryManager.item(new ItemMetalFlask());
    BROKEN_IRON_FLASK = registryManager.item(
      new ItemDeviceMisc("flask/metal/broken", Size.SMALL, Weight.LIGHT));

    SLING = registryManager.item(new ItemSling("normal"));
    SLING_REINFORCED = registryManager.item(new ItemSlingReinforced("reinforced"));
    SLING_AMMO = registryManager.item(new ItemSlingAmmo(0, "heavy"));
    SLING_AMMO_SPREAD = registryManager.item(new ItemSlingAmmo(1, "spread"));
    SLING_AMMO_LIGHT = registryManager.item(new ItemSlingAmmo(2, "light"));
    SLING_AMMO_FIRE = registryManager.item(new ItemSlingAmmo(3, "fire"));
    HANDSTONE = registryManager.item(
      new ItemCraftingTool("handstone", 250, Size.NORMAL, Weight.VERY_HEAVY, "handstone"));

    GRINDSTONE_QUARTZ = registryManager.item(new ItemGrindstone(1, 640, "quartz"));
    GRINDSTONE_STEEL = registryManager.item(new ItemGrindstone(2, 6400, "steel"));
    GRINDSTONE_DIAMOND = registryManager.item(new ItemGrindstone(3, 7000, "diamond"));
  }
}
