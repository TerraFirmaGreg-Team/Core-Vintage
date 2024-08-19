package tfctech.objects.recipes;

import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.api.lib.MathConstants;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.IForgeRegistryEntry;


import com.google.gson.JsonObject;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.objects.items.ceramics.ItemTechMold;
import net.dries007.tfc.objects.items.metal.ItemTechMetal;
import net.dries007.tfc.objects.recipes.RecipeUtils;

import org.jetbrains.annotations.NotNull;

import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

/**
 * Since TFC has Metal.ItemType we can't reuse {@link net.dries007.tfc.objects.recipes.UnmoldRecipe} directly
 */
@SuppressWarnings("unused")
public class UnmoldRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    private final NonNullList<Ingredient> input;
    private final ResourceLocation group;
    private final ItemTechMetal.ItemType type;
    /* This is return chance, not break chance */
    private final float chance;

    public UnmoldRecipe(ResourceLocation group, NonNullList<Ingredient> input, @NotNull ItemTechMetal.ItemType type, float chance) {
        this.group = group;
        this.input = input;
        this.type = type;
        this.chance = chance;
    }

    @Override
    public boolean matches(@NotNull InventoryCrafting inv, @NotNull World world) {
        boolean foundMold = false;
        for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
            ItemStack stack = inv.getStackInSlot(slot);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof ItemTechMold moldItem) {
                    IFluidHandler cap = stack.getCapability(FLUID_HANDLER_CAPABILITY, null);

                    if (cap instanceof IMoldHandler moldHandler) {
                        if (!moldHandler.isMolten()) {
                            Metal metal = moldHandler.getMetal();
                            if (metal != null && moldItem.type.equals(this.type) && !foundMold) {
                                foundMold = true;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return foundMold;
    }

    @Override
    @NotNull
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack moldStack = null;
        for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
            ItemStack stack = inv.getStackInSlot(slot);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof ItemTechMold) {
                    ItemTechMold tmp = ((ItemTechMold) stack.getItem());
                    if (tmp.type.equals(this.type) && moldStack == null) {
                        moldStack = stack;
                    } else {
                        return ItemStack.EMPTY;
                    }
                } else {
                    return ItemStack.EMPTY;
                }
            }
        }
        if (moldStack != null) {
            IFluidHandler moldCap = moldStack.getCapability(FLUID_HANDLER_CAPABILITY, null);
            if (moldCap instanceof IMoldHandler) {
                IMoldHandler moldHandler = (IMoldHandler) moldCap;
                if (!moldHandler.isMolten() && moldHandler.getAmount() == 100) {
                    return getOutputItem(moldHandler);
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
    public ItemStack getRecipeOutput() {return ItemStack.EMPTY;}

    @Override
    @NotNull
    public NonNullList<ItemStack> getRemainingItems(final InventoryCrafting inv) {
        // Return empty molds
        for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
            ItemStack stack = inv.getStackInSlot(slot);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof ItemTechMold) {
                    // No need to check for the mold, as it has already been checked earlier
                    EntityPlayer player = ForgeHooks.getCraftingPlayer();
                    if (!player.world.isRemote) {
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

    public ItemTechMetal.ItemType getType() {
        return type;
    }

    public float getChance() {
        return chance;
    }

    /**
     * Performs breaking check
     *
     * @param moldIn the mold to do a breaking check
     * @return ItemStack.EMPTY on break, the mold (empty) if pass
     */
    public ItemStack getMoldResult(ItemStack moldIn) {
        if (MathConstants.RNG.nextFloat() <= chance) {
            return new ItemStack(moldIn.getItem());
        } else {
            return ItemStack.EMPTY;
        }
    }

    public ItemStack getOutputItem(final IMoldHandler moldHandler) {
        Metal m = moldHandler.getMetal();
        if (m != null) {
            Item item = ItemTechMetal.get(m, type);
            if (item != null) {
                ItemStack output = new ItemStack(item);
                var cap = CapabilityHeat.get(output);

                if (cap != null) {
                    cap.setTemperature(moldHandler.getTemperature());
                }
                return output;
            }
        }
        return ItemStack.EMPTY;
    }

    @SuppressWarnings("unused")
    public static class Factory implements IRecipeFactory {

        @Override
        public IRecipe parse(final JsonContext context, final JsonObject json) {
            final NonNullList<Ingredient> ingredients = RecipeUtils.parseShapeless(context, json);
            final String result = JsonUtils.getString(json, "result");
            final ItemTechMetal.ItemType type = ItemTechMetal.ItemType.valueOf(result.toUpperCase());
            final String group = JsonUtils.getString(json, "group", "");

            //Chance of getting the mold back
            float chance = 0;
            if (JsonUtils.hasField(json, "chance")) {
                chance = JsonUtils.getFloat(json, "chance");
            }

            return new UnmoldRecipe(group.isEmpty() ? new ResourceLocation(result) : new ResourceLocation(group), ingredients, type, chance);
        }
    }
}
