package su.terrafirmagreg.modules.food;


import su.terrafirmagreg.modules.food.config.ConfigBlock;
import su.terrafirmagreg.modules.food.config.ConfigEntity;
import su.terrafirmagreg.modules.food.config.ConfigFeature;
import su.terrafirmagreg.modules.food.config.ConfigItem;
import su.terrafirmagreg.modules.food.config.ConfigMisc;

import net.minecraftforge.common.config.Config;

import com.cleanroommc.configanytime.ConfigAnytime;

import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.Tags.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/module/Food")
public class ConfigFood {

  @Config.Name("Block")
  @Config.Comment("Block setting")
  public static final ConfigBlock BLOCK = new ConfigBlock();

  @Config.Name("Item")
  @Config.Comment("Items settings")
  public static final ConfigItem ITEM = new ConfigItem();

  @Config.Name("Entity")
  @Config.Comment("Entity setting")
  public static final ConfigEntity ENTITY = new ConfigEntity();

  @Config.Name("Misc")
  @Config.Comment("Misc setting")
  public static final ConfigMisc MISC = new ConfigMisc();

  @Config.Name("Feature")
  @Config.Comment("Feature setting")
  public static final ConfigFeature FEATURE = new ConfigFeature();

  static {
    ConfigAnytime.register(ConfigFood.class);
  }

}
