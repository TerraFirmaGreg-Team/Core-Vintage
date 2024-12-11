package su.terrafirmagreg.modules.agriculture.config;

import net.minecraftforge.common.config.Config;

import static su.terrafirmagreg.api.data.Reference.MODID_TFC;

public class ConfigBlock {

  @Config.Comment("Berry Bush")
  public final BerryBush BERRY_BUSH = new BerryBush();

  public static final class BerryBush {

    @Config.Comment("Modifier for how long berry bushes take to grow fruits.")
    @Config.RangeDouble(min = 0.01, max = 100)
    public double growthTimeModifier = 1.0;

    @Config.Comment("Defines berry bush rarity to generate, in 1 / N chunks. 0 = Disable")
    @Config.RangeInt(min = 0)
    @Config.LangKey("config." + MODID_TFC + ".general.food.berryBushRarity")
    public int rarity = 80;

    @Config.Comment("Berry bush movement modifier. Lower = Slower, Higher = Faster. 1 = No slow down. (Speed * this = slow).")
    @Config.RangeDouble(min = 0, max = 1)
    public double movementModifier = 0.1;
  }
}
