package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.framework.manager.content.api.IContentRegistrar;
import su.terrafirmagreg.modules.device.content.item.ItemFireStarter;
import su.terrafirmagreg.modules.device.content.item.ItemFlaskLeather;
import su.terrafirmagreg.modules.device.content.item.ItemFlaskLeatherBroken;
import su.terrafirmagreg.modules.device.content.item.ItemFlaskLeatherUnfinished;
import su.terrafirmagreg.modules.device.content.item.ItemFlaskMetal;
import su.terrafirmagreg.modules.device.content.item.ItemFlaskMetalBroken;
import su.terrafirmagreg.modules.device.content.item.ItemFlaskMetalUnfinished;
import su.terrafirmagreg.modules.device.content.item.ItemWoodBucket;

import net.minecraft.item.Item;

public final class ItemsDevice {

  public static ItemFireStarter FIRESTARTER;
  public static ItemFlaskLeatherUnfinished LEATHER_FLASK_UNFINISHED;
  public static ItemFlaskLeather LEATHER_FLASK;
  public static ItemFlaskLeatherBroken LEATHER_FLASK_BROKEN;
  public static ItemFlaskMetalUnfinished METAL_FLASK_UNFINISHED;
  public static ItemFlaskMetal METAL_FLASK;
  public static ItemFlaskMetalBroken METAL_FLASK_BROKEN;
  public static ItemWoodBucket WOODEN_BUCKET;
  public static Item SLING;
  public static Item SLING_REINFORCED;
  public static Item SLING_AMMO;
  public static Item SLING_AMMO_SPREAD;
  public static Item SLING_AMMO_LIGHT;
  public static Item SLING_AMMO_FIRE;
  public static Item HANDSTONE;
  public static Item GRINDSTONE_QUARTZ;
  public static Item GRINDSTONE_STEEL;
  public static Item GRINDSTONE_DIAMOND;

  public static void onRegister(IContentRegistrar registrar) {

    FIRESTARTER = registrar.addItem(new ItemFireStarter());

    LEATHER_FLASK_UNFINISHED = registrar.addItem(new ItemFlaskLeatherUnfinished());
    LEATHER_FLASK = registrar.addItem(new ItemFlaskLeather());
    LEATHER_FLASK_BROKEN = registrar.addItem(new ItemFlaskLeatherBroken());

    METAL_FLASK_UNFINISHED = registrar.addItem(new ItemFlaskMetalUnfinished());
    METAL_FLASK = registrar.addItem(new ItemFlaskMetal());
    METAL_FLASK_BROKEN = registrar.addItem(new ItemFlaskMetalBroken());
    WOODEN_BUCKET = registrar.addItem(new ItemWoodBucket());
  }
}
