package su.terrafirmagreg.core.mixin.tfctech.jei;

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
import tfctech.compat.jei.wrappers.CastingRecipeWrapper;
import tfctech.objects.items.ceramics.ItemTechMold;
import tfctech.objects.items.metal.ItemTechMetal;

@Mixin(value = CastingRecipeWrapper.class, remap = false)
public abstract class CastingRecipeWrapperMixin extends net.dries007.tfc.compat.jei.wrappers.CastingRecipeWrapper {

	@Shadow
	@Final
	@Mutable
	private ItemStack mold;
	@Shadow
	@Final
	@Mutable
	private FluidStack input;

	public CastingRecipeWrapperMixin(Metal metal, Metal.ItemType type) {
		super(metal, type);
	}

	@Inject(method = "<init>", at = @At(value = "TAIL"), remap = false)
	public void CastingRecipeWrapper(Metal metal, ItemTechMetal.ItemType type, CallbackInfo ci) {
		this.input = new FluidStack(FluidsTFC.getFluidFromMetal(metal), 144);
		this.mold = new ItemStack(ItemTechMold.get(type));
		IFluidHandler cap = mold.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
		if (cap instanceof IMoldHandler) {
			cap.fill(this.input, true);
		}
	}
}
