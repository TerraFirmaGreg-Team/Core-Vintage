package net.dries007.tfc.module.core;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.registry.Registry;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.module.core.init.BlocksCore;
import net.dries007.tfc.module.core.init.ItemsCore;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.dries007.tfc.Tags.MOD_ID;
import static net.dries007.tfc.Tags.MOD_NAME;

public class ModuleCore extends ModuleBase {

    public static final CreativeTabs MISC_TAB = new CreativeTabsTFC.TFCCreativeTab("misc", "tfc:wand");
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME + "." + ModuleCore.class.getSimpleName());

    public ModuleCore() {
        super(0, MOD_ID);

        this.setRegistry(new Registry(MOD_ID, MISC_TAB));
        this.enableAutoRegistry();

        //PACKET_SERVICE = this.enableNetwork();

        MinecraftForge.EVENT_BUS.register(this);
    }

//    @SubscribeEvent
//    public static void onNewRegistryEvent(RegistryEvent.NewRegistry event) {
//        RegistryCore.createRegistries(event);
//
//    }

    @Override
    public void onRegister(Registry registry) {

        BlocksCore.onRegister(registry);
        ItemsCore.onRegister(registry);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onClientRegister(Registry registry) {
        BlocksCore.onClientRegister(registry);
        ItemsCore.onClientRegister(registry);
    }
}
