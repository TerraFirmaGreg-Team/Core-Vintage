package su.terrafirmagreg.modules.rock;

import su.terrafirmagreg.modules.rock.config.ConfigRockBlock;
import su.terrafirmagreg.modules.rock.config.ConfigRockEntity;
import su.terrafirmagreg.modules.rock.config.ConfigRockItem;
import su.terrafirmagreg.modules.rock.config.ConfigRockMisc;

import net.minecraftforge.common.config.Config;

import com.cleanroommc.configanytime.ConfigAnytime;

import static su.terrafirmagreg.data.Constants.MOD_ID;
import static su.terrafirmagreg.data.Constants.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/module/Rock")
public class ConfigRock {

  @Config.Name("Block")
  @Config.Comment("Block setting")
  public static final ConfigRockBlock BLOCK = new ConfigRockBlock();

  @Config.Name("Item")
  @Config.Comment("Item setting")
  public static final ConfigRockItem ITEM = new ConfigRockItem();

  @Config.Name("Entity")
  @Config.Comment("Entity setting")
  public static final ConfigRockEntity ENTITY = new ConfigRockEntity();

  @Config.Name("Misc")
  @Config.Comment("Misc setting")
  public static final ConfigRockMisc MISC = new ConfigRockMisc();

  static {
    ConfigAnytime.register(ConfigRock.class);
  }

}
