package com.speeedcraft.tfgmod.mixins.tfctech;

import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.capability.heat.IItemHeat;
import net.dries007.tfc.api.types.Metal;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.spongepowered.asm.mixin.*;
import tfctech.objects.items.ceramics.ItemTechMold;
import tfctech.objects.items.metal.ItemTechMetal;
import tfctech.objects.recipes.UnmoldRecipe;

import javax.annotation.Nonnull;

import static net.dries007.tfc.api.capability.heat.CapabilityItemHeat.ITEM_HEAT_CAPABILITY;
import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

@Mixin(value = UnmoldRecipe.class, remap = false)
@SuppressWarnings("unused")
public abstract class UnmoldRecipeMixin extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

	@Shadow
	@Final
	@Mutable
	private final NonNullList<Ingredient> input;
	@Shadow
	@Final
	@Mutable
	private final ResourceLocation group;
	@Shadow
	@Final
	@Mutable
	private final ItemTechMetal.ItemType type;
	@Shadow
	@Final
	@Mutable
	private final float chance;

	private UnmoldRecipeMixin(ResourceLocation group, NonNullList<Ingredient> input, @Nonnull ItemTechMetal.ItemType type, float chance) {
		this.group = group;
		this.input = input;
		this.type = type;
		this.chance = chance;
	}

	/**
	 * @author SpeeeDCraft
	 * @reason
	 */
	@Overwrite
	@Nonnull
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack moldStack = null;
		for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
			ItemStack stack = inv.getStackInSlot(slot);
			if (!stack.isEmpty()) {
				if (stack.getItem() instanceof ItemTechMold tmp) {
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
			if (moldCap instanceof IMoldHandler moldHandler) {
				if (!moldHandler.isMolten() && moldHandler.getAmount() == 144) {
					return ((IUnmoldRecipeTFCTechInvoker) this).invokeGetOutputItem(moldHandler);
				}
			}
		}
		return ItemStack.EMPTY;
	}

	/**
	 * @author SpeeeDCraft
	 * @reason
	 */
	@Overwrite
	public ItemStack getOutputItem(final IMoldHandler moldHandler) {
		Metal m = moldHandler.getMetal();
		if (m != null) {
			Item item = ItemTechMetal.get(m, type);
			if (item != null) {
				if (type == ItemTechMetal.ItemType.RACKWHEEL_PIECE) {
					switch (m.toString()) {
						case "platinum":
						case "pig_iron":
						case "sterling_silver":
						case "tin":
						case "silver":
						case "rose_gold":
						case "lead":
						case "gold":
						case "copper":
						case "brass":
						case "bismuth_bronze": {
							return ItemStack.EMPTY;
						}
						default: {
							ItemStack output = new ItemStack(item);
							IItemHeat heat = output.getCapability(ITEM_HEAT_CAPABILITY, null);
							if (heat != null) {
								heat.setTemperature(moldHandler.getTemperature());
							}
							return output;
						}
					}
				} else {
					ItemStack output = new ItemStack(item);
					IItemHeat heat = output.getCapability(ITEM_HEAT_CAPABILITY, null);
					if (heat != null) {
						heat.setTemperature(moldHandler.getTemperature());
					}
					return output;
				}
			}
		}
		return ItemStack.EMPTY;
	}
}
