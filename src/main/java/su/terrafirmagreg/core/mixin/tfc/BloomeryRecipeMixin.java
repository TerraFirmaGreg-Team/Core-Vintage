package su.terrafirmagreg.core.mixin.tfc;

import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.forge.IForgeable;
import net.dries007.tfc.api.capability.forge.IForgeableMeasurableMetal;
import net.dries007.tfc.api.recipes.BloomeryRecipe;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nonnull;

@Mixin(value = BloomeryRecipe.class, remap = false)
public class BloomeryRecipeMixin extends IForgeRegistryEntry.Impl<BloomeryRecipe> {

	@Shadow
	@Final
	@Mutable
	private final Metal metal;

	public BloomeryRecipeMixin(@Nonnull Metal metal) {
		this.metal = metal;
	}

	@Inject(method = "getOutput()Lnet/minecraft/item/ItemStack;", at = @At(value = "HEAD"), cancellable = true)
	public void getOutput(CallbackInfoReturnable<ItemStack> cir) {
		ItemStack bloom = new ItemStack(ItemsTFC.UNREFINED_BLOOM);
		IForgeable cap = bloom.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
		if (cap instanceof IForgeableMeasurableMetal capBloom) {
			capBloom.setMetalAmount(144);
			capBloom.setMetal(metal);
			capBloom.setTemperature(capBloom.getMeltTemp() - 1);
		}
		cir.setReturnValue(bloom);
	}
}
