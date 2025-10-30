package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.framework.manager.content.api.IContentRegistrar;
import su.terrafirmagreg.modules.device.content.item.ItemFireStarter;
import su.terrafirmagreg.modules.device.content.item.ItemFlaskBroken;
import su.terrafirmagreg.modules.device.content.item.ItemFlaskLeather;
import su.terrafirmagreg.modules.device.content.item.ItemFlaskMetal;
import su.terrafirmagreg.modules.device.content.item.ItemFlaskUnfinished;
import su.terrafirmagreg.modules.device.content.item.ItemWoodBucket;

import net.minecraft.item.Item;

public final class ItemsDevice {

  public static ItemFireStarter FIRESTARTER;
  public static ItemFlaskUnfinished LEATHER_FLASK_UNFINISHED;
  public static ItemFlaskBroken LEATHER_FLASK_BROKEN;
  public static ItemFlaskLeather LEATHER_FLASK;

  public static ItemFlaskUnfinished METAL_FLASK_UNFINISHED;
  public static ItemFlaskBroken METAL_FLASK_BROKEN;
  public static ItemFlaskMetal METAL_FLASK;

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

    FIRESTARTER = registrar.addItem("firestarter", new ItemFireStarter());

    LEATHER_FLASK_UNFINISHED = registrar.addItem("flask/leather/unfinished", new ItemFlaskUnfinished());
    LEATHER_FLASK_BROKEN = registrar.addItem("flask/leather/broken", new ItemFlaskBroken());
    LEATHER_FLASK = registrar.addItem("flask/leather", new ItemFlaskLeather());

    METAL_FLASK_UNFINISHED = registrar.addItem("flask/metal/unfinished", new ItemFlaskUnfinished());
    METAL_FLASK_BROKEN = registrar.addItem("flask/metal/broken", new ItemFlaskBroken());
    METAL_FLASK = registrar.addItem("flask/metal", new ItemFlaskMetal());

    WOODEN_BUCKET = registrar.addItem("bucket/wood", new ItemWoodBucket());
  }
}
