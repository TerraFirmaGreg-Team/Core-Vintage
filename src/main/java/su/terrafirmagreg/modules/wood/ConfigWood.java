package su.terrafirmagreg.modules.wood;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.cleanroommc.configanytime.ConfigAnytime;


import static su.terrafirmagreg.data.Constants.MOD_ID;
import static su.terrafirmagreg.data.Constants.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/" + "wood")
public class ConfigWood {

  @Config.Name("Blocks")
  @Config.Comment("Block settings")

  public static final BlocksCategory BLOCKS = new BlocksCategory();

  @Config.Name("Items")
  @Config.Comment("Item settings")

  public static final ItemsCategory ITEMS = new ItemsCategory();

  @Config.Name("Misc")
  @Config.Comment("Miscellaneous")

  public static final MiscCategory MISC = new MiscCategory();

  static {
    ConfigAnytime.register(ConfigWood.class);
  }

  public static final class BlocksCategory {

    @Config.Comment("Barrel")
    public final Barrel BARREL = new Barrel();

    @Config.Comment("Support")
    public final Support SUPPORT = new Support();

    public static final class Barrel {

      @Config.Comment("How much fluid (mB) can a barrel hold?")
      @Config.RangeInt(min = 100)
      public int tank = 10_000;

      @Config.Comment("List of fluids allowed to be inserted into a barrel.")
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
      public int supportBeamRangeHor = 4;

      @Config.Comment("Upwards support range of support beams.")
      @Config.RangeInt(min = 0, max = 3)
      public int supportBeamRangeUp = 1;

      @Config.Comment("Downwards support range of support beams.")
      @Config.RangeInt(min = 0, max = 3)
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
          "tfg:camel",
          "tfg:donkey",
          "tfg:horse",
          "tfg:mule"
      };
    }

    public static class Plow {

      public String[] canPull = {
          "minecraft:donkey",
          "minecraft:horse",
          "minecraft:mule",
          "minecraft:pig",
          "minecraft:player",
          "tfg:camel",
          "tfg:donkey",
          "tfg:horse",
          "tfg:mule"
      };
    }

    public static class AnimalCart {

      public String[] canPull = {
          "minecraft:donkey",
          "minecraft:horse",
          "minecraft:mule",
          "minecraft:pig",
          "minecraft:player",
          "tfg:camel",
          "tfg:donkey",
          "tfg:horse",
          "tfg:mule"
      };
    }

  }

  public static final class MiscCategory {

    @Config.RequiresMcRestart
    @Config.RangeDouble(min = -1.0D, max = 0.0D)
    public final double SPEED_MODIFIER = -0.65D;

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
