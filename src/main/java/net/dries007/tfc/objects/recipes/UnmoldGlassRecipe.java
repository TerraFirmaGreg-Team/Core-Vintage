package net.dries007.tfc.objects.recipes;

import su.terrafirmagreg.data.MathConstants;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.IForgeRegistryEntry;

import com.google.gson.JsonObject;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.objects.items.glassworking.ItemGlassMolder;

import org.jetbrains.annotations.NotNull;

import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY;

/**
 * Used for unmolding glass molds, since they don't extend ItemTechMolds or TFC's ItemMold
 */
@SuppressWarnings("unused")
public class UnmoldGlassRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

  private final NonNullList<Ingredient> input;
  private final ResourceLocation group;
  private final ItemStack result;
  /* This is return chance, not break chance */
  private final float chance;

  public UnmoldGlassRecipe(ResourceLocation group, NonNullList<Ingredient> input, @NotNull ItemStack result, float chance) {
    this.group = group;
    this.input = input;
    this.result = result;
    this.chance = chance;
  }

  public float getChance() {
    return chance;
  }

  @Override
  public boolean matches(@NotNull InventoryCrafting inv, @NotNull World world) {
    boolean found = false;
    for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
      ItemStack stack = inv.getStackInSlot(slot);
      if (!stack.isEmpty()) {
        if (found) {
          return false;
        }
        for (Ingredient ingredient : this.getIngredients()) {
          if (ingredient.apply(stack)) {
            found = true;
            break;
          }
        }
        if (!found) {
          return false;
        }
      }
    }
    return found;
  }

  @Override
  @NotNull
  public ItemStack getCraftingResult(InventoryCrafting inv) {
    ItemStack moldStack = null;
    for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
      ItemStack stack = inv.getStackInSlot(slot);
      if (!stack.isEmpty()) {
        if (moldStack != null) {
          return ItemStack.EMPTY;
        }
        for (Ingredient ingredient : this.getIngredients()) {
          if (ingredient.apply(stack)) {
            moldStack = stack;
            break;
          }
        }
        if (moldStack == null) {
          return ItemStack.EMPTY;
        }
      }
    }
    if (moldStack != null) {
      IFluidHandler moldCap = moldStack.getCapability(FLUID_HANDLER_ITEM_CAPABILITY, null);
      if (moldCap instanceof ItemGlassMolder.GlassMolderCapability cap) {
        if (cap.isSolidified()) {
          return getRecipeOutput();
        }
      }
    }
    return ItemStack.EMPTY;
  }

  @Override
  public boolean canFit(int width, int height) {
    return true;
  }

  @Override
  @NotNull
  public ItemStack getRecipeOutput() {
    return result.copy();
  }

  @Override
  @NotNull
  public NonNullList<ItemStack> getRemainingItems(final InventoryCrafting inv) {
    // Return empty molds
    for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
      ItemStack stack = inv.getStackInSlot(slot);
      if (!stack.isEmpty()) {
        EntityPlayer player = ForgeHooks.getCraftingPlayer();
        if (!player.world.isRemote) {
          if (MathConstants.RNG.nextFloat() <= chance) {
            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(stack.getItem()));
          } else {
            player.world.playSound(null, player.getPosition(), TFCSounds.CERAMIC_BREAK, SoundCategory.PLAYERS, 1.0f, 1.0f);
          }
          break;
        }
      }
    }
    return ForgeHooks.defaultRecipeGetRemainingItems(inv);
  }

  @Override
  @NotNull
  public NonNullList<Ingredient> getIngredients() {
    return input;
  }

  @Override
  public boolean isDynamic() {
    return true;
  }

  @Override
  @NotNull
  public String getGroup() {
    return group == null ? "" : group.toString();
  }

  @SuppressWarnings("unused")
  public static class Factory implements IRecipeFactory {

    @Override
    public IRecipe parse(final JsonContext context, final JsonObject json) {
      final NonNullList<Ingredient> ingredients = RecipeUtils.parseShapeless(context, json);
      final ItemStack result = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);
      final String group = JsonUtils.getString(json, "group", "");

      //Chance of getting the mold back
      float chance = 0;
      if (JsonUtils.hasField(json, "chance")) {
        chance = JsonUtils.getFloat(json, "chance");
      }
      return new UnmoldGlassRecipe(group.isEmpty() ? null : new ResourceLocation(group), ingredients, result, chance);
    }
  }
}
