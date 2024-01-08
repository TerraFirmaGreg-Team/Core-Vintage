package su.terrafirmagreg.core.mixin.tfcflorae.firmalife.jei.wrapper;

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
import tfcflorae.compat.firmalife.jei.wrappers.CastingRecipeWrapperEarthenwareFL;
import tfcflorae.objects.items.ItemsTFCF;

@Mixin(value = CastingRecipeWrapperEarthenwareFL.class, remap = false)
public abstract class CastingRecipeWrapperEarthenwareFLMixin implements IRecipeWrapper {

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
		this.mold = new ItemStack(ItemsTFCF.malletMoldEarthenware);
		IFluidHandler cap = this.mold.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
		if (cap instanceof IMoldHandler) {
			cap.fill(this.input, true);
		}
	}
}
