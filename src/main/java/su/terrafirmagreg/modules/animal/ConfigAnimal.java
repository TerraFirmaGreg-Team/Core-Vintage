package su.terrafirmagreg.modules.animal;

import su.terrafirmagreg.modules.animal.config.ConfigAnimalBlock;
import su.terrafirmagreg.modules.animal.config.ConfigAnimalEntity;
import su.terrafirmagreg.modules.animal.config.ConfigAnimalItem;
import su.terrafirmagreg.modules.animal.config.ConfigAnimalMisc;

import net.minecraftforge.common.config.Config;

import com.cleanroommc.configanytime.ConfigAnytime;

import static su.terrafirmagreg.data.Constants.MOD_ID;
import static su.terrafirmagreg.data.Constants.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/module/Animal")
public class ConfigAnimal {

  @Config.Name("Block")
  @Config.Comment("Block setting")
  public static ConfigAnimalBlock BLOCK = new ConfigAnimalBlock();

  @Config.Name("Item")
  @Config.Comment("Item setting")
  public static ConfigAnimalItem ITEM = new ConfigAnimalItem();

  @Config.Name("Entity")
  @Config.Comment("Entity setting")
  public static ConfigAnimalEntity ENTITY = new ConfigAnimalEntity();

  @Config.Name("Misc")
  @Config.Comment("Misc setting")
  public static ConfigAnimalMisc MISC = new ConfigAnimalMisc();

  static {
    ConfigAnytime.register(ConfigAnimal.class);
  }
  
}
