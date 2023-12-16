package com.speeedcraft.tfgmod.mixins.tfcflorae.jei.wrappers;

import com.speeedcraft.tfgmod.util.TFGModUtils;
import gregtech.api.unification.OreDictUnifier;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.items.metal.ItemMetal;
import net.minecraft.init.Items;
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
import tfcflorae.compat.jei.wrappers.UnmoldRecipeWrapperKaoliniteTFCF;
import tfcflorae.objects.items.ceramics.ItemKaoliniteMold;

@Mixin(value = UnmoldRecipeWrapperKaoliniteTFCF.class, remap = false)
public class UnmoldRecipeKaoliniteJEIMixin implements IRecipeWrapper {
	@Shadow
	@Final
	@Mutable
	private ItemStack mold;
	@Shadow
	@Final
	@Mutable
	private ItemStack output;

	@Inject(method = "<init>", at = @At(value = "TAIL"), remap = false)
	public void UnmoldRecipeJEI(Metal metal, Metal.ItemType type, CallbackInfo ci) {
		this.mold = new ItemStack(ItemKaoliniteMold.get(type));
		IFluidHandler cap = this.mold.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
		if (cap instanceof IMoldHandler) {
			cap.fill(new FluidStack(FluidsTFC.getFluidFromMetal(metal), 144), true);
		}

		String oreDict = TFGModUtils.constructOredictFromTFCToGT(metal, type);
		ItemStack outputTest = OreDictUnifier.get(oreDict);

		if (!outputTest.getItem().equals(Items.AIR)) {
			this.output = OreDictUnifier.get(oreDict);
		} else {
			this.output = new ItemStack(ItemMetal.get(metal, type));
		}
	}

	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(VanillaTypes.ITEM, this.mold);
		ingredients.setOutput(VanillaTypes.ITEM, this.output);
	}
}
