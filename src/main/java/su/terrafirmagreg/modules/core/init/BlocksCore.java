package su.terrafirmagreg.modules.core.init;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import su.terrafirmagreg.api.registry.Registry;
import su.terrafirmagreg.api.util.IHasModel;
import su.terrafirmagreg.api.util.ModelRegistrationHelper;
import su.terrafirmagreg.modules.core.objects.blocks.BlockDebug;
import su.terrafirmagreg.modules.soil.StorageSoil;

public final class BlocksCore {

    public static BlockDebug DEBUG;

    public static void onRegister(Registry registry) {

        //==== Other =================================================================================================//

        registry.registerBlock(DEBUG = new BlockDebug(), DEBUG.getItemBlock(), "debug");
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {
        registry.registerClientModelRegistration(() -> {
            ModelRegistrationHelper.registerBlockItemModels(
                    DEBUG
            );

        });
    }

}
