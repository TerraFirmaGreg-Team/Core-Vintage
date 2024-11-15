package net.dries007.buuz135.hotornot.object.recipe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.IForgeRegistryEntry;

import net.dries007.buuz135.hotornot.object.item.ItemMetalTongsHead;
import net.dries007.buuz135.hotornot.object.item.ItemMetalTongsJawMold;
import com.google.gson.JsonObject;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.objects.items.ceramics.ItemMold;
import net.dries007.tfc.objects.recipes.RecipeUtils;

import static su.terrafirmagreg.api.util.MathUtils.RNG;

@SuppressWarnings("unused")
public class UnMoldJawPiece extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

  private final NonNullList<Ingredient> inputIngredients;
  private final String recipeGroup;
  /**
   * The change to return the mold
   */
  private final float chance;

  private UnMoldJawPiece(final String recipeGroup, final NonNullList<Ingredient> inputIngredients, final float chance) {
    this.recipeGroup = recipeGroup;
    this.inputIngredients = inputIngredients;
    this.chance = chance;
  }

  @Override
  public boolean matches(final InventoryCrafting inventoryCrafting, final World world) {
    boolean foundMold = false;
    for (int slotIndex = 0; slotIndex < inventoryCrafting.getSizeInventory(); ++slotIndex) {
      final ItemStack stack = inventoryCrafting.getStackInSlot(slotIndex);
      if (!stack.isEmpty()) {
        // Not our mold item
        if (!(stack.getItem() instanceof ItemMetalTongsJawMold)) {
          return false;
        }

        final IMoldHandler moldHandler;
        {
          final IFluidHandler fluidHandler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);

          if (!(fluidHandler instanceof IMoldHandler)) {
            return false;
          }

          moldHandler = (IMoldHandler) fluidHandler;
        }

        // Can't be molten
        if (moldHandler.isMolten()) {
          return false;
        }

        final Metal metal = moldHandler.getMetal();

        if (metal == null || foundMold) {
          return false;
        }

        foundMold = true;
      }
    }
    return foundMold;
  }

  @Override
  public ItemStack getCraftingResult(final InventoryCrafting inventoryCrafting) {
    for (int slotIndex = 0; slotIndex < inventoryCrafting.getSizeInventory(); slotIndex++) {
      final ItemStack inputStack = inventoryCrafting.getStackInSlot(slotIndex);
      if (inputStack.isEmpty()) {
        continue;
      }

      if (!(inputStack.getItem() instanceof ItemMetalTongsJawMold)) {
        return ItemStack.EMPTY;
      }

      // Mold has to have a Fluid Handler Capability
      if (!inputStack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
        return ItemStack.EMPTY;
      }

      final IMoldHandler moldHandler;
      {
        final IFluidHandler fluidHandler = inputStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);

        // Needs to be a mold handler
        if (!(fluidHandler instanceof IMoldHandler)) {
          return ItemStack.EMPTY;
        }

        moldHandler = (IMoldHandler) fluidHandler;
      }

      // Still molten
      if (moldHandler.isMolten()) {
        return ItemStack.EMPTY;
      }

      // Not enough fluid
      if (moldHandler.getAmount() != 100) {
        return ItemStack.EMPTY;
      }

      return getOutputItem(moldHandler);
    }
    return ItemStack.EMPTY;
  }

  @Override
  public boolean canFit(final int width, final int height) {
    return true;
  }

  @Override
  public ItemStack getRecipeOutput() {
    return ItemStack.EMPTY;
  }

  @Override
  public NonNullList<ItemStack> getRemainingItems(final InventoryCrafting inv) {
    // Return empty molds
    for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
      ItemStack stack = inv.getStackInSlot(slot);
      if (!stack.isEmpty()) {
        if (stack.getItem() instanceof ItemMold) {
          // No need to check for the mold, as it has already been checked earlier
          final EntityPlayer player = ForgeHooks.getCraftingPlayer();
          if (player != null && !player.world.isRemote) {
            stack = getMoldResult(stack);
            if (!stack.isEmpty()) {
              // This can't use the remaining items, because vanilla doesn't sync them on crafting, thus it gives a desync error
              // To fix: ContainerWorkbench#onCraftMatrixChanged needs to call Container#detectAndSendChanges
              ItemHandlerHelper.giveItemToPlayer(player, stack);
            } else {
              player.world.playSound(null, player.getPosition(), TFCSounds.CERAMIC_BREAK, SoundCategory.PLAYERS, 1.0f, 1.0f);
            }
          }
        }
      }
    }
    return ForgeHooks.defaultRecipeGetRemainingItems(inv);
  }

  @Override
  public NonNullList<Ingredient> getIngredients() {
    return inputIngredients;
  }

  @Override
  public boolean isDynamic() {
    return true;
  }

  @Override
  public String getGroup() {
    return recipeGroup;
  }

  /**
   * Performs breaking check
   *
   * @param moldStack the mold to do a breaking check
   * @return ItemStack.EMPTY on break, the mold (empty) if pass
   */
  public ItemStack getMoldResult(final ItemStack moldStack) {
    if (RNG.nextFloat() <= chance) {
      return new ItemStack(moldStack.getItem());
    }

    return ItemStack.EMPTY;
  }

  public ItemStack getOutputItem(final IMoldHandler moldHandler) {
    final Metal metal = moldHandler.getMetal();
    if (metal != null) {
      return new ItemStack(ItemMetalTongsHead.get(metal));
    }
    return ItemStack.EMPTY;
  }

  public float getChance() {
    return chance;
  }

  @SuppressWarnings("unused")
  public static class Factory implements IRecipeFactory {

    @Override
    public IRecipe parse(final JsonContext context, final JsonObject json) {
      final NonNullList<Ingredient> ingredients = RecipeUtils.parseShapeless(context, json);
      final String group = JsonUtils.getString(json, "group", "");

      //Chance of getting the mold back
      final float chance = JsonUtils.getFloat(json, "chance", 0);

      return new UnMoldJawPiece(group, ingredients, chance);
    }
  }
}
