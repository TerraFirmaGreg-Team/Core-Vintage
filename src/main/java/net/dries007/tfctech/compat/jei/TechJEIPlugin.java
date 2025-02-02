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
import net.dries007.tfc.compat.jei.wrappers.SimpleRecipeWrapper;
import net.dries007.tfctech.client.gui.GuiGlassworking;
import net.dries007.tfctech.client.gui.GuiSmelteryCauldron;
import net.dries007.tfctech.compat.jei.categories.GlassworkingCategory;
import net.dries007.tfctech.compat.jei.categories.SmelteryCategory;
import net.dries007.tfctech.compat.jei.categories.WireDrawingCategory;
import net.dries007.tfctech.compat.jei.wrappers.CastingRecipeWrapper;
import net.dries007.tfctech.compat.jei.wrappers.GlassworkingRecipeWrapper;
import net.dries007.tfctech.compat.jei.wrappers.SmelteryRecipeWrapper;
import net.dries007.tfctech.compat.jei.wrappers.UnmoldRecipeWrapper;
import net.dries007.tfctech.objects.blocks.TechBlocks;
import net.dries007.tfctech.objects.items.TechItems;
import net.dries007.tfctech.objects.items.glassworking.ItemBlowpipe;
import net.dries007.tfctech.objects.items.metal.ItemTechMetal;
import net.dries007.tfctech.registry.TechRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static su.terrafirmagreg.api.data.enums.Mods.Names.TFCTECH;

@JEIPlugin
public class TechJEIPlugin implements IModPlugin {

  private static final String WIRE_DRAWING_UID = TFCTECH + ".wire_drawing";
  private static final String SMELTERY_UID = TFCTECH + ".smeltery";
  private static final String GLASSWORKING_UID = TFCTECH + ".glassworking";

  @Override
  public void registerCategories(IRecipeCategoryRegistration registry) {
    //Add new JEI recipe categories
    registry.addRecipeCategories(new WireDrawingCategory(registry.getJeiHelpers().getGuiHelper(), WIRE_DRAWING_UID));
    registry.addRecipeCategories(new SmelteryCategory(registry.getJeiHelpers().getGuiHelper(), SMELTERY_UID));
    registry.addRecipeCategories(new GlassworkingCategory(registry.getJeiHelpers().getGuiHelper(), GLASSWORKING_UID));
  }

  @Override
  public void register(IModRegistry registry) {
    // Wire drawing
    List<SimpleRecipeWrapper> wireList = TechRegistries.WIRE_DRAWING.getValuesCollection()
      .stream()
      .filter(x -> x.getIngredients().size()
                   == 2) //Only shows recipes which have a wire drawing plate (so, it can be obtained)
      .map(SimpleRecipeWrapper::new)
      .collect(Collectors.toList());

    registry.addRecipes(wireList, WIRE_DRAWING_UID);
    registry.addRecipeCatalyst(new ItemStack(TechBlocks.WIRE_DRAW_BENCH), WIRE_DRAWING_UID);

    // Glassworking (blowpipe)
    List<GlassworkingRecipeWrapper> glassList = TechRegistries.GLASSWORKING.getValuesCollection()
      .stream()
      .map(x -> new GlassworkingRecipeWrapper(x, registry.getJeiHelpers().getGuiHelper()))
      .collect(Collectors.toList());

    registry.addRecipes(glassList, GLASSWORKING_UID);
    TFCRegistries.METALS.getValuesCollection().forEach(metal -> {
      ItemBlowpipe blowpipe = ItemBlowpipe.get(metal);
      if (blowpipe != null) {
        registry.addRecipeCatalyst(new ItemStack(blowpipe), GLASSWORKING_UID);
      }
    });

    // Smeltery
    List<SmelteryRecipeWrapper> smelteryList = TechRegistries.SMELTERY.getValuesCollection()
      .stream()
      .map(SmelteryRecipeWrapper::new)
      .collect(Collectors.toList());

    registry.addRecipes(smelteryList, SMELTERY_UID);
    registry.addRecipeCatalyst(new ItemStack(TechBlocks.SMELTERY_CAULDRON), SMELTERY_UID);

    // Click areas
    registry.addRecipeClickArea(GuiSmelteryCauldron.class, 52, 58, 72, 15, SMELTERY_UID);
    registry.addRecipeClickArea(GuiGlassworking.class, 132, 27, 9, 14, GLASSWORKING_UID);

    // Information
    registry.addIngredientInfo(new ItemStack(TechItems.IRON_GROOVE), VanillaTypes.ITEM, "jei.information.tfctech.groove");
    registry.addIngredientInfo(new FluidStack(FluidsCore.LATEX.get(), 1000), VanillaTypes.FLUID, "jei.information.tfctech.latex");
    registry.addIngredientInfo(new ItemStack(TechBlocks.FRIDGE), VanillaTypes.ITEM, "jei.information.tfctech.fridge");
    registry.addIngredientInfo(new ItemStack(TechBlocks.WIRE_DRAW_BENCH), VanillaTypes.ITEM, "jei.information.tfctech.wiredraw");
    registry.addIngredientInfo(new ItemStack(TechBlocks.INDUCTION_CRUCIBLE), VanillaTypes.ITEM, "jei.information.tfctech.crucible");
    registry.addIngredientInfo(new ItemStack(TechBlocks.ELECTRIC_FORGE), VanillaTypes.ITEM, "jei.information.tfctech.forge");

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
