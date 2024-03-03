package su.terrafirmagreg.modules.wood;


import com.cleanroommc.configanytime.ConfigAnytime;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.Tags.MOD_NAME;
import static su.terrafirmagreg.api.lib.Constants.MODID_TFC;

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

		@Config.Comment("Barrel")
		@Config.LangKey("config." + MOD_ID + ".wood.barrel")
		public final Barrel BARREL = new Barrel();

		@Config.Comment("Barrel")
		@Config.LangKey("config." + MOD_ID + ".wood.barrel")
		public final Support SUPPORT = new Support();

		public static final class Barrel {

			@Config.Comment("How much fluid (mB) can a barrel hold?")
			@Config.RangeInt(min = 100)
			@Config.LangKey("config." + MOD_ID + ".wood.barrel.tank")
			public int tank = 10_000;

			@Config.Comment("List of fluids allowed to be inserted into a barrel.")
			@Config.LangKey("config." + MOD_ID + ".wood.barrel.fluidWhitelist")
			public String[] fluidWhitelist = {
					"fresh_water",
					"hot_water",
					"salt_water",
					"water",
					"limewater",
					"tannin",
					"olive_oil",
					"olive_oil_water",
					"vinegar",
					"rum",
					"beer",
					"whiskey",
					"rye_whiskey",
					"corn_whiskey",
					"sake",
					"vodka",
					"cider",
					"brine",
					"milk",
					"milk_curdled",
					"milk_vinegar",
					"white_dye",
					"orange_dye",
					"magenta_dye",
					"light_blue_dye",
					"yellow_dye",
					"lime_dye",
					"pink_dye",
					"gray_dye",
					"light_gray_dye",
					"cyan_dye",
					"purple_dye",
					"blue_dye",
					"brown_dye",
					"green_dye",
					"red_dye",
					"black_dye"
			};
		}


		public static final class Support {

			@Config.Comment("Horizontal radius of the support range of support beams.")
			@Config.RangeInt(min = 0, max = 8)
			@Config.LangKey("config." + MODID_TFC + ".general.fallable.supportBeamRangeHor")
			public int supportBeamRangeHor = 4;

			@Config.Comment("Upwards support range of support beams.")
			@Config.RangeInt(min = 0, max = 3)
			@Config.LangKey("config." + MODID_TFC + ".general.fallable.supportBeamRangeUp")
			public int supportBeamRangeUp = 1;

			@Config.Comment("Downwards support range of support beams.")
			@Config.RangeInt(min = 0, max = 3)
			@Config.LangKey("config." + MODID_TFC + ".general.fallable.supportBeamRangeDown")
			public int supportBeamRangeDown = 1;
		}
	}

	public static final class ItemsCategory {

		public final SupplyCart SUPPLY_CART = new SupplyCart();
		public final Plow PLOW = new Plow();
		public final AnimalCart ANIMAL_CART = new AnimalCart();

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

	}

	public static final class MiscCategory {

		@Config.RequiresMcRestart
		@Config.RangeDouble(min = -1.0D, max = 0.0D)
		public final double SPEED_MODIFIER = -0.65D;


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
