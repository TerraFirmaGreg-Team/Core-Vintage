package su.terrafirmagreg.core.mixin.tfc;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.command.CommandGenTree;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TerraFirmaCraft.class, remap = false)
public class TerraFirmaCraftMixin {

	@Inject(method = "onServerStarting", at = @At(value = "HEAD"))
	public void onServerStarting(FMLServerStartingEvent event, CallbackInfo ci) {
		event.registerServerCommand(new CommandGenTree());
	}
}
