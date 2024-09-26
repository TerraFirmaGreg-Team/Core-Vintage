package net.dries007.tfc.objects.items.food;

import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodData;
import su.terrafirmagreg.modules.core.capabilities.food.spi.Nutrient;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.api.capability.food.FoodHandler;
import net.dries007.tfc.util.agriculture.Food;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemSandwich extends ItemFoodTFC {

  public ItemSandwich(@NotNull Food food) {
    super(food);
  }

  @Nullable
  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
    return new SandwichHandler(nbt, food.getData());
  }

  public static class SandwichHandler extends FoodHandler {

    private final FoodData rootData;

    public SandwichHandler(@Nullable NBTTagCompound nbt, FoodData data) {
      super(nbt, data);

      this.rootData = data;
    }

    public void initCreationFoods(FoodData bread1, FoodData bread2, List<FoodData> ingredients) {
      // Nutrition and saturation of sandwich is (average of breads) + 0.8f (sum of ingredients), +1 bonus saturation
      float[] nutrition = new float[Nutrient.TOTAL];
      float saturation = 1 + 0.5f * (bread1.getSaturation() + bread2.getSaturation());
      float water = 0.5f * (bread1.getWater() + bread2.getWater());
      for (int i = 0; i < nutrition.length; i++) {
        nutrition[i] = 0.5f * (bread1.getNutrients()[i] + bread2.getNutrients()[i]);
      }
      for (FoodData ingredient : ingredients) {
        for (int i = 0; i < nutrition.length; i++) {
          nutrition[i] += 0.8f * ingredient.getNutrients()[i];
        }
        saturation += 0.8f * ingredient.getSaturation();
        water += 0.8f * ingredient.getWater();
      }
      this.data = new FoodData(4, water, saturation, nutrition, rootData.getDecayModifier());
    }

    @Override
    protected boolean isDynamic() {
      return true;
    }
  }
}
