package net.dries007.tfc.module.core.init;

import com.codetaylor.mc.athenaeum.registry.Registry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemsCore {

    public static void onRegister(Registry registry) {


    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {

        registry.registerClientModelRegistrationStrategy(() -> {

        });
    }
}
