package su.terrafirmagreg.modules.food;

import su.terrafirmagreg.modules.food.config.ConfigFoodBlock;
import su.terrafirmagreg.modules.food.config.ConfigFoodEntity;
import su.terrafirmagreg.modules.food.config.ConfigFoodItem;
import su.terrafirmagreg.modules.food.config.ConfigFoodMisc;

import net.minecraftforge.common.config.Config;

import com.cleanroommc.configanytime.ConfigAnytime;

import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.Tags.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/" + "food")
public class ConfigFood {

  @Config.Name("Blocks")
  @Config.Comment("Block settings")
  public static final ConfigFoodBlock BLOCKS = new ConfigFoodBlock();

  @Config.Name("Items")
  @Config.Comment("Items settings")
  public static final ConfigFoodItem ITEMS = new ConfigFoodItem();

  @Config.Name("Entity")
  @Config.Comment("Entity setting")
  public static final ConfigFoodEntity ENTITY = new ConfigFoodEntity();

  @Config.Name("Misc")
  @Config.Comment("Miscellaneous")
  public static final ConfigFoodMisc MISC = new ConfigFoodMisc();

  static {
    ConfigAnytime.register(ConfigFood.class);
  }
}
