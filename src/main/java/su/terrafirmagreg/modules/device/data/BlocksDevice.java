package su.terrafirmagreg.modules.device.data;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.device.objects.blocks.BlockAlloyCalculator;
import su.terrafirmagreg.modules.device.objects.blocks.BlockBearTrap;
import su.terrafirmagreg.modules.device.objects.blocks.BlockSnare;


public final class BlocksDevice {

	public static BlockAlloyCalculator ALLOY_CALCULATOR;
	public static BlockBearTrap BEAR_TRAP;
	public static BlockSnare SNARE;

	public static void onRegister(RegistryManager registry) {
		//==== Other =================================================================================================//

		ALLOY_CALCULATOR = registry.registerBlock(new BlockAlloyCalculator());
		BEAR_TRAP = registry.registerBlock(new BlockBearTrap());
		SNARE = registry.registerBlock(new BlockSnare());
	}

}
