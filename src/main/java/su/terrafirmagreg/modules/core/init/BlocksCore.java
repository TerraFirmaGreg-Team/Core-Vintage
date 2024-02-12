package su.terrafirmagreg.modules.core.init;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import su.terrafirmagreg.api.registry.Registry;
import su.terrafirmagreg.api.util.ModelRegistrationHelper;
import su.terrafirmagreg.modules.core.objects.blocks.BlockDebug;

public final class BlocksCore {

    public static BlockDebug DEBUG;

    public static void onRegister(Registry registry) {

        //==== Other =================================================================================================//

        registry.registerAuto(DEBUG = new BlockDebug());
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {
//        registry.registerClientModel(() -> {
//            ModelRegistrationHelper.registerBlockItemModels(
//                    DEBUG
//
//
//            );
//        });
    }

}
