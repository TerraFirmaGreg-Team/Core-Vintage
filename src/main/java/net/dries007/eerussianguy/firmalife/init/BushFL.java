package net.dries007.eerussianguy.firmalife.init;

import su.terrafirmagreg.api.library.MCDate.Month;
import su.terrafirmagreg.modules.world.classic.objects.generator.GeneratorBerryBushes;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.dries007.eerussianguy.firmalife.registry.ItemsFL;
import net.dries007.tfc.api.types.IBerryBush;

import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;

import lombok.Getter;

import java.util.function.Supplier;

import static net.dries007.tfc.api.types.IBerryBush.Size.LARGE;

public enum BushFL implements IBerryBush {
  PINEAPPLE(() -> ItemsFL.getFood(FoodFL.PINEAPPLE), Month.JANUARY, 12, -5f, 17f, 100f, 400f, 0.8f, LARGE, true);

  static {
    for (IBerryBush bush : values()) {
      GeneratorBerryBushes.register(bush);
    }
  }

  private final Supplier<Item> fruit;
  private final Month harvestMonthStart;
  private final int harvestingMonths;
  private final float growthTime;
  private final float minTemp;
  private final float maxTemp;
  private final float minRain;
  private final float maxRain;
  @Getter
  private final Size size;
  private final boolean hasSpikes;

  BushFL(Supplier<Item> fruit, Month harvestMonthStart, int harvestingMonths, float minTemp, float maxTemp, float minRain, float maxRain, float growthTime, Size size,
         boolean spiky) {
    this.fruit = fruit;
    this.harvestMonthStart = harvestMonthStart;
    this.harvestingMonths = harvestingMonths;
    this.growthTime = growthTime * Calendar.CALENDAR_TIME.getDaysInMonth() * ICalendar.HOURS_IN_DAY;

    this.minTemp = minTemp;
    this.maxTemp = maxTemp;
    this.minRain = minRain;
    this.maxRain = maxRain;

    this.size = size;
    this.hasSpikes = spiky;
  }

  @Override
  public float getGrowthTime() {
    return this.growthTime;
  }

  @Override
  public boolean isHarvestMonth(Month month) {
    Month testing = this.harvestMonthStart;
    for (int i = 0; i < this.harvestingMonths; i++) {
      if (testing.equals(month)) {
        return true;
      }
      testing = testing.next();
    }
    return false;
  }

  @Override
  public boolean isValidConditions(float temperature, float rainfall) {
    return minTemp - 5 < temperature && temperature < maxTemp + 5 && minRain - 50 < rainfall && rainfall < maxRain + 50;
  }

  @Override
  public boolean isValidForGrowth(float temperature, float rainfall) {
    return minTemp < temperature && temperature < maxTemp && minRain < rainfall && rainfall < maxRain;
  }

  @Override
  public ItemStack getFoodDrop() {
    return new ItemStack(this.fruit.get());
  }

  @Override
  public boolean isSpiky() {
    return hasSpikes;
  }
}
