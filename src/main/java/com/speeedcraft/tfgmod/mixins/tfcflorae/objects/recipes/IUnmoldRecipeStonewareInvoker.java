package com.speeedcraft.tfgmod.mixins.tfcflorae.objects.recipes;

import net.dries007.tfc.api.capability.IMoldHandler;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import tfcflorae.objects.recipes.UnmoldRecipeStonewareTFCF;

@Mixin(value = UnmoldRecipeStonewareTFCF.class, remap = false)
public interface IUnmoldRecipeStonewareInvoker {
	@Invoker
	ItemStack invokeGetOutputItem(IMoldHandler moldHandler);
}
