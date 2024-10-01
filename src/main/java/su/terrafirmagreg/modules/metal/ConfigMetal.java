package su.terrafirmagreg.modules.metal;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.cleanroommc.configanytime.ConfigAnytime;

import static su.terrafirmagreg.data.Constants.MOD_ID;
import static su.terrafirmagreg.data.Constants.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/" + "metal")
public class ConfigMetal {

  @Config.Name("Block")
  @Config.Comment("Block settings")
  public static final BlocksCategory BLOCK = new BlocksCategory();

  @Config.Name("Item")
  @Config.Comment("Item settings")
  public static final ItemsCategory ITEMS = new ItemsCategory();

  @Config.Name("Misc")
  @Config.Comment("Misc settings")
  public static final MiscCategory MISC = new MiscCategory();

  static {
    ConfigAnytime.register(ConfigMetal.class);
  }

  public static final class BlocksCategory {

    @Config.Comment("Lamp")
    public Lamp LAMP = new Lamp();

    public static final class Lamp {

      @Config.Comment("How much fuel (mB) can a metal lamps hold?")
      @Config.RangeInt(min = 1)
      public int tank = 250;

      @Config.Comment({"How fast lamps consume fuel (mb/hour)? " +
                       "1 = lamp life of 1 hour per mB, 0.125 = lamp life of 2000 hours by default, 0 = infinite fuel"})
      @Config.RangeDouble(min = 0)
      public double burnRate = 0.125;

      @Config.Comment("Which fluids are valid fuels for lamps?")
      public String[] fuels = {
        "olive_oil",
        "linseed_oil",
        "rape_seed_oil",
        "sunflower_seed_oil",
        "opium_poppy_seed_oil"
      };
    }
  }

  public static final class ItemsCategory {

  }

  public static final class MiscCategory {

  }

  @Mod.EventBusSubscriber(modid = MOD_ID)
  public static class EventHandler {

    @SubscribeEvent
    public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
      if (event.getModID().equals(MOD_ID)) {
        ModuleMetal.LOGGER.warn("Config changed");
        ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);
      }
    }
  }
}
