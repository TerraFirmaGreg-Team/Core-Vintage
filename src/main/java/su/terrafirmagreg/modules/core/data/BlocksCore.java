package su.terrafirmagreg.modules.core.data;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.core.objects.blocks.BlockDebug;
import su.terrafirmagreg.modules.core.objects.blocks.BlockPuddle;

public final class BlocksCore {

	public static BlockDebug DEBUG;
	public static BlockPuddle PUDDLE; //TODO: отключить itemBlock

	public static void onRegister(RegistryManager registry) {

		//==== Other =================================================================================================//

		registry.registerAuto(DEBUG = new BlockDebug());
		registry.registerAuto(PUDDLE = new BlockPuddle());
	}

	@SideOnly(Side.CLIENT)
	public static void onClientRegister(RegistryManager registry) {
	}

}
