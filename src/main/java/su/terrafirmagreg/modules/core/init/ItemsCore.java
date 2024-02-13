package su.terrafirmagreg.modules.core.init;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import su.terrafirmagreg.api.registry.Registry;
import su.terrafirmagreg.modules.core.objects.items.ItemDebug;

public final class ItemsCore {

    public static ItemDebug WAND;

    public static void onRegister(Registry registry) {

        //==== Other =================================================================================================//

        registry.registerAuto(WAND = new ItemDebug());
    }


    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {

//        registry.registerClientModel(() -> {
//            ModelRegistrationHelper.registerItemModels(
//                    WAND
//
//
//            );
//        });

    }
}
