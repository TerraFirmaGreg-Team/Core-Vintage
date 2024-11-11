package su.terrafirmagreg.modules.soil;

import su.terrafirmagreg.modules.soil.config.ConfigSoilBlock;
import su.terrafirmagreg.modules.soil.config.ConfigSoilEntity;
import su.terrafirmagreg.modules.soil.config.ConfigSoilItem;
import su.terrafirmagreg.modules.soil.config.ConfigSoilMisc;

import net.minecraftforge.common.config.Config;

import com.cleanroommc.configanytime.ConfigAnytime;

import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.Tags.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/module/Soil")
public class ConfigSoil {

  @Config.Name("Block")
  @Config.Comment("Block setting")
  public static final ConfigSoilBlock BLOCK = new ConfigSoilBlock();

  @Config.Name("Item")
  @Config.Comment("Item setting")
  public static final ConfigSoilItem ITEM = new ConfigSoilItem();

  @Config.Name("Entity")
  @Config.Comment("Entity setting")
  public static final ConfigSoilEntity ENTITY = new ConfigSoilEntity();

  @Config.Name("Misc")
  @Config.Comment("Misc setting")
  public static final ConfigSoilMisc MISC = new ConfigSoilMisc();

  static {
    ConfigAnytime.register(ConfigSoil.class);
  }

}
