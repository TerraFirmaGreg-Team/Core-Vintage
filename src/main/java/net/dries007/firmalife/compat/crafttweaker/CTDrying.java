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
import net.dries007.tfc.objects.recipes.DryingRecipe;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;

@ZenClass("mods.firmalife.Drying")
@ZenRegister
@SuppressWarnings("unused")
public class CTDrying {

  @ZenMethod
  public static void addRecipe(String recipe_name, IIngredient input, IItemStack output, int duration) {
    DryingRecipe recipe = new DryingRecipe(CTHelper.getInternalIngredient(input), InputHelper.toStack(output), duration).setRegistryName(recipe_name);
    CraftTweakerAPI.apply(new IAction() {
      @Override
      public void apply() {
        TFCRegistries.DRYING.register(recipe);
      }

      @Override
      public String describe() {
        return "Adding Drying recipe " + recipe.getRegistryName().toString();
      }
    });
  }

  @ZenMethod
  public static void removeRecipe(String recipe_name) {

    DryingRecipe recipe = TFCRegistries.DRYING.getValue(new ResourceLocation(recipe_name));

    if (recipe != null) {
      CraftTweakerAPI.apply(new IAction() {
        @Override
        public void apply() {
          IForgeRegistryModifiable<DryingRecipe> DRYING = (IForgeRegistryModifiable<DryingRecipe>) TFCRegistries.DRYING;
          DRYING.remove(recipe.getRegistryName());
        }

        @Override
        public String describe() {
          return "Removing Drying recipe " + recipe_name;
        }
      });
    }
  }

  @ZenMethod
  public static void removeRecipe(IItemStack output) {
    if (output == null) {throw new IllegalArgumentException("Output not allowed to be empty");}
    ArrayList<DryingRecipe> removeList = new ArrayList<>();

    TFCRegistries.DRYING.getValuesCollection()
      .stream()
      .filter(x -> x.getOutputItem(ItemStack.EMPTY).isItemEqual(InputHelper.toStack(output)))
      .forEach(removeList::add);

    for (DryingRecipe recipe : removeList) {
      CraftTweakerAPI.apply(new IAction() {
        @Override
        public void apply() {
          IForgeRegistryModifiable<DryingRecipe> DRYING = (IForgeRegistryModifiable<DryingRecipe>) TFCRegistries.DRYING;
          DRYING.remove(recipe.getRegistryName());
        }

        @Override
        public String describe() {
          return "Removing Drying recipe for output " + output.getDisplayName();
        }
      });
    }
  }
}
