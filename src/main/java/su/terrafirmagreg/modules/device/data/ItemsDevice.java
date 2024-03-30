package su.terrafirmagreg.modules.device.data;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
	public static ItemReinforcedSling SLING_REINFORCED;
	public static ItemSlingAmmo SLING_AMMO;
	public static ItemSlingAmmo SLING_AMMO_SPREAD;
	public static ItemSlingAmmo SLING_AMMO_LIGHT;
	public static ItemSlingAmmo SLING_AMMO_FIRE;


	public static void onRegister(RegistryManager registry) {

		registry.registerAuto(FIRESTARTER = new ItemFireStarter());

		registry.registerAuto(LEATHER_SIDE = new ItemDeviceMisc("flask/leather/side", Size.SMALL, Weight.LIGHT));
		registry.registerAuto(LEATHER_FLASK = new ItemLeatherFlask());
		registry.registerAuto(BROKEN_LEATHER_FLASK = new ItemDeviceMisc("flask/leather/broken", Size.SMALL, Weight.LIGHT));

		registry.registerAuto(UNFINISHED_FLASK = new ItemDeviceMisc("flask/metal/unfinished", Size.SMALL, Weight.LIGHT));
		registry.registerAuto(IRON_FLASK = new ItemMetalFlask());
		registry.registerAuto(BROKEN_IRON_FLASK = new ItemDeviceMisc("flask/metal/broken", Size.SMALL, Weight.LIGHT));

		registry.registerAuto(SLING = new ItemSling());
		registry.registerAuto(SLING_REINFORCED = new ItemReinforcedSling());
		registry.registerAuto(SLING_AMMO = new ItemSlingAmmo(0, "heavy"));
		registry.registerAuto(SLING_AMMO_SPREAD = new ItemSlingAmmo(1, "spread"));
		registry.registerAuto(SLING_AMMO_LIGHT = new ItemSlingAmmo(2, "light"));
		registry.registerAuto(SLING_AMMO_FIRE = new ItemSlingAmmo(3, "fire"));

	}


	@SideOnly(Side.CLIENT)
	public static void onClientRegister(RegistryManager registry) {

	}
}
