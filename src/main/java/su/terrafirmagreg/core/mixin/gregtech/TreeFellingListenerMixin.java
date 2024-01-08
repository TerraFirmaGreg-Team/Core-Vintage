package su.terrafirmagreg.core.mixin.gregtech;

import gregtech.api.items.toolitem.TreeFellingListener;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TreeFellingListener.class, remap = false)
public class TreeFellingListenerMixin {

	@Inject(method = "start", at = @At(value = "HEAD"), remap = false, cancellable = true)
	private static void start(IBlockState state, ItemStack tool, BlockPos start, EntityPlayerMP player, CallbackInfo ci) {
		ci.cancel();
	}

	@Inject(method = "onWorldTick", at = @At(value = "HEAD"), remap = false, cancellable = true)
	public void onWorldTick(TickEvent.WorldTickEvent event, CallbackInfo ci) {
		ci.cancel();
	}
}
