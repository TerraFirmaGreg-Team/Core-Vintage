package net.dries007.horsepower.jei;

import su.terrafirmagreg.api.base.block.BaseBlockHorse;
import su.terrafirmagreg.modules.device.init.BlocksDevice;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.dries007.horsepower.Configs;
import net.dries007.horsepower.blocks.ModBlocks;
import net.dries007.horsepower.jei.chopping.ChoppingRecipeMaker;
import net.dries007.horsepower.jei.chopping.ChoppingRecipeWrapper;
import net.dries007.horsepower.jei.chopping.HPChoppingCategory;
import net.dries007.horsepower.jei.chopping.manual.HPManualChoppingCategory;
import net.dries007.horsepower.jei.chopping.manual.ManualChoppingRecipeWrapper;
import net.dries007.horsepower.jei.grinding.GrindingRecipeMaker;
import net.dries007.horsepower.jei.grinding.GrindstoneRecipeWrapper;
import net.dries007.horsepower.jei.grinding.HorsePowerGrindingCategory;
import net.dries007.horsepower.jei.press.HorsePowerPressCategory;
import net.dries007.horsepower.jei.press.PressRecipeMaker;
import net.dries007.horsepower.jei.press.PressRecipeWrapper;
import net.dries007.horsepower.recipes.ChoppingBlockRecipe;
import net.dries007.horsepower.recipes.GrindstoneRecipe;
import net.dries007.horsepower.recipes.HandGrindstoneRecipe;
import net.dries007.horsepower.recipes.PressRecipe;
import net.dries007.horsepower.recipes.ShapedChoppingRecipe;
import net.dries007.horsepower.recipes.ShapelessChoppingRecipe;

@JEIPlugin
public class HorsePowerPlugin implements IModPlugin {

  public static final String HAND_GRINDING = "horsepower.hand_grinding";
  public static final String GRINDING = "horsepower.grinding";
  public static final String MANUAL_CHOPPING = "horsepower.manual_chopping";
  public static final String CHOPPING = "horsepower.chopping";
  public static final String PRESS_ITEM = "horsepower.press";
  public static final String PRESS_FLUID = "horsepower.press_fluid";

  public static IJeiHelpers jeiHelpers;
  public static IGuiHelper guiHelper;
  public static IRecipeRegistry recipeRegistry;
  public static ICraftingGridHelper craftingGridHelper;
  public static IIngredientRegistry ingredientRegistry;

  @Override
  public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
    subtypeRegistry.registerSubtypeInterpreter(Item.getItemFromBlock(ModBlocks.BLOCK_CHOPPER), itemStack ->
    {
      NBTTagCompound nbtTagCompound = itemStack.getTagCompound();
      if (itemStack.getMetadata() == OreDictionary.WILDCARD_VALUE || nbtTagCompound == null || nbtTagCompound.isEmpty()) {
        return null;
      }
      return nbtTagCompound.toString();
    });
    subtypeRegistry.registerSubtypeInterpreter(Item.getItemFromBlock(ModBlocks.BLOCK_MANUAL_CHOPPER), itemStack ->
    {
      NBTTagCompound nbtTagCompound = itemStack.getTagCompound();
      if (itemStack.getMetadata() == OreDictionary.WILDCARD_VALUE || nbtTagCompound == null || nbtTagCompound.isEmpty()) {
        return null;
      }
      return nbtTagCompound.toString();
    });
  }

  @Override
  public void registerIngredients(IModIngredientRegistration registry) {

  }

  @Override
  public void registerCategories(IRecipeCategoryRegistration registry) {
    if (Configs.recipes.useSeperateGrindstoneRecipes) {
      registry.addRecipeCategories(new HorsePowerGrindingCategory(registry.getJeiHelpers().getGuiHelper(), true));
    }
    registry.addRecipeCategories(new HorsePowerGrindingCategory(registry.getJeiHelpers().getGuiHelper(), false));
    if (Configs.general.enableHandChoppingBlock) {
      registry.addRecipeCategories(new HPManualChoppingCategory(registry.getJeiHelpers().getGuiHelper()));
    }
    registry.addRecipeCategories(new HPChoppingCategory(registry.getJeiHelpers().getGuiHelper()));

    registry.addRecipeCategories(new HorsePowerPressCategory(registry.getJeiHelpers().getGuiHelper(), false));
    registry.addRecipeCategories(new HorsePowerPressCategory(registry.getJeiHelpers().getGuiHelper(), true));
  }

  @Override
  public void register(IModRegistry registry) {
    ingredientRegistry = registry.getIngredientRegistry();
    jeiHelpers = registry.getJeiHelpers();
    guiHelper = jeiHelpers.getGuiHelper();
    craftingGridHelper = guiHelper.createCraftingGridHelper(1, 0);

    if (Configs.recipes.useSeperateGrindstoneRecipes) {
      registry.handleRecipes(HandGrindstoneRecipe.class, GrindstoneRecipeWrapper::new, HAND_GRINDING);
      registry.addRecipes(GrindingRecipeMaker.getGrindstoneRecipes(jeiHelpers, true), HAND_GRINDING);
    }

    if (Configs.general.enableHandChoppingBlock) {
      ManualChoppingRecipeWrapper.setAxes();
      registry.handleRecipes(ChoppingBlockRecipe.class, ManualChoppingRecipeWrapper::new, MANUAL_CHOPPING);
      if (Configs.recipes.useSeperateChoppingRecipes) {
        registry.addRecipes(ChoppingRecipeMaker.getChoppingRecipes(jeiHelpers, true, true), MANUAL_CHOPPING);
      } else {
        registry.addRecipes(ChoppingRecipeMaker.getChoppingRecipes(jeiHelpers, true, false), MANUAL_CHOPPING);
      }
    }

    registry.handleRecipes(GrindstoneRecipe.class, GrindstoneRecipeWrapper::new, GRINDING);
    registry.addRecipes(GrindingRecipeMaker.getGrindstoneRecipes(jeiHelpers, false), GRINDING);

    registry.handleRecipes(ChoppingBlockRecipe.class, ChoppingRecipeWrapper::new, CHOPPING);
    registry.addRecipes(ChoppingRecipeMaker.getChoppingRecipes(jeiHelpers, false, false), CHOPPING);

    registry.handleRecipes(PressRecipe.class, PressRecipeWrapper::new, PRESS_ITEM);
    registry.handleRecipes(PressRecipe.class, PressRecipeWrapper::new, PRESS_FLUID);
    registry.addRecipes(PressRecipeMaker.getPressItemRecipes(jeiHelpers), PRESS_ITEM);
    registry.addRecipes(PressRecipeMaker.getPressFluidRecipes(jeiHelpers), PRESS_FLUID);

    registry.handleRecipes(ShapedChoppingRecipe.class, ShapedChoppingCraftingWrapper::new, VanillaRecipeCategoryUid.CRAFTING);
    registry.handleRecipes(ShapelessChoppingRecipe.class, ShapelessChoppingCraftingWrapper::new, VanillaRecipeCategoryUid.CRAFTING);

    if (Configs.general.enableHandChoppingBlock) {
      ItemStack itemStackManualChopper = BaseBlockHorse.createItemStack(ModBlocks.BLOCK_MANUAL_CHOPPER, 1, new ItemStack(Item.getItemFromBlock(Blocks.LOG)));
      registry.addRecipeCatalyst(itemStackManualChopper, MANUAL_CHOPPING);
    }
    registry.addRecipeCatalyst(new ItemStack(BlocksDevice.QUERN_HORSE), GRINDING);

    ItemStack itemStackChopper = BaseBlockHorse.createItemStack(ModBlocks.BLOCK_CHOPPER, 1, new ItemStack(Item.getItemFromBlock(Blocks.LOG)));
    registry.addRecipeCatalyst(itemStackChopper, CHOPPING);
    registry.addRecipeCatalyst(new ItemStack(ModBlocks.BLOCK_PRESS), PRESS_ITEM);
    registry.addRecipeCatalyst(new ItemStack(ModBlocks.BLOCK_PRESS), PRESS_FLUID);

    registry.addIngredientInfo(new ItemStack(BlocksDevice.QUERN_HORSE), VanillaTypes.ITEM, "info.horsepower:grindstone.info1",
                               "info.horsepower:grindstone.info2", "info.horsepower:grindstone.info3");
  }

  @Override
  public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
    recipeRegistry = jeiRuntime.getRecipeRegistry();
  }
}
