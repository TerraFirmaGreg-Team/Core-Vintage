package com.speeedcraft.tfgmod.mixins.tfc;

import gregtech.common.items.ToolItems;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.objects.te.TEAnvilTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = TEAnvilTFC.class, remap = false)
public class TEAnvilTFCMixin {

	@Overwrite
	public boolean isItemValid(int slot, ItemStack stack) {
		switch (slot) {
			case 0:
			case 1:
				return stack.hasCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
			case 2:
				return stack.getItem() == ToolItems.HARD_HAMMER;
			case 3:
				return OreDictionaryHelper.doesStackMatchOre(stack, "dustFlux");
			default:
				return false;
		}
	}
}
