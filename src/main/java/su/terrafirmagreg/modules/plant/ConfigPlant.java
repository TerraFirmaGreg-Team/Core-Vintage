package su.terrafirmagreg.modules.plant;

import su.terrafirmagreg.modules.plant.config.ConfigPlantBlock;
import su.terrafirmagreg.modules.plant.config.ConfigPlantEntity;
import su.terrafirmagreg.modules.plant.config.ConfigPlantItem;
import su.terrafirmagreg.modules.plant.config.ConfigPlantMisc;

import net.minecraftforge.common.config.Config;

import com.cleanroommc.configanytime.ConfigAnytime;

import static su.terrafirmagreg.data.Constants.MOD_ID;
import static su.terrafirmagreg.data.Constants.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/module/Plant")
public class ConfigPlant {

  @Config.Name("Block")
  @Config.Comment("Block setting")
  public static final ConfigPlantBlock BLOCK = new ConfigPlantBlock();

  @Config.Name("Item")
  @Config.Comment("Items setting")
  public static final ConfigPlantItem ITEM = new ConfigPlantItem();

  @Config.Name("Entity")
  @Config.Comment("Entity setting")
  public static final ConfigPlantEntity ENTITY = new ConfigPlantEntity();

  @Config.Name("Misc")
  @Config.Comment("Misc settings")
  public static final ConfigPlantMisc MISC = new ConfigPlantMisc();

  static {
    ConfigAnytime.register(ConfigPlant.class);
  }


}
