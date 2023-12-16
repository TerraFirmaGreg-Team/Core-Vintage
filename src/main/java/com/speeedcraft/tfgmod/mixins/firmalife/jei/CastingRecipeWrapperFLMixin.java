package com.speeedcraft.tfgmod.mixins.firmalife.jei;

import com.eerussianguy.firmalife.compat.jei.wrapper.CastingRecipeWrapperFL;
import com.eerussianguy.firmalife.registry.ItemsFL;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.fluids.FluidsTFC;
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

@Mixin(value = CastingRecipeWrapperFL.class, remap = false)
public abstract class CastingRecipeWrapperFLMixin implements IRecipeWrapper {

	@Shadow
	@Final
	@Mutable
	private ItemStack mold;
	@Shadow
	@Final
	@Mutable
	private FluidStack input;

	@Inject(method = "<init>", at = @At(value = "TAIL"), remap = false)
	public void CastingRecipeWrapperFL(Metal metal, String type, CallbackInfo ci) {
		this.input = new FluidStack(FluidsTFC.getFluidFromMetal(metal), 144);
		this.mold = new ItemStack(ItemsFL.malletMold);
		IFluidHandler cap = this.mold.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
		if (cap instanceof IMoldHandler) {
			cap.fill(this.input, true);
		}
	}
}
