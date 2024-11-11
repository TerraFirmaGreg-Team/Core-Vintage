package su.terrafirmagreg.api.data.enums;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public enum MoonPhase {

  FULL(0, "full"),
  WAXING_GIBBOUS(1, "waxing.gibbous"),
  FIRST_QUARTER(2, "first.quarter"),
  WAXING_CRESCENT(3, "waxing.crescent"),
  NEW_MOON(4, "new"),
  WANING_CRESCENT(5, "waning.crescent"),
  LAST_QUARTER(6, "last.quarter"),
  WANING_GIBBOUS(7, "waning.gibbous");

  private final int phase;

  private final String key;

  MoonPhase(int phase, String key) {

    this.phase = phase;
    this.key = key;
  }

  @SideOnly(Side.CLIENT)
  public static MoonPhase getCurrentPhase() {

    return getPhase(Minecraft.getMinecraft().world.getMoonPhase());
  }

  public static MoonPhase getPhase(int phase) {

    final int safePhase = phase < 0 ? 0 : Math.min(phase, 7);
    return MoonPhase.values()[safePhase];
  }

  @SideOnly(Side.CLIENT)
  public String getPhaseName() {

    return I18n.format("moon.phase." + this.key + ".name");
  }
}
