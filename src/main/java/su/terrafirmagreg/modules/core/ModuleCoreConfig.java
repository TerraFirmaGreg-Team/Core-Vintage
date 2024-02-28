package su.terrafirmagreg.modules.core;

import com.cleanroommc.configanytime.ConfigAnytime;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import su.terrafirmagreg.modules.rock.ModuleRock;

import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.Tags.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/" + "core")
public class ModuleCoreConfig {

	@Config.Name("Blocks")
	@Config.Comment("Block settings")
	@Config.LangKey("config." + MOD_ID + ".core.blocks")
	public static final BlocksCategory BLOCKS = new BlocksCategory();

	@Config.Name("Items")
	@Config.Comment("Items settings")
	@Config.LangKey("config." + MOD_ID + ".core.items")
	public static final ItemsCategory ITEMS = new ItemsCategory();

	@Config.Comment("Miscelaneous")
	@Config.LangKey("config." + MOD_ID + ".core.misc")
	public static final MiscCategory MISC = new MiscCategory();

	public static final class BlocksCategory {


	}

	public static final class ItemsCategory {

		@Config.Comment("Chance for the fire starter to be successful")
		@Config.RangeDouble(min = 0d, max = 1d)
		@Config.LangKey("config." + MOD_ID + ".core.items.fireStarterChance")
		public double fireStarterChance = 0.5;


	}

	public static final class MiscCategory {


	}


	static {
		ConfigAnytime.register(ModuleCoreConfig.class);
	}

	@SubscribeEvent
	public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(MOD_ID)) {
			ModuleRock.LOGGER.warn("Config changed");
			ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);
		}
	}

}
