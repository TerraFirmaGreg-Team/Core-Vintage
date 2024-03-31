package su.terrafirmagreg.modules.device.data;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.device.objects.blocks.BlockAlloyCalculator;


public final class BlocksDevice {

	public static BlockAlloyCalculator ALLOY_CALCULATOR;

	public static void onRegister(RegistryManager registry) {
		//==== Other =================================================================================================//

		ALLOY_CALCULATOR = registry.registerBlock(new BlockAlloyCalculator());
	}

}
