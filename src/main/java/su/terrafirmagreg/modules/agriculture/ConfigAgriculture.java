package su.terrafirmagreg.modules.agriculture;

import su.terrafirmagreg.modules.agriculture.config.ConfigBlock;
import su.terrafirmagreg.modules.agriculture.config.ConfigEntity;
import su.terrafirmagreg.modules.agriculture.config.ConfigItem;
import su.terrafirmagreg.modules.agriculture.config.ConfigMisc;

import net.minecraftforge.common.config.Config;

import com.cleanroommc.configanytime.ConfigAnytime;

import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.Tags.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/module/Agriculture")
public class ConfigAgriculture {

  @Config.Name("Block")
  @Config.Comment("Block setting")
  public static final ConfigBlock BLOCKS = new ConfigBlock();

  @Config.Name("Item")
  @Config.Comment("Item setting")
  public static final ConfigItem ITEMS = new ConfigItem();

  @Config.Name("Entity")
  @Config.Comment("Entity setting")
  public static final ConfigEntity ENTITY = new ConfigEntity();

  @Config.Name("Misc")
  @Config.Comment("Misc setting")
  public static final ConfigMisc MISC = new ConfigMisc();

  static {
    ConfigAnytime.register(ConfigAgriculture.class);
  }
}
