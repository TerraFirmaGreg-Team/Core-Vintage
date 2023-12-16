package com.speeedcraft.tfgmod.mixins.tfcflorae.firmalife.jei.wrapper;

import com.eerussianguy.firmalife.registry.ItemsFL;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
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
import tfcflorae.compat.firmalife.jei.wrappers.UnmoldRecipeWrapperStonewareFL;
import tfcflorae.objects.items.ItemsTFCF;

@Mixin(value = UnmoldRecipeWrapperStonewareFL.class, remap = false)
public class UnmoldRecipeWrapperStonewareFLMixin {

	@Shadow
	@Final
	@Mutable
	private ItemStack mold;
	@Shadow
	@Final
	@Mutable
	private ItemStack output;

	@Inject(method = "<init>", at = @At(value = "TAIL"), remap = false)
	public void UnmoldRecipeWrapperEarthenwareFL(Metal metal, String type, CallbackInfo ci) {
		this.mold = new ItemStack(ItemsTFCF.malletMoldStoneware);
		IFluidHandler cap = this.mold.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
		if (cap instanceof IMoldHandler) {
			cap.fill(new FluidStack(FluidsTFC.getFluidFromMetal(metal), 144), true);
		}

		this.output = new ItemStack(ItemsFL.getMetalMalletHead(metal));
	}

	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(VanillaTypes.ITEM, this.mold);
		ingredients.setOutput(VanillaTypes.ITEM, this.output);
	}
}
