package su.terrafirmagreg.modules.core.capabilities.food;

import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodData;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CapabilityHandlerFood {

  public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_FOODS = new HashMap<>(); //Used inside CT, set custom IFood for food items outside TFC


  public static void init() {
    // Add custom vanilla food instances
    CUSTOM_FOODS.put(IIngredient.of(Items.ROTTEN_FLESH),
      () -> new CapabilityProviderFood(null, FoodData.ROTTEN_FLESH));

    CUSTOM_FOODS.put(IIngredient.of(Items.GOLDEN_APPLE),
      () -> new CapabilityProviderFood(null, FoodData.GOLDEN_APPLE));

    CUSTOM_FOODS.put(IIngredient.of(Items.GOLDEN_CARROT),
      () -> new CapabilityProviderFood(null, FoodData.GOLDEN_CARROT));

    CUSTOM_FOODS.put(IIngredient.of(Items.EGG),
      () -> new CapabilityProviderFood(null, FoodData.RAW_EGG));

    CUSTOM_FOODS.put(IIngredient.of(Items.MUSHROOM_STEW),
      () -> new CapabilityProviderFood(null, FoodData.MUSHROOM_STEW));

    CUSTOM_FOODS.put(IIngredient.of(Items.RABBIT_STEW),
      () -> new CapabilityProviderFood(null, FoodData.RABBIT_STEW));

    CUSTOM_FOODS.put(IIngredient.of(Items.BEETROOT_SOUP),
      () -> new CapabilityProviderFood(null, FoodData.BEETROOT_SOUP));

  }
}
