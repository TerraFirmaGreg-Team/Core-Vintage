package net.dries007.firmalife.compat.crafttweaker;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.compat.crafttweaker.CTHelper;
import net.dries007.tfc.objects.recipes.StrainingRecipe;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.firmalife.Straining")
@ZenRegister
@SuppressWarnings("unused")
public class CTStraining {

  @ZenMethod
  public static void addRecipe(String recipe_name, ILiquidStack inputFluid, IItemStack outputItem, ILiquidStack outputFluid) {
    if (inputFluid == null) {throw new IllegalArgumentException("Recipe must have an input");} else if (outputItem == null && outputFluid == null) {
      throw new IllegalArgumentException("Recipe must have at least one output");
    }

    ItemStack outItem = outputItem == null ? ItemStack.EMPTY : (ItemStack) outputItem.getInternal();
    FluidStack outFluid = outputFluid == null ? null : (FluidStack) outputFluid.getInternal();

    StrainingRecipe recipe = new StrainingRecipe(CTHelper.getInternalIngredient(inputFluid), outItem, outFluid);

    CraftTweakerAPI.apply(new IAction() {
      @Override
      public void apply() {
        TFCRegistries.STRAINING.register(recipe);
      }

      @Override
      public String describe() {
        return "Adding Straining recipe " + recipe_name;
      }
    });
  }

  @ZenMethod
  public static void removeRecipe(String recipe_name) {
    StrainingRecipe recipe = TFCRegistries.STRAINING.getValue(new ResourceLocation(recipe_name));

    if (recipe != null) {
      CraftTweakerAPI.apply(new IAction() {
        @Override
        public void apply() {
          IForgeRegistryModifiable<StrainingRecipe> Strain = (IForgeRegistryModifiable<StrainingRecipe>) TFCRegistries.STRAINING;
          Strain.remove(recipe.getRegistryName());
        }

        @Override
        public String describe() {
          return "Removing Straining recipe " + recipe_name;
        }
      });
    }
  }
}
