package su.terrafirmagreg.modules.core.capabilities.food.spi;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;


import org.jetbrains.annotations.Nullable;

import lombok.Getter;

public class FoodData implements INBTSerializable<NBTTagCompound> {

  // Instances for special vanilla foods (with relation to decay)
  public static final FoodData ROTTEN_FLESH = new FoodData(0, 0, 0, 0, 0, 0, 0, 0,
          Float.POSITIVE_INFINITY);
  public static final FoodData GOLDEN_APPLE = new FoodData(1, 0, 0, 0, 2.5f, 0, 0, 0, 0);
  public static final FoodData GOLDEN_CARROT = new FoodData(1, 0, 0, 0, 0, 2.5f, 0, 0, 0);
  public static final FoodData RAW_EGG = new FoodData(1, 0, 0, 0, 0, 0, 0, 0, 1.3f);
  public static final FoodData MILK = new FoodData(0, 0, 0, 0, 0, 0, 0, 1.0f, 0);

  public static final FoodData CHOCOLATE = new FoodData(4, 0.0F, 0.2F, 0.2F, 0.0F, 0.0F, 0.0F, 0.8F,
          1.1F);
  public static final FoodData COCOA_BEANS = new FoodData(4, 0.0F, 0.1F, 0.1F, 0.0F, 0.0F, 0.1F,
          0.0F, 0.5F);
  //copied from squash
  public static final FoodData PUMPKIN = new FoodData(4, 1F, 0F, 0F, 1.4F, 0F, 0F, 0F, 1.8F);

  public static final FoodData DRIED_COCOA_BEANS = new FoodData(4, 0.0F, 0.1F, 0.1F, 0.0F, 0.0F,
          0.1F, 0.0F, 0.5F);

  public static final FoodData MILK_CURD = new FoodData(4, 0F, 1F, 0F, 0F, 0F, 0F, 2F, 3.0F);
  public static final FoodData CHEESE_BRINED = new FoodData(4, 0F, 2F, 0F, 0F, 0F, 0F, 3F, 0.3F);
  public static final FoodData CHEESE_SALTED = new FoodData(4, 0F, 2F, 0F, 0F, 0F, 0F, 3F, 0.3F);

  public static final FoodData DRIED_FRUIT_SATURATION = new FoodData(4, 0F, 0.5F, 0F, 0.75F, 0F, 0F,
          0F, 0.8F);
  public static final FoodData DRIED_FRUIT_DECAY = new FoodData(4, 0F, 0.2F, 0F, 1F, 0F, 0F, 0F,
          0.8F);
  public static final FoodData DRIED_FRUIT_CATEGORY = new FoodData(4, 0F, 0.2F, 0F, 0.75F, 0F, 0F,
          0F, 0.8F);

  public static final FoodData UNCRACKED_NUT = new FoodData(4, 0.0F, 0.1F, 0.1F, 0.0F, 0.0F, 0.0F,
          0.0F, 0.4F);
  public static final FoodData NUT = new FoodData(4, 0.0F, 0.5F, 0.9F, 0.0F, 0.0F, 0.6F, 0.0F,
          1.0F);
  public static final FoodData ROASTED_NUT = new FoodData(4, 0.0F, 1.0F, 1.2F, 0.0F, 0.0F, 0.8F,
          0.0F, 1.1F);

  public static final FoodData DOUGH = new FoodData(4, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
          3.0F);
  public static final FoodData FLOUR = new FoodData(4, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
          0.5F);
  public static final FoodData FLATBREAD = new FoodData(4, 1.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F,
          1.0F);
  public static final FoodData PIZZA = new FoodData(4, 1.0F, 0.0F, 1.0F, 0.0F, 0.5F, 0.0F, 0.0F,
          1.0F);
  public static final FoodData SLICE = new FoodData(4, 0.0F, 0.75F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F,
          1.5F);
  public static final FoodData TOAST = new FoodData(4, 0.0F, 1.5F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F,
          1.8F);
  public static final FoodData SANDWICH = new FoodData(4, 0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
          5F);
  public static final FoodData TRAIL_MIX = new FoodData(4, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
          0.9F);
  public static final FoodData GROUND_SOYBEANS = new FoodData(4, 0.0F, 0.5F, 0.0F, 0.0F, 0.5F, 1.0F,
          0.0F, 2.5F);
  public static final FoodData TOFU = new FoodData(4, 0.8F, 2.0F, 0.0F, 0.0F, 0.5F, 1.5F, 0.0F,
          2.0F);

  public static final FoodData GARLIC_BREAD = new FoodData(4, 0.5F, 0.75F, 0.5F, 0.0F, 2.0F, 0.0F,
          0.0F, 0.5F);
  public static final FoodData PICKLED_EGG = new FoodData(4, 0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.75F,
          0.25F, 0.75F);

  public static final FoodData PINEAPPLE = new FoodData(4, 0.5F, 4.1F, 0.0F, 0.75F, 0.0F, 0.0F,
          0.0F, 4.9F);
  public static final FoodData MELON = new FoodData(4, 1.5F, 1.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F,
          4.9F);

  @Getter
  private final float[] nutrients; // Nutritional values
  @Getter
  private int hunger; // Hunger. In TFC (for now) this is almost always 4
  @Getter
  private float saturation; // Saturation, only provided by some basic foods and meal bonuses
  @Getter
  private float water; // Water, provided by some foods
  @Getter
  private float decayModifier; // Decay modifier - higher = shorter decay
  private boolean buffed; // if this data instance has been buffed externally.

  public FoodData() {
    this(4, 0, 0, 0, 0, 0, 0, 0, 1);
  }

  public FoodData(int hunger, float water, float saturation, float grain, float fruit, float veg,
          float protein, float dairy, float decayModifier) {
    this(hunger, water, saturation, new float[]{grain, fruit, veg, protein, dairy}, decayModifier);
  }

  public FoodData(int hunger, float water, float saturation, float[] nutrients,
          float decayModifier) {
    this.hunger = hunger;
    this.water = water;
    this.saturation = saturation;
    this.nutrients = nutrients;
    this.decayModifier = decayModifier;
  }

  public FoodData(@Nullable NBTTagCompound nbt) {
    this.nutrients = new float[5];
    deserializeNBT(nbt);
  }

  @Override
  public NBTTagCompound serializeNBT() {
    NBTTagCompound nbt = new NBTTagCompound();
    nbt.setInteger("food", hunger);
    nbt.setFloat("sat", saturation);
    nbt.setFloat("water", water);
    nbt.setFloat("decay", decayModifier);
    nbt.setFloat("grain", nutrients[Nutrient.GRAIN.ordinal()]);
    nbt.setFloat("veg", nutrients[Nutrient.VEGETABLES.ordinal()]);
    nbt.setFloat("fruit", nutrients[Nutrient.FRUIT.ordinal()]);
    nbt.setFloat("meat", nutrients[Nutrient.PROTEIN.ordinal()]);
    nbt.setFloat("dairy", nutrients[Nutrient.DAIRY.ordinal()]);
    nbt.setBoolean("buffed", buffed);
    return nbt;
  }

  @Override
  public void deserializeNBT(NBTTagCompound nbt) {
    if (nbt != null) {
      hunger = nbt.getInteger("food");
      saturation = nbt.getFloat("sat");
      water = nbt.getFloat("water");
      decayModifier = nbt.getFloat("decay");
      nutrients[Nutrient.GRAIN.ordinal()] = nbt.getFloat("grain");
      nutrients[Nutrient.VEGETABLES.ordinal()] = nbt.getFloat("veg");
      nutrients[Nutrient.FRUIT.ordinal()] = nbt.getFloat("fruit");
      nutrients[Nutrient.PROTEIN.ordinal()] = nbt.getFloat("meat");
      nutrients[Nutrient.DAIRY.ordinal()] = nbt.getFloat("dairy");
      buffed = nbt.getBoolean("buffed");
    }
  }

  public FoodData copy() {
    return new FoodData(hunger, water, saturation, nutrients, decayModifier);
  }

  public void applyBuff(FoodData buff) {
    if (!buffed) {
      buffed = true;
      for (Nutrient nutrient : Nutrient.values()) {
        nutrients[nutrient.ordinal()] += buff.nutrients[nutrient.ordinal()];
      }
    }
  }
}
