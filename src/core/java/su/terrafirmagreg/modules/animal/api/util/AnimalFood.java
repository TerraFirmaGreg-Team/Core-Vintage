package su.terrafirmagreg.modules.animal.api.util;

import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalAlpaca;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalCamel;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalChicken;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalCow;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalDonkey;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalDuck;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalGoat;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalGrouse;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalHorse;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalLlama;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalMule;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalMuskOx;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalOcelot;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalParrot;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalPig;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalQuail;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalSheep;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalWolf;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalYak;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalZebu;
import su.terrafirmagreg.modules.core.capabilities.food.CapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.food.ICapabilityFood;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class AnimalFood {

  public static final Map<Class<? extends Entity>, Supplier<AnimalFood>> CUSTOM_ANIMAL_FOOD = new HashMap<>();

  static {
    CUSTOM_ANIMAL_FOOD.put(EntityAnimalAlpaca.class,
      () -> new AnimalFood(true)
        .addFood(IIngredient.of("grain")));

    CUSTOM_ANIMAL_FOOD.put(EntityAnimalCamel.class,
      () -> new AnimalFood(false)
        .addFood(IIngredient.of("grain")));

    CUSTOM_ANIMAL_FOOD.put(EntityAnimalChicken.class,
      () -> new AnimalFood(true)
        .addFood(IIngredient.of("grain")));

    CUSTOM_ANIMAL_FOOD.put(EntityAnimalCow.class,
      () -> new AnimalFood(false)
        .addFood(IIngredient.of("grain")));

    CUSTOM_ANIMAL_FOOD.put(EntityAnimalDonkey.class,
      () -> new AnimalFood(false)
        .addFood(IIngredient.of("grain")));

    CUSTOM_ANIMAL_FOOD.put(EntityAnimalDuck.class,
      () -> new AnimalFood(true)
        .addFood(IIngredient.of("grain")));

    CUSTOM_ANIMAL_FOOD.put(EntityAnimalGoat.class,
      () -> new AnimalFood(false)
        .addFood(IIngredient.of("grain")));

    CUSTOM_ANIMAL_FOOD.put(EntityAnimalHorse.class,
      () -> new AnimalFood(false)
        .addFood(IIngredient.of("grain")));

    CUSTOM_ANIMAL_FOOD.put(EntityAnimalLlama.class,
      () -> new AnimalFood(false)
        .addFood(IIngredient.of("grain")));

    CUSTOM_ANIMAL_FOOD.put(EntityAnimalMule.class,
      () -> new AnimalFood(false)
        .addFood(IIngredient.of("grain")));

    CUSTOM_ANIMAL_FOOD.put(EntityAnimalOcelot.class,
      () -> new AnimalFood(false
      ).addFood(IIngredient.of("categoryMeat")));

    CUSTOM_ANIMAL_FOOD.put(EntityAnimalPig.class,
      () -> new AnimalFood(true)
        .addFood(IIngredient.of("grain")));

    CUSTOM_ANIMAL_FOOD.put(EntityAnimalSheep.class,
      () -> new AnimalFood(false)
        .addFood(IIngredient.of("grain")));

    CUSTOM_ANIMAL_FOOD.put(EntityAnimalWolf.class,
      () -> new AnimalFood(false)
        .addFood(IIngredient.of("categoryMeat"))
        .addFood(IIngredient.of("bone")));

    CUSTOM_ANIMAL_FOOD.put(EntityAnimalYak.class,
      () -> new AnimalFood(false)
        .addFood(IIngredient.of("grain")));

    CUSTOM_ANIMAL_FOOD.put(EntityAnimalMuskOx.class,
      () -> new AnimalFood(false)
        .addFood(IIngredient.of("grain")));

    CUSTOM_ANIMAL_FOOD.put(EntityAnimalZebu.class,
      () -> new AnimalFood(false)
        .addFood(IIngredient.of("grain")));

    CUSTOM_ANIMAL_FOOD.put(EntityAnimalQuail.class,
      () -> new AnimalFood(false)
        .addFood(IIngredient.of("grain")));

    CUSTOM_ANIMAL_FOOD.put(EntityAnimalGrouse.class,
      () -> new AnimalFood(false)
        .addFood(IIngredient.of("grain")));

    CUSTOM_ANIMAL_FOOD.put(EntityAnimalParrot.class,
      () -> new AnimalFood(false)
        .addFood(IIngredient.of("grain")));

  }

  private final List<IIngredient<ItemStack>> acceptedFoods;
  private final boolean eatRotten;

  public AnimalFood(boolean eatRotten) {
    this.eatRotten = eatRotten;
    this.acceptedFoods = new ArrayList<>();
  }

  @Nullable
  public static Supplier<AnimalFood> get(Class<? extends Entity> animalClass) {
    return CUSTOM_ANIMAL_FOOD.get(animalClass);
  }

  public AnimalFood addFood(IIngredient<ItemStack> ingredient) {
    this.acceptedFoods.add(ingredient);
    return this;
  }

  public boolean isFood(ItemStack stack) {
    for (var acceptedFood : acceptedFoods) {
      if (acceptedFood.test(stack)) {
        if (!eatRotten) {
          ICapabilityFood cap = stack.getCapability(CapabilityFood.CAPABILITY, null);
          return cap == null || !cap.isRotten();
        }
        return true;
      }
    }
    return false;
  }
}
