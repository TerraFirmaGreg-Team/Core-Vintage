package su.terrafirmagreg.core.mixin.tfcflorae.firmalife.ceramics;

import net.minecraftforge.fluids.FluidTank;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets = "tfcflorae.compat.firmalife.ceramics.ItemKaoliniteMalletMoldFL$FilledMoldCapability", remap = false)
public class ItemKaoliniteMalletMoldFLMixin {

    @Shadow
    @Final
    private final FluidTank tank = new FluidTank(144);
}
