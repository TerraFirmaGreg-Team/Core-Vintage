/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.recipes;

import com.google.gson.JsonObject;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.core.unification.material.internal.MaterialRegistryManager;
import net.dries007.tfc.Constants;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.objects.items.ceramics.ItemMold;
import net.dries007.tfc.util.Helpers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

@SuppressWarnings("unused")
@ParametersAreNonnullByDefault
public class UnmoldRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe
{
    private final ItemStack inputMold;
    private final Material inputMaterial;
    private final float chanceToBreakMold;

    public UnmoldRecipe(ResourceLocation group, ItemStack inputMold, Material inputMaterial, float chanceToBreakMold) {
        super();

        this.inputMold = inputMold;
        this.inputMaterial = inputMaterial;

        this.chanceToBreakMold = chanceToBreakMold;
    }

    @Override
    public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World world) {
        for (var slotId = 0; slotId < inv.getSizeInventory(); slotId++) {
            var stack = inv.getStackInSlot(slotId);

            if (stack.isEmpty()) continue;
            if (stack.getItem() != inputMold.getItem()) continue;
            if (!(stack.getItem() instanceof ItemMold itemMold)) continue;

            var fluidHandler = stack.getCapability(FLUID_HANDLER_CAPABILITY, null);

            if (!(fluidHandler instanceof IMoldHandler moldHandler)) continue;
            if (Helpers.getOrePrefixMaterialAmount(itemMold.getOrePrefix()) != moldHandler.getAmount()) continue;
            if (moldHandler.isMolten()) continue;

            var material = moldHandler.getMaterial();

            if (material == inputMaterial) return true;
        }

        return false;
    }

    @Override
    @Nonnull
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        for (var slotId = 0; slotId < inv.getSizeInventory(); slotId++) {
            var stack = inv.getStackInSlot(slotId);

            if (stack.isEmpty()) continue;

            var moldItem = (ItemMold) stack.getItem();
            var capability = (IMoldHandler) stack.getCapability(FLUID_HANDLER_CAPABILITY, null);

            if (capability != null)
                return OreDictUnifier.get(moldItem.getOrePrefix(), capability.getMaterial());

        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    @Nonnull
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    @Nonnull
    public NonNullList<ItemStack> getRemainingItems(final InventoryCrafting inv) {
        // Return empty molds
        for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
            ItemStack stack = inv.getStackInSlot(slot);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof ItemMold) {
                    // No need to check for the mold, as it has already been checked earlier
                    EntityPlayer player = ForgeHooks.getCraftingPlayer();
                    if (player != null && !player.world.isRemote) {
                        stack = getMoldResult(stack);
                        if (!stack.isEmpty()) {
                            // This can't use the remaining items, because vanilla doesn't sync them on crafting, thus it gives a desync error
                            // To fix: ContainerWorkbench#onCraftMatrixChanged needs to call Container#detectAndSendChanges
                            ItemHandlerHelper.giveItemToPlayer(player, stack);
                        }
                        else {
                            player.world.playSound(null, player.getPosition(), TFCSounds.CERAMIC_BREAK, SoundCategory.PLAYERS, 1.0f, 1.0f);
                        }
                    }
                }
            }
        }
        return ForgeHooks.defaultRecipeGetRemainingItems(inv);
    }



    @Override
    public boolean isDynamic() {
        return true;
    }

    /**
     * Performs breaking check
     *
     * @param moldIn the mold to do a breaking check
     * @return ItemStack.EMPTY on break, the mold (empty) if pass
     */
    public ItemStack getMoldResult(ItemStack moldIn) {
        if (Constants.RNG.nextFloat() <= chanceToBreakMold) {
            return new ItemStack(moldIn.getItem());
        }
        else {
            return ItemStack.EMPTY;
        }
    }
/*
    public ItemStack getOutputItem(final IMoldHandler moldHandler) {
        var materialInMold = moldHandler.getMaterial();


        if (materialInMold != null) {
            if (inputMaterial == materialInMold) {
                var output = OreDictUnifier.get(outputOrePrefix, inputMaterial);
                var heat = output.getCapability(ITEM_HEAT_CAPABILITY, null);

                if (heat != null) {
                    heat.setTemperature(moldHandler.getTemperature());
                }

                return output;
            }
            return ItemStack.EMPTY;
        }
        return ItemStack.EMPTY;
    }*/

    @SuppressWarnings("unused")
    public static class Factory implements IRecipeFactory {
        @Override
        public IRecipe parse(final JsonContext context, final JsonObject json) {
            final var inputItemName = JsonUtils.getString(json, "inputItem");
            final var inputItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(inputItemName));
            if (inputItem == null) throw new RuntimeException(String.format("Item [%s] not found", inputItemName));

            final var materialName = JsonUtils.getString(json, "inputMaterial");
            final var inputMaterial = MaterialRegistryManager.getInstance().getMaterial(materialName);
            if (inputMaterial == null) throw new RuntimeException(String.format("Material [%s] not found", materialName));

            OrePrefix orePrefix;

            if (inputItem instanceof ItemMold itemMold) {
                orePrefix = itemMold.getOrePrefix();
            }
            else {
                throw new RuntimeException(String.format("ItemInput is not ItemMold [%s]", inputItemName));
            }

            final var inputMold = new ItemStack(inputItem);

            float chanceToBreakMold = 0;
            if (JsonUtils.hasField(json, "chance")) {
                chanceToBreakMold = JsonUtils.getFloat(json, "chance");
            }

            return new UnmoldRecipe(
                    new ResourceLocation(TerraFirmaCraft.MOD_ID, "unmold_" + orePrefix + "_" + materialName),
                    inputMold,
                    inputMaterial,
                    chanceToBreakMold
            );
        }
    }
}
