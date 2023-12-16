package com.speeedcraft.tfgmod.mixins.tfctech;

import net.dries007.tfc.api.capability.IMoldHandler;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import tfctech.objects.recipes.UnmoldRecipe;

@Mixin(value = UnmoldRecipe.class, remap = false)
public interface IUnmoldRecipeTFCTechInvoker {
	@Invoker
	ItemStack invokeGetOutputItem(final IMoldHandler moldHandler);
}
