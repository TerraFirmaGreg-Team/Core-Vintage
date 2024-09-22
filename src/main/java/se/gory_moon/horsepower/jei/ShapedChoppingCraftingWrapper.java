package se.gory_moon.horsepower.jei;

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
import mezz.jei.api.recipe.IStackHelper;
import mezz.jei.api.recipe.wrapper.ICustomCraftingRecipeWrapper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import se.gory_moon.horsepower.Configs;
import se.gory_moon.horsepower.blocks.BlockChopper;
import se.gory_moon.horsepower.blocks.BlockHPChoppingBase;
import se.gory_moon.horsepower.recipes.ShapedChoppingRecipe;

import java.util.List;

import static su.terrafirmagreg.data.Constants.MODID_TFC;

public class ShapedChoppingCraftingWrapper implements IShapedCraftingRecipeWrapper, ICustomCraftingRecipeWrapper {

  private final ShapedChoppingRecipe recipe;
  private final int width;
  private final int height;
  private final List<List<ItemStack>> outputs;

  public ShapedChoppingCraftingWrapper(ShapedChoppingRecipe recipe) {
    this.recipe = recipe;

    for (Object input : this.recipe.getIngredients()) {
      if (input instanceof ItemStack itemStack) {
        if (itemStack.getCount() != 1) {
          itemStack.setCount(1);
        }
      }
    }
    this.width = recipe.getWidth();
    this.height = recipe.getHeight();

    ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
    for (ItemStack stack : recipe.outputBlocks) {
      BlockHPChoppingBase block = (BlockHPChoppingBase) Block.getBlockFromItem(recipe.getSimpleRecipeOutput().getItem());
      if (!Configs.general.useDynamicCrafting && !MODID_TFC.equals(stack.getItem()
              .getRegistryName()
              .getNamespace())) {
        builder.add(BlockHPChoppingBase.createItemStack(block, recipe.getSimpleRecipeOutput()
                .getCount(), new ItemStack(Blocks.LOG)));
        break;
      }
      if (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
        for (ItemStack sub : HorsePowerPlugin.jeiHelpers.getStackHelper().getSubtypes(stack)) {
          builder.add(BlockHPChoppingBase.createItemStack(block, recipe.getSimpleRecipeOutput()
                  .getCount(), sub));
        }
      } else if (Block.getBlockFromItem(stack.getItem()) instanceof BlockHPChoppingBase) {
        NonNullList<ItemStack> stacks = NonNullList.create();
        Block.getBlockFromItem(stack.getItem()).getSubBlocks(null, stacks);
        for (ItemStack sub : stacks) {
          builder.add(BlockHPChoppingBase.createItemStack(block, recipe.getSimpleRecipeOutput()
                  .getCount(), sub));
        }
      } else {
        builder.add(BlockHPChoppingBase.createItemStack(block, recipe.getSimpleRecipeOutput()
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
        BlockHPChoppingBase block = (BlockHPChoppingBase) Block.getBlockFromItem(recipe.getSimpleRecipeOutput()
                .getItem());

        // then create a stack with the focus item (which we already validated above)
        ItemStack outputFocus = BlockChopper.createItemStack(block, 1, focus);

        // and finally, set the focus override for the recipe
        guiIngredients.setOverrideDisplayFocus(HorsePowerPlugin.recipeRegistry.createFocus(IFocus.Mode.OUTPUT, outputFocus));
      }

      // if we clicked the chopping block, remove all items which affect the base textures that are not the base item
      else if (mode == IFocus.Mode.OUTPUT) {
        // so determine the base
        ItemStack base = new ItemStack(focus.hasTagCompound() ? focus.getTagCompound()
                .getCompoundTag("textureBlock") : new NBTTagCompound());
        if (Block.getBlockFromItem(recipe.outputBlocks.get(0).getItem()) instanceof BlockHPChoppingBase) {
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
    HorsePowerPlugin.craftingGridHelper.setInputs(guiItemStacks, inputs, this.getWidth(), this.getHeight());
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

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }
}
