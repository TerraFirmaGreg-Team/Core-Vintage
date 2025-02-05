package net.dries007.tfc.compat.crafttweaker;

import su.terrafirmagreg.modules.core.capabilities.food.CapabilityHandlerFood;
import su.terrafirmagreg.modules.core.capabilities.food.CapabilityProviderFood;
import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodData;
import su.terrafirmagreg.modules.core.capabilities.metal.CapabilityHandlerMetal;
import su.terrafirmagreg.modules.core.capabilities.metal.CapabilityProviderMetal;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilityHandlerSize;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.terrafirmacraft.ItemRegistry")
@ZenRegister
public class CTItemRegistry {

  @ZenMethod
  @SuppressWarnings({"unchecked", "rawtypes"})
  public static void registerItemSize(crafttweaker.api.item.IIngredient input, String inputSize, String inputWeight) {
    if (input == null) {throw new IllegalArgumentException("Input not allowed to be empty!");}
    if (input instanceof ILiquidStack) {throw new IllegalArgumentException("There is a fluid where it's supposed to be an item!");}
    IIngredient inputIngredient = CTHelper.getInternalIngredient(input);
    Size size = Size.valueOf(inputSize.toUpperCase());
    Weight weight = Weight.valueOf(inputWeight.toUpperCase());
    if (CapabilityHandlerSize.CUSTOM_ITEMS.get(inputIngredient) != null) {
      throw new IllegalStateException("Input registered more than once!");
    } else {
      CraftTweakerAPI.apply(new IAction() {
        @Override
        public void apply() {
          CapabilityHandlerSize.CUSTOM_ITEMS.put(inputIngredient, () -> CapabilityProviderSize.of(size, weight, true));
        }

        @Override
        public String describe() {
          return "Registered size and weight for " + input.toCommandString();
        }
      });
    }
  }

  @ZenMethod
  @SuppressWarnings({"unchecked", "rawtypes"})
  public static void registerItemMetal(crafttweaker.api.item.IIngredient input, String metalStr, int amount, boolean canMelt) {
    if (input == null) {throw new IllegalArgumentException("Input not allowed to be empty!");}
    if (input instanceof ILiquidStack) {throw new IllegalArgumentException("There is a fluid where it's supposed to be an item!");}
    //noinspection ConstantConditions
    Metal metal = TFCRegistries.METALS.getValuesCollection().stream()
      .filter(x -> x.getRegistryName().getPath().equalsIgnoreCase(metalStr)).findFirst().orElse(null);
    if (metal == null) {throw new IllegalArgumentException("Metal specified not found!");}
    IIngredient inputIngredient = CTHelper.getInternalIngredient(input);
    if (CapabilityHandlerMetal.CUSTOM_ITEMS.get(inputIngredient) != null) {
      throw new IllegalStateException("Input already registered in metal item capability!");
    } else {
      CraftTweakerAPI.apply(new IAction() {
        @Override
        public void apply() {
          CapabilityHandlerMetal.CUSTOM_ITEMS.put(inputIngredient, () -> new CapabilityProviderMetal(metal, amount, canMelt));
        }

        @Override
        public String describe() {
          return "Registered metal item capability for " + input.toCommandString();
        }
      });
    }
  }


  @ZenMethod
  @SuppressWarnings({"unchecked", "rawtypes"})
  public static void registerFood(crafttweaker.api.item.IIngredient input, int hunger, float water, float saturation, float decay, float grain, float veg, float fruit, float protein, float dairy) {
    if (input == null) {
      throw new IllegalArgumentException("Input not allowed to be empty!");
    }
    if (input instanceof ILiquidStack) {
      throw new IllegalArgumentException("There is a fluid where it's supposed to be an item!");
    }
    IIngredient inputIngredient = CTHelper.getInternalIngredient(input);
    CraftTweakerAPI.apply(new IAction() {
      @Override
      public void apply() {
        CapabilityHandlerFood.CUSTOM_FOODS.put(inputIngredient, () -> new CapabilityProviderFood(null, new FoodData(hunger, water, saturation, grain, fruit, veg, protein, dairy, decay)));
      }

      @Override
      public String describe() {
        return "Registered food stats for " + input.toCommandString();
      }
    });
  }

}
