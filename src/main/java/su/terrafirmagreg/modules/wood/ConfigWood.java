package su.terrafirmagreg.modules.wood;

import su.terrafirmagreg.modules.wood.config.ConfigWoodBlock;
import su.terrafirmagreg.modules.wood.config.ConfigWoodEntity;
import su.terrafirmagreg.modules.wood.config.ConfigWoodItem;
import su.terrafirmagreg.modules.wood.config.ConfigWoodMisc;

import net.minecraftforge.common.config.Config;

import com.cleanroommc.configanytime.ConfigAnytime;

import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.Tags.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/module/Wood")
public class ConfigWood {

  @Config.Name("Block")
  @Config.Comment("Block setting")
  public static final ConfigWoodBlock BLOCK = new ConfigWoodBlock();

  @Config.Name("Item")
  @Config.Comment("Item setting")
  public static final ConfigWoodItem ITEM = new ConfigWoodItem();

  @Config.Name("Entity")
  @Config.Comment("Entity setting")
  public static final ConfigWoodEntity ENTITY = new ConfigWoodEntity();

  @Config.Name("Misc")
  @Config.Comment("Misc setting")
  public static final ConfigWoodMisc MISC = new ConfigWoodMisc();

  static {
    ConfigAnytime.register(ConfigWood.class);
  }


}
