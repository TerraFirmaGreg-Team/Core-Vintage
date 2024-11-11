package se.gory_moon.horsepower.jei;

import su.terrafirmagreg.api.base.block.BaseBlockHorse;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.ImmutableList;
import mezz.jei.api.gui.IGuiIngredientGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import mezz.jei.api.recipe.wrapper.ICustomCraftingRecipeWrapper;
import se.gory_moon.horsepower.Configs;
import se.gory_moon.horsepower.blocks.BlockChopperHorse;
import se.gory_moon.horsepower.recipes.ShapelessChoppingRecipe;

import java.util.List;

import static su.terrafirmagreg.api.data.Reference.MODID_TFC;

public class ShapelessChoppingCraftingWrapper implements IRecipeWrapper, ICustomCraftingRecipeWrapper {

  private final ShapelessChoppingRecipe recipe;
  private final List<List<ItemStack>> outputs;

  public ShapelessChoppingCraftingWrapper(ShapelessChoppingRecipe recipe) {
    this.recipe = recipe;

    for (Object input : this.recipe.getIngredients()) {
      if (input instanceof ItemStack itemStack) {
        if (itemStack.getCount() != 1) {
          itemStack.setCount(1);
        }
      }
    }

    ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
    for (ItemStack stack : recipe.outputBlocks) {
      BaseBlockHorse block = (BaseBlockHorse) Block.getBlockFromItem(recipe.getSimpleRecipeOutput()
                                                                           .getItem());
      if (!Configs.general.useDynamicCrafting && !MODID_TFC.equals(stack.getItem()
                                                                        .getRegistryName()
                                                                        .getNamespace())) {
        builder.add(BaseBlockHorse.createItemStack(block, recipe.getSimpleRecipeOutput()
                                                                .getCount(), new ItemStack(Blocks.LOG)));
        break;
      }
      if (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
        for (ItemStack sub : HorsePowerPlugin.jeiHelpers.getStackHelper().getSubtypes(stack)) {
          builder.add(BaseBlockHorse.createItemStack(block, recipe.getSimpleRecipeOutput()
                                                                  .getCount(), sub));
        }
      } else if (Block.getBlockFromItem(stack.getItem()) instanceof BaseBlockHorse) {
        NonNullList<ItemStack> stacks = NonNullList.create();
        Block.getBlockFromItem(stack.getItem()).getSubBlocks(null, stacks);
        for (ItemStack sub : stacks) {
          builder.add(BaseBlockHorse.createItemStack(block, recipe.getSimpleRecipeOutput()
                                                                  .getCount(), sub));
        }
      } else {
        builder.add(BaseBlockHorse.createItemStack(block, recipe.getSimpleRecipeOutput()
                                                                .getCount(), stack));
      }
    }
    outputs = ImmutableList.of(builder.build());
  }

  @Override
  public void getIngredients(IIngredients ingredients) {
    IStackHelper stackHelper = HorsePowerPlugin.jeiHelpers.getStackHelper();

    List<List<ItemStack>> inputs = stackHelper.expandRecipeItemStackInputs(recipe.getIngredients());
    ingredients.setInputLists(ItemStack.class, inputs);

    if (!outputs.isEmpty()) {
      ingredients.setOutputLists(ItemStack.class, outputs);
    }
  }

  @Override
  public void setRecipe(IRecipeLayout recipeLayout, IIngredients ingredients) {
    recipeLayout.setShapeless();
    IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

    List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
    List<ItemStack> outputs = ingredients.getOutputs(ItemStack.class).get(0);

    // determine the focused stack
    IFocus<?> ifocus = recipeLayout.getFocus();
    Object focusObj = ifocus.getValue();

    // if the thing in focus is an itemstack
    if (focusObj instanceof ItemStack focus) {
      IGuiIngredientGroup<ItemStack> guiIngredients = recipeLayout.getIngredientsGroup(ItemStack.class);
      IFocus.Mode mode = ifocus.getMode();

      // input means we clicked on an ingredient, make sure it is one that affects the base
      if (mode == IFocus.Mode.INPUT && isOutputBlock(focus)) {
        // first, get the output recipe
        BaseBlockHorse block = (BaseBlockHorse) Block.getBlockFromItem(recipe.getSimpleRecipeOutput()
                                                                             .getItem());

        // then create a stack with the focus item (which we already validated above)
        ItemStack outputFocus = BlockChopperHorse.createItemStack(block, 1, focus);

        // and finally, set the focus override for the recipe
        guiIngredients.setOverrideDisplayFocus(HorsePowerPlugin.recipeRegistry.createFocus(IFocus.Mode.OUTPUT, outputFocus));
      }

      // if we clicked the chopping block, remove all items which affect the base textures that are not the base item
      else if (mode == IFocus.Mode.OUTPUT) {
        // so determine the base
        ItemStack base = new ItemStack(focus.hasTagCompound() ? focus.getTagCompound()
                                                                     .getCompoundTag("textureBlock") : new NBTTagCompound());
        if (Block.getBlockFromItem(recipe.outputBlocks.get(0).getItem()) instanceof BaseBlockHorse) {
          base = recipe.outputBlocks.get(0).copy();
          NBTTagCompound tag = new NBTTagCompound();
          tag.setTag("textureBlock", focus.hasTagCompound() ? focus.getTagCompound()
                                                                   .getCompoundTag("textureBlock") : new NBTTagCompound());
          base.setTagCompound(tag);
        }
        if (!base.isEmpty()) {
          // and loop through all slots removing leg affecting inputs which don't match
          guiIngredients.setOverrideDisplayFocus(HorsePowerPlugin.recipeRegistry.createFocus(IFocus.Mode.INPUT, base));
        }
      }
    }

    // add the itemstacks to the grid
    HorsePowerPlugin.craftingGridHelper.setInputs(guiItemStacks, inputs);
    recipeLayout.getItemStacks().set(0, outputs);
  }

  private boolean isOutputBlock(ItemStack stack) {
    if (stack.isEmpty()) {
      return false;
    }

    for (ItemStack output : recipe.outputBlocks) {
      // if the item matches the oredict entry, it is an output block
      if (OreDictionary.itemMatches(output, stack, false)) {
        return true;
      }
    }

    return false;
  }
}
