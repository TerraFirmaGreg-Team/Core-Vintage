package su.terrafirmagreg.modules.soil.init;

import net.minecraft.item.Item;
import su.terrafirmagreg.api.modules.registry.Registry;
import su.terrafirmagreg.modules.soil.StorageSoil;

public final class ItemsSoil {

	public static void onRegister(Registry registry) {

		for (var item : StorageSoil.SOIL_ITEMS.values()) registry.registerItem((Item) item, item.getName());
	}


	public static void onClientRegister(Registry registry) {

	}
}
