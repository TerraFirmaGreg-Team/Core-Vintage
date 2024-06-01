package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.device.objects.items.ItemCraftingTool;
import su.terrafirmagreg.modules.device.objects.items.ItemDeviceMisc;
import su.terrafirmagreg.modules.device.objects.items.ItemFireStarter;
import su.terrafirmagreg.modules.device.objects.items.ItemGrindstone;
import su.terrafirmagreg.modules.device.objects.items.ItemLeatherFlask;
import su.terrafirmagreg.modules.device.objects.items.ItemMetalFlask;
import su.terrafirmagreg.modules.device.objects.items.ItemSling;
import su.terrafirmagreg.modules.device.objects.items.ItemSlingAmmo;
import su.terrafirmagreg.modules.device.objects.items.ItemSlingReinforced;

import su.terrafirmagreg.api.capabilities.size.spi.Size;

import su.terrafirmagreg.api.capabilities.size.spi.Weight;

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

    public static void onRegister(RegistryManager registry) {

        FIRESTARTER = registry.item(new ItemFireStarter());

        LEATHER_SIDE = registry.item(new ItemDeviceMisc("flask/leather/side", Size.SMALL, Weight.LIGHT));
        LEATHER_FLASK = registry.item(new ItemLeatherFlask());
        BROKEN_LEATHER_FLASK = registry.item(new ItemDeviceMisc("flask/leather/broken", Size.SMALL, Weight.LIGHT));

        UNFINISHED_FLASK = registry.item(new ItemDeviceMisc("flask/metal/unfinished", Size.SMALL, Weight.LIGHT));
        IRON_FLASK = registry.item(new ItemMetalFlask());
        BROKEN_IRON_FLASK = registry.item(new ItemDeviceMisc("flask/metal/broken", Size.SMALL, Weight.LIGHT));

        SLING = registry.item(new ItemSling("normal"));
        SLING_REINFORCED = registry.item(new ItemSlingReinforced("reinforced"));
        SLING_AMMO = registry.item(new ItemSlingAmmo(0, "heavy"));
        SLING_AMMO_SPREAD = registry.item(new ItemSlingAmmo(1, "spread"));
        SLING_AMMO_LIGHT = registry.item(new ItemSlingAmmo(2, "light"));
        SLING_AMMO_FIRE = registry.item(new ItemSlingAmmo(3, "fire"));
        HANDSTONE = registry.item(new ItemCraftingTool("handstone", 250, Size.NORMAL, Weight.VERY_HEAVY, "handstone"));

        GRINDSTONE_QUARTZ = registry.item(new ItemGrindstone(1, 640, "quartz"));
        GRINDSTONE_STEEL = registry.item(new ItemGrindstone(2, 6400, "steel"));
        GRINDSTONE_DIAMOND = registry.item(new ItemGrindstone(3, 7000, "diamond"));
    }
}
