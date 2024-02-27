package su.terrafirmagreg.modules.core.data;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.core.objects.blocks.BlockDebug;

public final class BlocksCore {

	public static BlockDebug DEBUG;

	public static void onRegister(RegistryManager registry) {

		//==== Other =================================================================================================//

		registry.registerAuto(DEBUG = new BlockDebug());
	}

	@SideOnly(Side.CLIENT)
	public static void onClientRegister(RegistryManager registry) {
//        registry.registerClientModel(() -> {
//            ModelRegistrationHelper.registerBlockItemModels(
//                    DEBUG
//
//
//            );
//        });
	}

}
