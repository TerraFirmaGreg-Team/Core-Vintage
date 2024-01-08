package su.terrafirmagreg.core.mixin.tfcflorae.firmalife.recipes;

import su.terrafirmagreg.core.mixin.tfcflorae.firmalife.IUnmoldStonewareMalletRecipeMixin;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.spongepowered.asm.mixin.*;
import tfcflorae.compat.firmalife.ceramics.ItemStonewareMalletMoldFL;
import tfcflorae.compat.firmalife.recipes.UnmoldStonewareMalletRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

@Mixin(value = UnmoldStonewareMalletRecipe.class, remap = false)
public abstract class UnmoldStonewareMalletRecipeMixin extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

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
	private final String type;
	@Shadow
	@Final
	@Mutable
	private final float chance;

	private UnmoldStonewareMalletRecipeMixin(@Nullable ResourceLocation group, NonNullList<Ingredient> input, @Nonnull String type, float chance) {
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
				if (stack.getItem() instanceof ItemStonewareMalletMoldFL tmp) {
					if (tmp.getToolName().equals(this.type) && moldStack == null) {
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
					return ((IUnmoldStonewareMalletRecipeMixin) this).invokeGetOutputItem(moldHandler);
				}
			}
		}
		return ItemStack.EMPTY;
	}

}
