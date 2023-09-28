package su.terrafirmagreg.modules.core.init;

import com.codetaylor.mc.athenaeum.registry.Registry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlocksCore {


    public static void onRegister(Registry registry) {

        //==== Blocks ================================================================================================//


        //==== TileEntity ============================================================================================//

    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {

        registry.registerClientModelRegistrationStrategy(() -> {

        });

        //==== TESRs =================================================================================================//

    }
}
