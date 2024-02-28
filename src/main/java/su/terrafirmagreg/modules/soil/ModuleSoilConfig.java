package su.terrafirmagreg.modules.soil;

import com.cleanroommc.configanytime.ConfigAnytime;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import su.terrafirmagreg.modules.soil.client.GrassColorHandler;

import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.Tags.MOD_NAME;
import static su.terrafirmagreg.api.lib.Constants.MODID_TFC;

@Config(modid = MOD_ID, name = MOD_NAME + "/" + "soil")
public class ModuleSoilConfig {

	@Config.Name("Blocks")
	@Config.Comment("Block settings")
	@Config.LangKey("config." + MOD_ID + ".soil.blocks")
	public static final BlocksCategory BLOCKS = new BlocksCategory();

	@Config.Name("Items")
	@Config.Comment("Item settings")
	@Config.LangKey("config." + MOD_ID + ".soil.items")
	public static final ItemsCategory ITEMS = new ItemsCategory();

	@Config.Name("Misc")
	@Config.Comment("Miscellaneous")
	@Config.LangKey("config." + MOD_ID + ".soil.misc")
	public static final MiscCategory MISC = new MiscCategory();

	@Config.Name("Client")
	@Config.Comment("Client Side")
	@Config.LangKey("config." + MOD_ID + ".soil.client")
	public static final ClientCategory CLIENT = new ClientCategory();

	public static final class BlocksCategory {


	}

	public static final class ItemsCategory {


	}

	public static final class MiscCategory {


	}

	public static final class ClientCategory {

		@Config.Comment("If true, grass and foliage will be slightly varied in color.")
		@Config.LangKey("config." + MODID_TFC + ".client.grassColor.noiseEnable")
		public boolean noiseEnable = true;

		@Config.Comment("If true, grass and foliage will be colored seasonally.")
		@Config.LangKey("config." + MODID_TFC + ".client.grassColor.seasonColorEnable")
		public boolean seasonColorEnable = true;

		@Config.Comment("The noise scale. Default = 10")
		@Config.LangKey("config." + MODID_TFC + ".client.grassColor.noiseScale")
		public float noiseScale = 10f;

		@Config.Comment("How many darkness levels should the noise have? Default = 5")
		@Config.LangKey("config." + MODID_TFC + ".client.grassColor.noiseLevels")
		public int noiseLevels = 5;

		@Config.Comment("How potent should the darkness be? Default = 0.15")
		@Config.LangKey("config." + MODID_TFC + ".client.grassColor.noiseDarkness")
		public float noiseDarkness = 0.15f;

		@Config.Comment("ARGB code for summer coloring in hexadecimal. Default: 1155FF44")
		@Config.LangKey("config." + MODID_TFC + ".client.grassColor.seasonColorSummer")
		public String seasonColorSummer = "1155FF44";

		@Config.Comment("ARGB code for summer coloring in hexadecimal. Default: 55FFDD44")
		@Config.LangKey("config." + MODID_TFC + ".client.grassColor.seasonColorAutumn")
		public String seasonColorAutumn = "55FFDD44";

		@Config.Comment("ARGB code for winter coloring in hexadecimal. Default: 335566FF")
		@Config.LangKey("config." + MODID_TFC + ".client.grassColor.seasonColorWinter")
		public String seasonColorWinter = "335566FF";

		@Config.Comment("ARGB code for spring coloring in hexadecimal. Default: 3355FFBB")
		@Config.LangKey("config." + MODID_TFC + ".client.grassColor.seasonColorSpring")
		public String seasonColorSpring = "3355FFBB";


	}

	static {
		ConfigAnytime.register(ModuleSoilConfig.class);
	}

	@Mod.EventBusSubscriber(modid = MOD_ID)
	public static class EventHandler {
		@SubscribeEvent
		public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(MOD_ID)) {
				ModuleSoil.LOGGER.warn("Config changed");
				ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);

				GrassColorHandler.resetColors();
			}
		}
	}


}
