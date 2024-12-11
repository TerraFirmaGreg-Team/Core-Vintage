package su.terrafirmagreg.modules.agriculture.api.types.berrybush;

import su.terrafirmagreg.api.data.enums.EnumBushSize;
import su.terrafirmagreg.api.library.MCDate.Month;
import su.terrafirmagreg.api.library.types.type.Type;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import lombok.Getter;

import java.util.Set;
import java.util.function.Supplier;

@Getter
public class BerryBushType extends Type<BerryBushType> {

  @Getter
  private static final Set<BerryBushType> types = new ObjectOpenHashSet<>();


  private final Supplier<Item> fruit;
  private final Month harvestMonthStart;
  private final int harvestingMonths;
  private final float growthTime;
  private final float minTemp;
  private final float maxTemp;
  private final float minRain;
  private final float maxRain;
  private final EnumBushSize size;
  private final boolean spiky;

  protected BerryBushType(Builder builder) {
    super(builder.name);

    this.fruit = builder.fruit;
    this.harvestMonthStart = builder.harvestMonthStart;
    this.harvestingMonths = builder.harvestingMonths;
    this.growthTime = builder.growthTime;

    this.minTemp = builder.minTemp;
    this.maxTemp = builder.maxTemp;
    this.minRain = builder.minRain;
    this.maxRain = builder.maxRain;

    this.size = builder.size;
    this.spiky = builder.spiky;

    if (!types.add(this)) {
      throw new RuntimeException(String.format("WoodType: [%s] already exists!", this.name));
    }
  }

  public static Builder builder(String name) {
    return new Builder(name);
  }

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

  public boolean isValidConditions(float temperature, float rainfall) {
    return minTemp - 5 < temperature && temperature < maxTemp + 5 && minRain - 50 < rainfall && rainfall < maxRain + 50;
  }

  public boolean isValidForGrowth(float temperature, float rainfall) {
    return minTemp < temperature && temperature < maxTemp && minRain < rainfall && rainfall < maxRain;
  }
  
  public ItemStack getFoodDrop() {
    return new ItemStack(fruit.get());
  }

  public static class Builder {

    private final String name;

    private Supplier<Item> fruit;
    private Month harvestMonthStart;
    private int harvestingMonths;
    private float growthTime;
    private float minTemp;
    private float maxTemp;
    private float minRain;
    private float maxRain;
    private EnumBushSize size;
    private boolean spiky;


    public Builder(String name) {
      this.name = name;
    }

    public Builder fruit(Supplier<Item> fruit) {
      this.fruit = fruit;
      return this;
    }

    public Builder harvest(Month harvestMonthStart, int harvestingMonths) {
      this.harvestMonthStart = harvestMonthStart;
      this.harvestingMonths = harvestingMonths;
      return this;
    }

    public Builder growthTime(float growthTime) {
      this.growthTime = growthTime * Calendar.CALENDAR_TIME.getDaysInMonth() * ICalendar.HOURS_IN_DAY;
      return this;
    }

    public Builder temp(float minTemp, float maxTemp) {
      this.minTemp = minTemp;
      this.maxTemp = maxTemp;
      return this;
    }

    public Builder rain(float minRain, float maxRain) {
      this.minRain = minRain;
      this.maxRain = maxRain;
      return this;
    }

    public Builder size(EnumBushSize size) {
      this.size = size;
      return this;
    }

    public Builder spiky(boolean spiky) {
      this.spiky = spiky;
      return this;
    }

    public BerryBushType build() {
      return new BerryBushType(this);
    }
  }
}
