package su.terrafirmagreg.modules.core.data;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.core.objects.blocks.BlockDebug;
import su.terrafirmagreg.modules.core.objects.blocks.BlockPuddle;
import su.terrafirmagreg.modules.core.objects.blocks.BlockThatch;

public final class BlocksCore {

	public static BlockDebug DEBUG;
	public static BlockPuddle PUDDLE; //TODO: отключить регистрацию itemBlock
	public static BlockThatch THATCH;

	public static void onRegister(RegistryManager registry) {

		//==== Other =================================================================================================//

		registry.registerAuto(DEBUG = new BlockDebug());
		registry.registerAuto(PUDDLE = new BlockPuddle());
		registry.registerAuto(THATCH = new BlockThatch());
	}

}
