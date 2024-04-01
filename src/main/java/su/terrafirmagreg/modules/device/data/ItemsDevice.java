package su.terrafirmagreg.modules.device.data;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.device.objects.items.*;


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


	public static void onRegister(RegistryManager registry) {

		FIRESTARTER = registry.registerItem(new ItemFireStarter());

		LEATHER_SIDE = registry.registerItem(new ItemDeviceMisc("flask/leather/side", Size.SMALL, Weight.LIGHT));
		LEATHER_FLASK = registry.registerItem(new ItemLeatherFlask());
		BROKEN_LEATHER_FLASK = registry.registerItem(new ItemDeviceMisc("flask/leather/broken", Size.SMALL, Weight.LIGHT));

		UNFINISHED_FLASK = registry.registerItem(new ItemDeviceMisc("flask/metal/unfinished", Size.SMALL, Weight.LIGHT));
		IRON_FLASK = registry.registerItem(new ItemMetalFlask());
		BROKEN_IRON_FLASK = registry.registerItem(new ItemDeviceMisc("flask/metal/broken", Size.SMALL, Weight.LIGHT));

		SLING = registry.registerItem(new ItemSling());
		SLING_REINFORCED = registry.registerItem(new ItemSlingReinforced());
		SLING_AMMO = registry.registerItem(new ItemSlingAmmo(0, "heavy"));
		SLING_AMMO_SPREAD = registry.registerItem(new ItemSlingAmmo(1, "spread"));
		SLING_AMMO_LIGHT = registry.registerItem(new ItemSlingAmmo(2, "light"));
		SLING_AMMO_FIRE = registry.registerItem(new ItemSlingAmmo(3, "fire"));
	}
}
