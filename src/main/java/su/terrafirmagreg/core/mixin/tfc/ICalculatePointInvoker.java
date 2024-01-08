package su.terrafirmagreg.core.mixin.tfc;

import net.dries007.tfc.objects.blocks.BlockPlacedHide;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = BlockPlacedHide.class, remap = false)
public interface ICalculatePointInvoker {
	@Invoker
	static Vec3d invokeCalculatePoint(Vec3d rayVector, Vec3d rayPoint) {
		throw new AssertionError();
	}
}
