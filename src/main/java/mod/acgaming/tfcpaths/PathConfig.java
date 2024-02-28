package mod.acgaming.tfcpaths;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static su.terrafirmagreg.api.lib.Constants.MODID_TFCPATHS;

@Config(modid = MODID_TFCPATHS, name = TFCPaths.NAME)
public class PathConfig {
	@Config.Comment("General Settings")
	public static GeneralSettings general_settings = new GeneralSettings();

	@Config.Comment("Player Settings")
	public static PlayerSettings player_settings = new PlayerSettings();

	@Config.Comment("Mob Settings")
	public static MobSettings mob_settings = new MobSettings();

	public static class GeneralSettings {
		@Config.Name("Destroy Vegetation")
		@Config.Comment("When enabled, vegetation like tall grass or loose rocks gets destroyed")
		public boolean DESTROY_VEGETATION = true;

		@Config.Name("All Entities")
		@Config.Comment("When enabled, all entities create paths (performance intensive)")
		public boolean ALL_ENTITIES = false;

		@Config.Name("Debug Mode")
		@Config.Comment("When enabled, prints debug values to console")
		public boolean DEBUG = false;
	}

	public static class PlayerSettings {
		@Config.Name("Grass -> Dirt")
		@Config.Comment("Chance per mille for grass to turn into dirt, set to 0.0 to disable")
		@Config.RangeDouble(min = 0.0, max = 1000.0)
		public double GRASS_TO_DIRT = 30.0;

		@Config.Name("Dirt -> Path")
		@Config.Comment("Chance per mille for dirt to turn into a path, set to 0.0 to disable")
		@Config.RangeDouble(min = 0.0, max = 1000.0)
		public double DIRT_TO_PATH = 10.0;

		@Config.Name("Path -> Gravel")
		@Config.Comment("Chance per mille for a path to turn into gravel, set to 0.0 to disable")
		@Config.RangeDouble(min = 0.0, max = 1000.0)
		public double PATH_TO_GRAVEL = 2.0;
	}

	public static class MobSettings {
		@Config.Name("Grass -> Dirt")
		@Config.Comment("Chance per mille for grass to turn into dirt, set to 0.0 to disable")
		@Config.RangeDouble(min = 0.0, max = 1000.0)
		public double GRASS_TO_DIRT = 15.0;

		@Config.Name("Dirt -> Path")
		@Config.Comment("Chance per mille for dirt to turn into a path, set to 0.0 to disable")
		@Config.RangeDouble(min = 0.0, max = 1000.0)
		public double DIRT_TO_PATH = 5.0;

		@Config.Name("Path -> Gravel")
		@Config.Comment("Chance per mille for a path to turn into gravel, set to 0.0 to disable")
		@Config.RangeDouble(min = 0.0, max = 1000.0)
		public double PATH_TO_GRAVEL = 1.0;
	}

	@Mod.EventBusSubscriber(modid = MODID_TFCPATHS)
	public static class EventHandler {
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(MODID_TFCPATHS)) {
				ConfigManager.sync(MODID_TFCPATHS, Config.Type.INSTANCE);
			}
		}
	}
}
