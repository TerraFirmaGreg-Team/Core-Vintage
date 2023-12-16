package com.speeedcraft.tfgmod.mixins.firmalife;

import com.eerussianguy.firmalife.recipe.UnmoldRecipeFL;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = UnmoldRecipeFL.class, remap = false)
public interface IUnmoldRecipeFLInvoker {
	@Invoker
	ItemStack invokeGetOutputItem(IMoldHandler moldHandler);
}
