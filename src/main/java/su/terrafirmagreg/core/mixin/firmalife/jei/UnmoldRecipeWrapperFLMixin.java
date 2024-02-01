package su.terrafirmagreg.core.mixin.firmalife.jei;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import com.eerussianguy.firmalife.compat.jei.wrapper.UnmoldRecipeWrapperFL;
import com.eerussianguy.firmalife.registry.ItemsFL;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = UnmoldRecipeWrapperFL.class, remap = false)
public class UnmoldRecipeWrapperFLMixin implements IRecipeWrapper {

    @Shadow
    @Final
    @Mutable
    private ItemStack mold;
    @Shadow
    @Final
    @Mutable
    private ItemStack output;

    @Inject(method = "<init>", at = @At(value = "TAIL"), remap = false)
    public void UnmoldRecipeWrapperFL(Metal metal, String type, CallbackInfo ci) {
        this.mold = new ItemStack(ItemsFL.malletMold);
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
