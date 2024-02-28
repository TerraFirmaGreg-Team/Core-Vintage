package su.terrafirmagreg.modules.wood;


import com.cleanroommc.configanytime.ConfigAnytime;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.Tags.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/" + "wood")
public class ModuleWoodConfig {

	@Config.Name("Blocks")
	@Config.Comment("Block settings")
	@Config.LangKey("config." + MOD_ID + ".wood.blocks")
	public static final BlocksCategory BLOCKS = new BlocksCategory();

	@Config.Name("Items")
	@Config.Comment("Item settings")
	@Config.LangKey("config." + MOD_ID + ".wood.items")
	public static final ItemsCategory ITEMS = new ItemsCategory();

	@Config.Name("Misc")
	@Config.Comment("Miscellaneous")
	@Config.LangKey("config." + MOD_ID + ".wood.misc")
	public static final MiscCategory MISC = new MiscCategory();

	public static final class BlocksCategory {


	}

	public static final class ItemsCategory {


	}

	public static final class MiscCategory {


	}

	@Config.RequiresMcRestart
	@Config.RangeDouble(min = -1.0D, max = 0.0D)
	public static double SPEED_MODIFIER = -0.65D;

	public static SupplyCart SUPPLY_CART = new SupplyCart();
	public static Plow PLOW = new Plow();
	public static AnimalCart ANIMAL_CART = new AnimalCart();

	public static class SupplyCart {

		public String[] canPull = {
				"minecraft:donkey",
				"minecraft:horse",
				"minecraft:mule",
				"minecraft:pig",
				"minecraft:player",
				"tfc:cameltfc",
				"tfc:donkeytfc",
				"tfc:horsetfc",
				"tfc:muletfc"
		};
	}

	public static class Plow {

		public String[] canPull = {
				"minecraft:donkey",
				"minecraft:horse",
				"minecraft:mule",
				"minecraft:pig",
				"minecraft:player",
				"tfc:cameltfc",
				"tfc:donkeytfc",
				"tfc:horsetfc",
				"tfc:muletfc"
		};
	}

	public static class AnimalCart {

		public String[] canPull = {
				"minecraft:donkey",
				"minecraft:horse",
				"minecraft:mule",
				"minecraft:pig",
				"minecraft:player",
				"tfc:cameltfc",
				"tfc:donkeytfc",
				"tfc:horsetfc",
				"tfc:muletfc"
		};
	}

	static {
		ConfigAnytime.register(ModuleWoodConfig.class);
	}

	@Mod.EventBusSubscriber(modid = MOD_ID)
	public static class EventHandler {
		@SubscribeEvent
		public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(MOD_ID)) {
				ModuleWood.LOGGER.warn("Config changed");
				ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);
			}
		}
	}


}
