package lyeoj.tfcthings.recipes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.IForgeRegistryEntry;


import com.google.gson.JsonObject;
import lyeoj.tfcthings.init.TFCThingsItems;
import lyeoj.tfcthings.items.ItemTFCThingsMold;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.heat.IItemHeat;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.objects.recipes.RecipeUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import su.terrafirmagreg.api.lib.MathConstants;

public class TFCThingsUnmoldRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    private final NonNullList<Ingredient> input;
    private final ResourceLocation group;
    private final String type;
    private final float chance;

    private TFCThingsUnmoldRecipe(@Nullable ResourceLocation group, NonNullList<Ingredient> input, @NotNull String type, float chance) {
        this.group = group;
        this.input = input;
        this.type = type;
        this.chance = chance;
    }

    public boolean matches(@NotNull InventoryCrafting inv, @NotNull World world) {
        boolean foundMold = false;

        for (int slot = 0; slot < inv.getSizeInventory(); ++slot) {
            ItemStack stack = inv.getStackInSlot(slot);
            if (!stack.isEmpty()) {
                if (!(stack.getItem() instanceof ItemTFCThingsMold moldItem)) {
                    return false;
                }

                IFluidHandler cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, (EnumFacing) null);
                if (!(cap instanceof IMoldHandler moldHandler)) {
                    return false;
                }

                if (moldHandler.isMolten()) {
                    return false;
                }

                Metal metal = moldHandler.getMetal();
                if (metal == null || !moldItem.getToolName().equals(this.type) || foundMold) {
                    return false;
                }

                foundMold = true;
            }
        }

        return foundMold;
    }

    @NotNull
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack moldStack = null;

        for (int slot = 0; slot < inv.getSizeInventory(); ++slot) {
            ItemStack stack = inv.getStackInSlot(slot);
            if (!stack.isEmpty()) {
                if (!(stack.getItem() instanceof ItemTFCThingsMold tmp)) {
                    return ItemStack.EMPTY;
                }

                if (!tmp.getToolName().equals(this.type) || moldStack != null) {
                    return ItemStack.EMPTY;
                }

                moldStack = stack;
            }
        }

        if (moldStack != null) {
            IFluidHandler moldCap = moldStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, (EnumFacing) null);
            if (moldCap instanceof IMoldHandler moldHandler) {
                if (!moldHandler.isMolten() && moldHandler.getAmount() == 100) {
                    return this.getOutputItem(moldHandler);
                }
            }
        }

        return ItemStack.EMPTY;
    }

    public boolean canFit(int width, int height) {
        return true;
    }

    @NotNull
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @NotNull
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        for (int slot = 0; slot < inv.getSizeInventory(); ++slot) {
            ItemStack stack = inv.getStackInSlot(slot);
            if (!stack.isEmpty() && stack.getItem() instanceof ItemTFCThingsMold) {
                EntityPlayer player = ForgeHooks.getCraftingPlayer();
                if (!player.world.isRemote) {
                    stack = this.getMoldResult(stack);
                    if (!stack.isEmpty()) {
                        ItemHandlerHelper.giveItemToPlayer(player, stack);
                    } else {
                        player.world.playSound((EntityPlayer) null, player.getPosition(), TFCSounds.CERAMIC_BREAK, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    }
                }
            }
        }

        return ForgeHooks.defaultRecipeGetRemainingItems(inv);
    }

    @NotNull
    public NonNullList<Ingredient> getIngredients() {
        return this.input;
    }

    public boolean isDynamic() {
        return true;
    }

    @NotNull
    public String getGroup() {
        return this.group == null ? "" : this.group.toString();
    }

    public String getType() {
        return this.type;
    }

    public float getChance() {
        return this.chance;
    }

    public ItemStack getMoldResult(ItemStack moldIn) {
        return MathConstants.RNG.nextFloat() <= this.chance ? new ItemStack(moldIn.getItem()) : ItemStack.EMPTY;
    }

    public ItemStack getOutputItem(IMoldHandler moldHandler) {
        Metal m = moldHandler.getMetal();
        if (m != null) {
            ItemStack output = new ItemStack(TFCThingsItems.TOOLS_HEADS_BY_METAL.get(type).get(m));
            IItemHeat heat = (IItemHeat) output.getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, (EnumFacing) null);
            if (heat != null) {
                heat.setTemperature(moldHandler.getTemperature());
            }

            return output;
        } else {
            return ItemStack.EMPTY;
        }
    }

    public static class Factory implements IRecipeFactory {

        public Factory() {
        }

        public IRecipe parse(JsonContext context, JsonObject json) {
            NonNullList<Ingredient> ingredients = RecipeUtils.parseShapeless(context, json);
            String result = JsonUtils.getString(json, "result");
            String type = result.toLowerCase();
            String group = JsonUtils.getString(json, "group", "");
            float chance = 0.0F;
            if (JsonUtils.hasField(json, "chance")) {
                chance = JsonUtils.getFloat(json, "chance");
            }

            return new TFCThingsUnmoldRecipe(group.isEmpty() ? new ResourceLocation(result) : new ResourceLocation(group), ingredients, type, chance);
        }
    }
}
