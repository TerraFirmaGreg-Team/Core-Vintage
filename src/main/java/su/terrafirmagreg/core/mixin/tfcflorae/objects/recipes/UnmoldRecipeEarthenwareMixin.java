package su.terrafirmagreg.core.mixin.tfcflorae.objects.recipes;

import gregtech.api.unification.OreDictUnifier;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.capability.heat.IItemHeat;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.metal.ItemMetal;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import su.terrafirmagreg.core.util.TFGModUtils;
import tfcflorae.objects.items.ceramics.ItemEarthenwareMold;
import tfcflorae.objects.recipes.UnmoldRecipeEarthenwareTFCF;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.dries007.tfc.api.capability.heat.CapabilityItemHeat.ITEM_HEAT_CAPABILITY;
import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

@Mixin(value = UnmoldRecipeEarthenwareTFCF.class, remap = false)
public abstract class UnmoldRecipeEarthenwareMixin extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

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
	private final Metal.ItemType type;
	@Shadow
	@Final
	@Mutable
	private final float chance;

	private UnmoldRecipeEarthenwareMixin(@Nullable ResourceLocation group, NonNullList<Ingredient> input, @Nonnull Metal.ItemType type, float chance) {
		this.group = group;
		this.input = input;
		this.type = type;
		this.chance = chance;
	}

	/**
	 * @author SpeeeDCraft
	 * @reason 100mb in mold -> 144mb = Recipe working!
	 */
	@Overwrite
	@Nonnull
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack moldStack = null;
		for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
			ItemStack stack = inv.getStackInSlot(slot);
			if (!stack.isEmpty()) {
				if (stack.getItem() instanceof ItemEarthenwareMold tmp) {
					if (tmp.getType().equals(this.type) && moldStack == null) {
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
					return ((IUnmoldRecipeEarthenwareInvoker) this).invokeGetOutputItem(moldHandler);
				}
			}
		}
		return ItemStack.EMPTY;
	}

	@Inject(method = "getOutputItem", at = @At(value = "HEAD"), cancellable = true)
	private void onGetOutputItem(IMoldHandler moldHandler, CallbackInfoReturnable<ItemStack> cir) {
		Metal m = moldHandler.getMetal();
		if (m != null) {

			String oreDict = TFGModUtils.constructOredictFromTFCToGT(m, type);
			ItemStack outputTest = OreDictUnifier.get(oreDict);

			if (!outputTest.getItem().equals(Items.AIR)) {

				ItemStack output = OreDictUnifier.get(oreDict);

				IItemHeat heat = output.getCapability(ITEM_HEAT_CAPABILITY, null);
				if (heat != null) {
					heat.setTemperature(moldHandler.getTemperature());
				}
				cir.setReturnValue(output);
			} else {
				ItemStack output = new ItemStack(ItemMetal.get(m, type));

				IItemHeat heat = output.getCapability(ITEM_HEAT_CAPABILITY, null);
				if (heat != null) {
					heat.setTemperature(moldHandler.getTemperature());
				}
				cir.setReturnValue(output);
			}

		}
		cir.cancel();
	}
}
