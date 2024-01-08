package su.terrafirmagreg.core.mixin.firmalife;

import com.eerussianguy.firmalife.items.ItemMetalMalletMold;
import com.eerussianguy.firmalife.recipe.UnmoldRecipeFL;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.spongepowered.asm.mixin.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mixin(value = UnmoldRecipeFL.class, remap = false)
public abstract class UnmoldRecipesFLMixin extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

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

	private UnmoldRecipesFLMixin(@Nullable ResourceLocation group, NonNullList<Ingredient> input, @Nonnull String type, float chance) {
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

		for (int slot = 0; slot < inv.getSizeInventory(); ++slot) {
			ItemStack stack = inv.getStackInSlot(slot);
			if (!stack.isEmpty()) {
				if (!(stack.getItem() instanceof ItemMetalMalletMold tmp)) {
					return ItemStack.EMPTY;
				}

				if (!tmp.getToolName().equals(this.type) || moldStack != null) {
					return ItemStack.EMPTY;
				}

				moldStack = stack;
			}
		}

		if (moldStack != null) {
			IFluidHandler moldCap = moldStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
			if (moldCap instanceof IMoldHandler moldHandler) {
				if (!moldHandler.isMolten() && moldHandler.getAmount() == 144) {
					return ((IUnmoldRecipeFLInvoker) this).invokeGetOutputItem(moldHandler);
				}
			}
		}

		return ItemStack.EMPTY;
	}
}
