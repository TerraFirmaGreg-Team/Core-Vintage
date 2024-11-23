package su.terrafirmagreg.modules.flora;

import su.terrafirmagreg.modules.flora.config.ConfigBlock;
import su.terrafirmagreg.modules.flora.config.ConfigEntity;
import su.terrafirmagreg.modules.flora.config.ConfigItem;
import su.terrafirmagreg.modules.flora.config.ConfigMisc;

import net.minecraftforge.common.config.Config;

import com.cleanroommc.configanytime.ConfigAnytime;

import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.Tags.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/module/Flora")
public class ConfigFlora {

  @Config.Name("Block")
  @Config.Comment("Block setting")
  public static final ConfigBlock BLOCK = new ConfigBlock();

  @Config.Name("Item")
  @Config.Comment("Items setting")
  public static final ConfigItem ITEM = new ConfigItem();

  @Config.Name("Entity")
  @Config.Comment("Entity setting")
  public static final ConfigEntity ENTITY = new ConfigEntity();

  @Config.Name("Misc")
  @Config.Comment("Misc settings")
  public static final ConfigMisc MISC = new ConfigMisc();

  static {
    ConfigAnytime.register(ConfigFlora.class);
  }


}
