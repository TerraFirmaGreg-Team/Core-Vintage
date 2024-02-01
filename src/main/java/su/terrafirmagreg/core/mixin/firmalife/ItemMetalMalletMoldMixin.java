package su.terrafirmagreg.core.mixin.firmalife;

import net.minecraftforge.fluids.FluidTank;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets = "com.eerussianguy.firmalife.items.ItemMetalMalletMold$FilledMoldCapability", remap = false)
public class ItemMetalMalletMoldMixin {

    @Shadow
    @Final
    private final FluidTank tank = new FluidTank(144);
}
