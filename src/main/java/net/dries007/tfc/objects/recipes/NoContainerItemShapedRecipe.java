package net.dries007.tfc.objects.recipes;

import com.google.gson.JsonObject;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class NoContainerItemShapedRecipe extends ShapedOreRecipe {

  private NoContainerItemShapedRecipe(ResourceLocation group, @NotNull ItemStack result, CraftingHelper.ShapedPrimer primer) {
    super(group, result, primer);
  }

  @NotNull
  @Override
  public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
    return NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
  }

  public static class Factory implements IRecipeFactory {

    @Override
    public IRecipe parse(final JsonContext context, final JsonObject json) {
      String group = JsonUtils.getString(json, "group", "");
      CraftingHelper.ShapedPrimer primer = RecipeUtils.parsePhaped(context, json);
      ItemStack result = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);
      return new NoContainerItemShapedRecipe(group.isEmpty() ? null : new ResourceLocation(group), result, primer);
    }
  }
}
