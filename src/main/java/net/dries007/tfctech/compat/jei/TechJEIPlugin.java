package net.dries007.tfctech.compat.jei;

import su.terrafirmagreg.modules.core.init.FluidsCore;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.TechItems;
import net.dries007.tfc.objects.items.metal.ItemTechMetal;
import net.dries007.tfctech.compat.jei.wrappers.CastingRecipeWrapper;
import net.dries007.tfctech.compat.jei.wrappers.UnmoldRecipeWrapper;

import java.util.ArrayList;
import java.util.List;

@JEIPlugin
public class TechJEIPlugin implements IModPlugin {

  @Override
  public void registerCategories(IRecipeCategoryRegistration registry) {
    //Add new JEI recipe categories

  }

  @Override
  public void register(IModRegistry registry) {

    //Wraps all unmold and casting recipes
    List<UnmoldRecipeWrapper> unmoldList = new ArrayList<>();
    List<CastingRecipeWrapper> castingList = new ArrayList<>();
    TFCRegistries.METALS.getValuesCollection()
                        .forEach(metal -> {
                          if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(true)) {
                            for (ItemTechMetal.ItemType type : ItemTechMetal.ItemType.values()) {
                              if (type.hasMold() && ItemTechMetal.get(metal, type) != null) {
                                unmoldList.add(new UnmoldRecipeWrapper(metal, type));
                                castingList.add(new CastingRecipeWrapper(metal, type));
                              }
                            }
                          }
                        });

    // Glass unmolding
    ItemStack input = new ItemStack(TechItems.MOLD_BLOCK);
    IFluidHandlerItem cap = input.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
    if (cap != null) {
      cap.fill(new FluidStack(FluidsCore.GLASS.get(), 1000), true);
    }
    unmoldList.add(new UnmoldRecipeWrapper(input, new ItemStack(Blocks.GLASS)));
    registry.addIngredientInfo(input, VanillaTypes.ITEM, "jei.information.tfctech.fill_mold");

    input = new ItemStack(TechItems.MOLD_PANE);
    cap = input.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
    if (cap != null) {
      cap.fill(new FluidStack(FluidsCore.GLASS.get(), 375), true);
    }
    unmoldList.add(new UnmoldRecipeWrapper(input, new ItemStack(Blocks.GLASS_PANE)));
    registry.addIngredientInfo(input, VanillaTypes.ITEM, "jei.information.tfctech.fill_mold");

    registry.addRecipes(unmoldList, VanillaRecipeCategoryUid.CRAFTING);
    registry.addRecipes(castingList, "tfc.casting");
  }
}
