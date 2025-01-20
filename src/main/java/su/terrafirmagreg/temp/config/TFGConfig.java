package su.terrafirmagreg.temp.config;

import su.terrafirmagreg.Tags;

import net.minecraftforge.common.config.Config;

@Config(modid = Tags.MOD_ID)
public class TFGConfig {


  @Config.Comment("General Settings")
  public static final General GENERAL = new General();


  public static class General {

    @Config.Comment("What kind of liquid should a primitive pump extract? If the liquid you specify is not detected, salt water will be extracted. Default: fresh_water")
    @Config.RequiresMcRestart
    public static String fluidForPrimitivePump = "fresh_water";
    @Config.Comment("If true, hot effects for items will be enabled")
    public static boolean HOT_ITEMS = true;
    @Config.Comment("If true, hot effects for fluids will be enabled")
    public static boolean HOT_FLUIDS = true;
    @Config.Comment("If true, cold effects for fluids will be enabled")
    public static boolean COLD_FLUIDS = true;
    @Config.Comment("If true, gaseous effects for fluids will be enabled")
    public static boolean GASEOUS_FLUIDS = true;
    @Config.Comment("If true, items causing effects will get a tooltip")
    public static boolean TOOLTIP = true;
    @Config.Comment("If true, hot items make the player yeet them")
    public static boolean YEET = true;
    @Config.Comment("How hot a fluid should be to start burning the player (in Celsius)")
    public static int HOT_FLUID = 480;
    @Config.Comment("How cold a fluid should be to start adding effects the player (in Celsius)")
    public static int COLD_FLUID = 0;
    @Config.Comment("How hot an item should be to start burning the player (in Celsius)")
    public static int HOT_ITEM = 480;
    @Config.Comment("How often will the durability will be decreased? In Ticks (20 ticks = 1 sec)")
    public static int DURABILITY_DECREASING = 20;
    @Config.Comment("Hot items that are included manually")
    public static String[] HOT_ITEM_ADDITIONS = new String[]{"minecraft:blaze_rod"};
    @Config.Comment("Cold items that are included manually")
    public static String[] COLD_ITEM_ADDITIONS = new String[]{"minecraft:ice", "minecraft:packed_ice"};
    @Config.Comment("Gaseous items that are included manually")
    public static String[] GASEOUS_ITEM_ADDITIONS = new String[]{"mod_id:item"};
    @Config.Comment("Items that are excluded")
    public static String[] ITEM_REMOVALS = new String[]{"immersiveengineering:drill", "immersiveengineering:chemthrower", "immersivepetroleum:fluid_diesel",
                                                        "immersivepetroleum:fluid_gasoline"};
  }
}
