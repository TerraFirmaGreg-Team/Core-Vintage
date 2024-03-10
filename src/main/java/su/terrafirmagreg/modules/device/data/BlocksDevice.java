package su.terrafirmagreg.modules.device.data;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.device.objects.blocks.BlockAlloyCalculator;


public class BlocksDevice {

	public static BlockAlloyCalculator ALLOY_CALCULATOR;

	public static void onRegister(RegistryManager registry) {
		//==== Other =================================================================================================//

		registry.registerAuto(ALLOY_CALCULATOR = new BlockAlloyCalculator());
	}


	@SideOnly(Side.CLIENT)
	public static void onClientRegister(RegistryManager registry) {

	}
}
