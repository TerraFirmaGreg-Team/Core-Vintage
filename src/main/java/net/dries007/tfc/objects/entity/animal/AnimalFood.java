package net.dries007.tfc.objects.entity.animal;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnimalFood {

  private static final HashMap<Class<? extends Entity>, AnimalFood> ANIMAL_FOOD_MAP = new HashMap<>();
  private final List<Ingredient> acceptedFoods;
  private final boolean eatRotten;

  public AnimalFood(boolean eatRotten) {
    this.eatRotten = eatRotten;
    acceptedFoods = new ArrayList<>();
  }

  @Nullable
  public static AnimalFood get(Class<? extends Entity> animalClass) {
    return ANIMAL_FOOD_MAP.get(animalClass);
  }

  public void addFood(Ingredient ingredient) {
    acceptedFoods.add(ingredient);
  }

  public boolean isFood(ItemStack stack) {
    for (Ingredient acceptedFood : acceptedFoods) {
      if (acceptedFood.apply(stack)) {
        if (!eatRotten) {
          IFood cap = stack.getCapability(CapabilityFood.CAPABILITY, null);
          return cap == null || !cap.isRotten();
        }
        return true;
      }
    }
    return false;
  }
}
