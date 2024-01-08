package su.terrafirmagreg.core.mixin.tfc.objects.blocks;

import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.objects.blocks.wood.BlockLoom;
import net.dries007.tfc.util.OreDictionaryHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BlockLoom.class, remap = false)
public class BlockLoomTFCMixin {

	/**
	 * @author SpeeeDCraft
	 * @reason Add loom oredict and loom{woodType} oredict for looms
	 */
	@Inject(method = "<init>", at = @At(value = "TAIL"), remap = false)
	public void onConstructor(Tree wood, CallbackInfo ci) {
		OreDictionaryHelper.register(BlockLoom.get(wood), "loom");
		OreDictionaryHelper.register(BlockLoom.get(wood), "loom", wood);
	}
}
