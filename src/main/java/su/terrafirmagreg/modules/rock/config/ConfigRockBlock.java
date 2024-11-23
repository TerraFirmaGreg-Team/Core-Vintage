package su.terrafirmagreg.modules.rock.config;

import net.minecraftforge.common.config.Config;

public final class ConfigRockBlock {

  @Config.Comment("Anvil")
  public final Anvil ANVIL = new Anvil();

  @Config.Comment("Raw")
  public final Raw RAW = new Raw();

  public static final class Anvil {

    @Config.Comment("Range of pixels on either side of the working target that can be accepted to complete a smithing recipe")
    @Config.RangeInt(min = 0)
    public int acceptableAnvilRange = 0;

    @Config.Comment("Enable the creation of stone anvils.")
    public boolean enableStoneAnvil = true;
  }

  public static final class Raw {

    @Config.Comment({"Chance that mining a raw stone will drop a gem.",
                     "Gem grade is random from: 16/31 Chipped, 8/31 Flawed, 4/31 Normal, 2/31 Flawless and 1/31 Exquisite."})
    @Config.RangeDouble(min = 0, max = 1)
    public double stoneGemDropChance = 31.0 / 8000.0; // 0.003875
  }
}
