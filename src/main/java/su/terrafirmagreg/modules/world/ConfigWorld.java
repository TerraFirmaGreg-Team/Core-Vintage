package su.terrafirmagreg.modules.world;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.cleanroommc.configanytime.ConfigAnytime;

import net.dries007.tfc.util.config.HemisphereType;
import net.dries007.tfc.util.config.TemperatureMode;

import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.Tags.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/module/World")
public class ConfigWorld {

  @Config.Name("Blocks")
  @Config.Comment("Block settings")
  public static final BlocksCategory BLOCKS = new BlocksCategory();

  @Config.Name("Items")
  @Config.Comment("Item settings")
  public static final ItemsCategory ITEMS = new ItemsCategory();

  @Config.Name("Misc")
  @Config.Comment("Misc settings")
  public static final MiscCategory MISC = new MiscCategory();

  static {
    ConfigAnytime.register(ConfigWorld.class);
  }

  public static final class BlocksCategory {

  }

  public static final class ItemsCategory {

  }

  public static final class MiscCategory {

    @Config.RequiresMcRestart
    @Config.Comment({
      "This controls which registered entities can respawn in TFG biomes.",
      "You must specify by following the pattern 'modid:entity <rarity> <minGroupSpawn> <maxGroupSpawn>'",
      "Invalid entries will be ignored."})
    public String[] respawnableCreatures = {
      "tfc:blackbeartfc 30 1 2", "tfc:boartfc 30 1 2", "tfc:cougartfc 30 1 2",
      "tfc:coyotetfc 30 3 6", "tfc:deertfc 70 2 4", "tfc:direwolftfc 30 1 2",
      "tfc:gazelletfc 70 2 4", "tfc:grizzlybeartfc 30 1 2", "tfc:grousetfc 70 2 3",
      "tfc:haretfc 70 2 3", "tfc:hyenatfc 30 3 6", "tfc:jackaltfc 30 3 6",
      "tfc:liontfc 30 1 2", "tfc:mongoosetfc 30 1 2", "tfc:muskoxtfc 70 2 4",
      "tfc:ocelottfc 70 2 4", "tfc:panthertfc 30 1 2", "tfc:pheasanttfc 70 2 3",
      "tfc:polarbeartfc 30 1 2", "tfc:quailtfc 70 2 3", "tfc:rabbittfc 70 2 3",
      "tfc:sabertoothtfc 30 1 2", "tfc:turkeytfc 70 2 3", "tfc:wildebeesttfc 70 2 4",
      "tfc:wolftfc 70 2 4", "tfc:yaktfc 70 2 4", "tfc:zebutfc 70 2 4"
    };

    @Config.RequiresMcRestart
    @Config.Comment("Sets temperature in relation to the equator change. North = Cold, South = Hot or North = Hot, South = Cold.")
    public HemisphereType hemisphereType = HemisphereType.COLD_NORTH_HOT_SOUTH;

    @Config.RequiresMcRestart
    @Config.Comment("This controls how temperature is affected by how far from equator you are.")
    public TemperatureMode temperatureMode = TemperatureMode.CYCLIC;

    @Config.Comment({
      "If Cyclic, this controls the length (in blocks) of the temperature regions. The temperature values change in a wave-like pattern (sine wave).",
      "Wandering straight in a direction increases or decreases temperature. When you travel this many blocks, the temperature begins changing in the other direction."})
    @Config.RangeInt(min = 1_000, max = 1_000_000)
    public int latitudeTemperatureModifier = 40_000;

    @Config.Comment(
      "The rarity for clay pits to occur. On average 1 / N chunks will have a clay deposit, if the chunk in question is valid for clay to spawn.")
    @Config.RangeInt(min = 1)
    public int clayRarity = 30;

    @Config.Comment("The minimum rainfall in an area required for clay to spawn. By default this is the same threshold as dry grass.")
    @Config.RangeInt(min = 1, max = 500)
    public int clayRainfallThreshold = 150;

    @Config.Comment("The number of attempts per chunk to spawn loose rocks. Includes surface ore samples.")
    @Config.RangeInt(min = 1)
    public int looseRocksFrequency = 18;

    @Config.Comment("Enable generation of loose rocks (just the rocks)?")
    public boolean enableLooseRocks = true;

    @Config.Comment("Enable generation of loose ores?")
    public boolean enableLooseOres = true;

    @Config.Comment("Enable generation of loose rocks (just the rocks)?")
    public boolean enableLooseSticks = true;

    @Config.Comment({"This controls the number of sticks generated on chunk generation.",
                     "The number of trees in the area and flora density is also a factor in this."})
    @Config.RangeDouble(min = 0, max = 10)
    public double sticksDensityModifier = 1;

    @Config.Comment(
      "This is how deep (in blocks) from the surface a loose rock will scan for a vein when generating, Higher values = More veins spawn samples thus adding more samples.")
    @Config.RangeInt(min = 1, max = 255)
    public int looseRockScan = 35;

    @Config.RequiresMcRestart
    @Config.RangeDouble(min = 0.05, max = 0.4)
    @Config.Comment({
      "This controls how spread the rainfall distribution is. Higher values means the world will be distributed towards the extremes more, making more deserts and rain forests.",
      "WARNING: This can cause very weird world generation conditions."})
    public double rainfallSpreadFactor = 0.13;

    @Config.RequiresMcRestart
    @Config.RangeDouble(min = 0.05, max = 0.4)
    @Config.Comment({
      "This controls how spread the flora diversity distribution is. Higher values means the world will be distributed towards the extremes more, making forests have much more different kinds of trees.",
      "WARNING: This can cause very weird world generation conditions."})
    public double floraDiversitySpreadFactor = 0.16;

    @Config.RequiresMcRestart
    @Config.RangeDouble(min = 0.05, max = 0.4)
    @Config.Comment({
      "This controls how spread the flora density distribution is. Higher values means the world will be distributed towards the extremes more, making more dense forest pockets.",
      "WARNING: This can cause very weird world generation conditions."})
    public double floraDensitySpreadFactor = 0.16;
  }

  @Mod.EventBusSubscriber(modid = MOD_ID)
  public static class EventHandler {

    @SubscribeEvent
    public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
      if (event.getModID().equals(MOD_ID)) {
        ModuleWorld.LOGGER.warn("Config changed");
        ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);
      }
    }
  }

}
