package com.speeedcraft.tfgmod.mixins.tfc.jei.wrappers;

import mezz.jei.api.recipe.IRecipeWrapper;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.compat.jei.wrappers.CastingRecipeWrapper;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.items.ceramics.ItemMold;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CastingRecipeWrapper.class, remap = false)
public abstract class CastingRecipeWrapperMixin implements IRecipeWrapper {

	@Shadow
	@Final
	@Mutable
	private ItemStack mold;
	@Shadow
	@Final
	@Mutable
	private FluidStack input;

	@Inject(method = "<init>", at = @At(value = "TAIL"), remap = false)
	public void CastingRecipeWrapper(Metal metal, Metal.ItemType type, CallbackInfo ci) {
		input = new FluidStack(FluidsTFC.getFluidFromMetal(metal), 144);
		mold = new ItemStack(ItemMold.get(type));
		IFluidHandler cap = mold.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
		if (cap instanceof IMoldHandler) {
			cap.fill(input, true);
		}
	}
}
