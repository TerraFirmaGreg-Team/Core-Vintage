package pieman.caffeineaddon;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pieman.caffeineaddon.init.RegistryHandler;
import pieman.caffeineaddon.proxy.CommonProxy;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, dependencies = "required-after:tfc")
public class CaffeineAddon {
    public static final Logger LOG = LogManager.getLogger(Reference.MOD_ID);
    @Instance
    public static CaffeineAddon instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    @EventHandler
    public static void PreInit(FMLPreInitializationEvent event) {
        proxy.preinit();
        RegistryHandler.preInitRegistries();
    }

    @EventHandler
    public static void Init(FMLInitializationEvent event) {
        proxy.init();
        RegistryHandler.initRegistries();
    }

    @EventHandler
    public static void PostInit(FMLPostInitializationEvent event) {
        proxy.postinit();
        RegistryHandler.postInitRegistries();
    }

}
