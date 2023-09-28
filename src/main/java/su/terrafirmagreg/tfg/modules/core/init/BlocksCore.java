package su.terrafirmagreg.tfg.modules.core.init;

import com.codetaylor.mc.athenaeum.registry.Registry;
import com.codetaylor.mc.athenaeum.util.ModelRegistrationHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.tfg.modules.core.common.blocks.BlockDebug;

public class BlocksCore {

    public static BlockDebug DEBUG;


    public static void onRegister(Registry registry) {

        //==== Blocks ================================================================================================//

        registry.registerBlock(DEBUG = new BlockDebug(), DEBUG.getItemBlock(), BlockDebug.NAME);


        //==== TileEntity ============================================================================================//

    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {

        registry.registerClientModelRegistrationStrategy(() -> {

            ModelRegistrationHelper.registerBlockItemModels(
                    DEBUG
            );

        });

        //==== TESRs =================================================================================================//

    }
}
