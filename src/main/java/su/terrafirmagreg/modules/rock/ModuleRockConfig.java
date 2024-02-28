package su.terrafirmagreg.modules.rock;

import com.cleanroommc.configanytime.ConfigAnytime;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static su.terrafirmagreg.api.lib.Constants.MODID_TFC;
import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.Tags.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/" + "rock")
public class ModuleRockConfig {

	@Config.Name("Blocks")
	@Config.Comment("Block settings")
	@Config.LangKey("config." + MOD_ID + ".rock.blocks")
	public static final BlocksCategory BLOCKS = new BlocksCategory();

	@Config.Comment("Miscelaneous")
	@Config.LangKey("config." + MOD_ID + ".rock.misc")
	public static final MiscCategory MISC = new MiscCategory();

	public static final class BlocksCategory {

		@Config.Comment("Enable the creation of stone anvils.")
		@Config.LangKey("config." + MODID_TFC + ".rock.blocks.enableStoneAnvil")
		public boolean enableStoneAnvil = true;

	}

	public static final class MiscCategory {

		@Config.Comment({"Chance that mining a raw stone will drop a gem.",
				"Gem grade is random from: 16/31 Chipped, 8/31 Flawed, 4/31 Normal, 2/31 Flawless and 1/31 Exquisite."})
		@Config.RangeDouble(min = 0, max = 1)
		@Config.LangKey("config." + MOD_ID + ".rock.misc.stoneGemDropChance")
		public double stoneGemDropChance = 31.0 / 8000.0; // 0.003875
	}


	static {
		ConfigAnytime.register(ModuleRockConfig.class);
	}

	@SubscribeEvent
	public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(MOD_ID)) {
			ModuleRock.LOGGER.warn("Config changed");
			ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);
		}
	}

}
