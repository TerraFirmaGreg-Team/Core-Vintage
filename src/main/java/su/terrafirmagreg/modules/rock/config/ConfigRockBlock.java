package su.terrafirmagreg.modules.rock.config;

import net.minecraftforge.common.config.Config;

public final class ConfigRockBlock {

  @Config.Comment("Anvil")
  public final Anvil ANVIL = new Anvil();

  public static final class Anvil {

    @Config.Comment("Range of pixels on either side of the working target that can be accepted to complete a smithing recipe")
    @Config.RangeInt(min = 0)
    public int acceptableAnvilRange = 0;

    @Config.Comment("Enable the creation of stone anvils.")
    public boolean enableStoneAnvil = true;
  }
}
