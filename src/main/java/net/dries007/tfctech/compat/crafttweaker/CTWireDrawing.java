package net.dries007.tfctech.compat.crafttweaker;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.compat.crafttweaker.CTHelper;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import net.dries007.tfctech.api.recipes.WireDrawingRecipe;
import net.dries007.tfctech.registry.TechRegistries;

@ZenClass("mods.tfctech.WireDrawing")
@ZenRegister
public class CTWireDrawing {

  @ZenMethod
  public static void addRecipe(String registryName, crafttweaker.api.item.IIngredient input, int minTier, IItemStack output, int color) {
    if (output == null || input == null) {throw new IllegalArgumentException("Input and output are not allowed to be empty");}
    if (input instanceof ILiquidStack) {throw new IllegalArgumentException("There is a fluid where it's supposed to be an item!");}
    //noinspection rawtypes
    IIngredient ingredient = CTHelper.getInternalIngredient(input);
    Metal.Tier tier = Metal.Tier.valueOf(minTier);
    ItemStack outputItem = (ItemStack) output.getInternal();
    //noinspection unchecked
    WireDrawingRecipe recipe = new WireDrawingRecipe(new ResourceLocation(registryName), ingredient, tier, outputItem, color);
    CraftTweakerAPI.apply(new IAction() {
      @Override
      public void apply() {
        TechRegistries.WIRE_DRAWING.register(recipe);
      }

      @Override
      public String describe() {
        return "Adding wire drawing recipe for " + outputItem.getDisplayName();
      }
    });
  }

  @ZenMethod
  public static void removeRecipe(IItemStack output) {
    if (output == null) {throw new IllegalArgumentException("Output not allowed to be empty");}
    ItemStack item = (ItemStack) output.getInternal();
    List<WireDrawingRecipe> removeList = new ArrayList<>();
    TechRegistries.WIRE_DRAWING.getValuesCollection()
      .stream()
      .filter(x -> x.getOutputs().get(0).isItemEqual(item))
      .forEach(removeList::add);
    for (WireDrawingRecipe rem : removeList) {
      CraftTweakerAPI.apply(new IAction() {
        @Override
        public void apply() {
          IForgeRegistryModifiable<WireDrawingRecipe> modRegistry = (IForgeRegistryModifiable<WireDrawingRecipe>) TechRegistries.WIRE_DRAWING;
          modRegistry.remove(rem.getRegistryName());
        }

        @Override
        public String describe() {
          //noinspection ConstantConditions
          return "Removing wire drawing recipe " + rem.getRegistryName().toString();
        }
      });
    }
  }

  @ZenMethod
  public static void removeRecipe(String registryName) {
    WireDrawingRecipe recipe = TechRegistries.WIRE_DRAWING.getValue(new ResourceLocation(registryName));
    if (recipe != null) {
      CraftTweakerAPI.apply(new IAction() {
        @Override
        public void apply() {
          IForgeRegistryModifiable<WireDrawingRecipe> modRegistry = (IForgeRegistryModifiable<WireDrawingRecipe>) TechRegistries.WIRE_DRAWING;
          modRegistry.remove(recipe.getRegistryName());
        }

        @Override
        public String describe() {
          //noinspection ConstantConditions
          return "Removing wire drawing recipe " + recipe.getRegistryName().toString();
        }
      });
    }
  }
}
