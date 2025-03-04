package net.dries007.tfc.compat.crafttweaker;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipe;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipeSimple;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.dries007.tfc.api.registries.TFCRegistries;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@ZenClass("mods.terrafirmacraft.LeatherKnapping")
@ZenRegister
public class CTLeatherKnapping {

  @ZenMethod
  public static void addRecipe(String registryName, IItemStack output, String... pattern) {
    if (output == null || pattern.length < 1 || pattern.length > 5) {
      throw new IllegalArgumentException("Output item must be non-null and pattern must be a closed interval [1, 5]");
    }
    ItemStack outputStack = (ItemStack) output.getInternal();
    KnappingRecipe recipe = new KnappingRecipeSimple(KnappingType.LEATHER, true, outputStack, pattern).setRegistryName(registryName);
    CraftTweakerAPI.apply(new IAction() {
      @Override
      public void apply() {
        TFCRegistries.KNAPPING.register(recipe);
      }

      @Override
      public String describe() {
        //noinspection ConstantConditions
        return "Adding leather knapping recipe " + recipe.getRegistryName().toString();
      }
    });
  }

  @ZenMethod
  public static void removeRecipe(IItemStack output) {
    if (output == null) {throw new IllegalArgumentException("Output not allowed to be empty");}
    ItemStack item = (ItemStack) output.getInternal();
    List<KnappingRecipe> removeList = new ArrayList<>();
    TFCRegistries.KNAPPING.getValuesCollection()
      .stream()
      .filter(x -> x.getType() == KnappingType.LEATHER && x.getOutput(ItemStack.EMPTY).isItemEqual(item))
      .forEach(removeList::add);
    for (KnappingRecipe rem : removeList) {
      CraftTweakerAPI.apply(new IAction() {
        @Override
        public void apply() {
          IForgeRegistryModifiable modRegistry = (IForgeRegistryModifiable) TFCRegistries.KNAPPING;
          modRegistry.remove(rem.getRegistryName());
        }

        @Override
        public String describe() {
          //noinspection ConstantConditions
          return "Removing leather knapping recipe " + rem.getRegistryName().toString();
        }
      });
    }
  }

  @ZenMethod
  public static void removeRecipe(String registryName) {
    KnappingRecipe recipe = TFCRegistries.KNAPPING.getValue(new ResourceLocation(registryName));
    if (recipe != null) {
      CraftTweakerAPI.apply(new IAction() {
        @Override
        public void apply() {
          IForgeRegistryModifiable modRegistry = (IForgeRegistryModifiable) TFCRegistries.KNAPPING;
          modRegistry.remove(recipe.getRegistryName());
        }

        @Override
        public String describe() {
          //noinspection ConstantConditions
          return "Removing leather knapping recipe " + recipe.getRegistryName().toString();
        }
      });
    }
  }
}
