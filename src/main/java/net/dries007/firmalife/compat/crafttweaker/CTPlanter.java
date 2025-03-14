package net.dries007.firmalife.compat.crafttweaker;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.compat.crafttweaker.CTHelper;
import net.dries007.tfc.objects.recipes.PlanterRecipe;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;

@ZenClass("mods.firmalife.Planter")
@ZenRegister
@SuppressWarnings("unused")
public class CTPlanter {

  @ZenMethod
  public static void addRecipe(String recipe_name, IIngredient input, IItemStack output, int stage, boolean large) {
    addRecipe(recipe_name, input, output, stage, large, 0);
  }

  @ZenMethod
  public static void addRecipe(String recipe_name, IIngredient input, IItemStack output, int stage, boolean large, int tier) {
    PlanterRecipe recipe = new PlanterRecipe(CTHelper.getInternalIngredient(input), InputHelper.toStack(output), stage, large, tier).setRegistryName(recipe_name);
    CraftTweakerAPI.apply(new IAction() {
      @Override
      public void apply() {
        TFCRegistries.PLANTER_QUAD.register(recipe);
      }

      @Override
      public String describe() {
        return "Adding Planter recipe " + recipe_name;
      }
    });
  }

  @ZenMethod
  public static void removeRecipe(String recipe_name) {

    PlanterRecipe recipe = TFCRegistries.PLANTER_QUAD.getValue(new ResourceLocation(recipe_name));

    if (recipe != null) {
      CraftTweakerAPI.apply(new IAction() {
        @Override
        public void apply() {
          IForgeRegistryModifiable<PlanterRecipe> Planter = (IForgeRegistryModifiable<PlanterRecipe>) TFCRegistries.PLANTER_QUAD;
          Planter.remove(recipe.getRegistryName());
        }

        @Override
        public String describe() {
          return "Removing Planter recipe " + recipe_name;
        }
      });
    }
  }

  @ZenMethod
  public static void removeRecipe(IItemStack output) {
    if (output == null) {throw new IllegalArgumentException("Output not allowed to be empty");}
    ArrayList<PlanterRecipe> removeList = new ArrayList<>();

    TFCRegistries.PLANTER_QUAD.getValuesCollection()
      .stream()
      .filter(x -> x.getOutputItem(ItemStack.EMPTY).isItemEqual(InputHelper.toStack(output)))
      .forEach(removeList::add);

    for (PlanterRecipe recipe : removeList) {
      CraftTweakerAPI.apply(new IAction() {
        @Override
        public void apply() {
          IForgeRegistryModifiable<PlanterRecipe> Planter = (IForgeRegistryModifiable<PlanterRecipe>) TFCRegistries.PLANTER_QUAD;
          Planter.remove(recipe.getRegistryName());
        }

        @Override
        public String describe() {
          return "Removing Planter recipe for output " + output.getDisplayName();
        }
      });
    }
  }
}
