package su.terrafirmagreg.modules.core;

import su.terrafirmagreg.modules.core.config.ConfigCoreBlock;
import su.terrafirmagreg.modules.core.config.ConfigCoreEntity;
import su.terrafirmagreg.modules.core.config.ConfigCoreItem;
import su.terrafirmagreg.modules.core.config.ConfigCoreMisc;

import net.minecraftforge.common.config.Config;

import com.cleanroommc.configanytime.ConfigAnytime;


import static su.terrafirmagreg.data.Constants.MOD_ID;
import static su.terrafirmagreg.data.Constants.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/module/Core")
public class ConfigCore {

  @Config.Name("Block")
  @Config.Comment("Block setting")
  public static final ConfigCoreBlock BLOCK = new ConfigCoreBlock();

  @Config.Name("Item")
  @Config.Comment("Items settings")
  public static final ConfigCoreItem ITEM = new ConfigCoreItem();

  @Config.Name("Entity")
  @Config.Comment("Entity setting")
  public static final ConfigCoreEntity ENTITY = new ConfigCoreEntity();

  @Config.Name("Misc")
  @Config.Comment("Misc setting")
  public static final ConfigCoreMisc MISC = new ConfigCoreMisc();

  static {
    ConfigAnytime.register(ConfigCore.class);
  }

}
