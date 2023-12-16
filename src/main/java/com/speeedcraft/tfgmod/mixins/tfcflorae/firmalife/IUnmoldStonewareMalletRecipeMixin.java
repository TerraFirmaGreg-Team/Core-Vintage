package com.speeedcraft.tfgmod.mixins.tfcflorae.firmalife;

import net.dries007.tfc.api.capability.IMoldHandler;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import tfcflorae.compat.firmalife.recipes.UnmoldStonewareMalletRecipe;

@Mixin(value = UnmoldStonewareMalletRecipe.class, remap = false)
public interface IUnmoldStonewareMalletRecipeMixin {
	@Invoker
	ItemStack invokeGetOutputItem(IMoldHandler moldHandler);
}
