package su.terrafirmagreg.core.mixin.tfc.objects.blocks;

import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.blocks.stone.BlockButtonStoneTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.BlockButtonStone;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BlockButtonStoneTFC.class, remap = false)
public class BlockButtonStoneTFCMixin extends BlockButtonStone {

	/**
	 * @author SpeeeDCraft
	 * @reason Add buttonStone oredict and buttonStone{rockType} oredict for stoneButtons
	 */
	@Inject(method = "<init>", at = @At(value = "TAIL"), remap = false)
	public void onConstructor(Rock rock, CallbackInfo ci) {
		OreDictionaryHelper.register(BlockButtonStoneTFC.get(rock), "button", "stone");
		OreDictionaryHelper.register(BlockButtonStoneTFC.get(rock), "button", "stone", rock.toString());
	}
}
