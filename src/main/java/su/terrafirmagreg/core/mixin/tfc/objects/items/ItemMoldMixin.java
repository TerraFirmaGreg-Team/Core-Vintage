package su.terrafirmagreg.core.mixin.tfc.objects.items;

import net.minecraftforge.fluids.FluidTank;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets = "net.dries007.tfc.objects.items.ceramics.ItemMold$FilledMoldCapability", remap = false)
public class ItemMoldMixin {
	@Shadow
	@Final
	private final FluidTank tank = new FluidTank(144);
}
