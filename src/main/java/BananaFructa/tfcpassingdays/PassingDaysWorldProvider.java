package BananaFructa.tfcpassingdays;

import su.terrafirmagreg.api.library.MCDate.Month;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;

import BananaFructa.floraefixes.Utils;

public class PassingDaysWorldProvider extends WorldProvider {

  WorldProvider oldProvider;

  public PassingDaysWorldProvider(WorldProvider provider) {
    oldProvider = provider;
    this.setWorld(Utils.readDeclaredField(WorldProvider.class, provider, "world"));
    init();
    this.doesWaterVaporize = provider.doesWaterVaporize();
    this.nether = provider.isNether();
  }

  public int translateMonth(Month month) {
    int dif = month.ordinal() - 8;
    if (dif < 0) {
      dif += 12;
    }
    return dif;
  }

  public double getWinterSolticeDayPrecentege(double z) {
    return Math.sin((z - 20000) * Math.PI / 80000) * 0.4 + 0.5;
  }

  public double getDayPrecentege(double month, double solticeDecemberDayPrecentege) {
    return 0.5 - Math.sin((month - 2.0 / 3.0) * (Math.PI / 6)) * (0.5 - solticeDecemberDayPrecentege);
  }

  public int translateToLocalizedCelestialTime(int ticks, double dayPrecentege) {
    double x = ticks / 24000.0;
    if (x <= dayPrecentege) {
      return (int) (24000.0 * x * Math.pow(2 * dayPrecentege, -1));
    } else {
      return (int) (24000.0 * ((x * Math.pow(2 * (1 - dayPrecentege), -1)) + (0.5 - dayPrecentege) / (1 - dayPrecentege)));
    }
  }

  @Override
  public DimensionType getDimensionType() {
    return oldProvider.getDimensionType();
  }
}
