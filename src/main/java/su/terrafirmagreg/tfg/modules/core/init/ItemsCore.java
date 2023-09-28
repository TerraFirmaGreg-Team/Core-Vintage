package su.terrafirmagreg.tfg.modules.core.init;

import com.codetaylor.mc.athenaeum.registry.Registry;
import com.codetaylor.mc.athenaeum.util.ModelRegistrationHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.tfg.modules.core.common.items.ItemDebug;

public class ItemsCore {

    public static ItemDebug WAND;

    public static void onRegister(Registry registry) {

        //==== Items =================================================================================================//

        registry.registerItem(WAND = new ItemDebug(), ItemDebug.NAME);


    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {

        registry.registerClientModelRegistrationStrategy(() -> {
            ModelRegistrationHelper.registerItemModels(
                    WAND
            );

        });
    }
}
