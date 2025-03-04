package net.dries007.tfc.objects.recipes;

import su.terrafirmagreg.modules.core.feature.skill.SmithingSkill;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.google.gson.JsonObject;

import javax.annotation.Nonnull;

public class ShapedSkillRecipe extends ShapedOreRecipe {

  private ShapedSkillRecipe(ResourceLocation group, @Nonnull ItemStack result, CraftingHelper.ShapedPrimer primer) {
    super(group, result, primer);
  }

  @Nonnull
  @Override
  public ItemStack getCraftingResult(@Nonnull InventoryCrafting inventory) {
    for (int i = 0; i < inventory.getSizeInventory(); i++) {
      ItemStack inputStack = inventory.getStackInSlot(i);
      float skillBonus = SmithingSkill.getSkillBonus(inputStack);
      if (skillBonus > 0) {
        ItemStack outputStack = super.getCraftingResult(inventory);
        SmithingSkill.copySkillBonus(outputStack, inputStack);
        return outputStack;
      }
    }
    return super.getCraftingResult(inventory);
  }

  @SuppressWarnings("unused")
  public static class Factory implements IRecipeFactory {

    @Override
    public IRecipe parse(final JsonContext context, final JsonObject json) {
      String group = JsonUtils.getString(json, "group", "");

      CraftingHelper.ShapedPrimer primer = RecipeUtils.parsePhaped(context, json);

      ItemStack result = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);
      return new ShapedSkillRecipe(group.isEmpty() ? null : new ResourceLocation(group), result, primer);
    }
  }
}
