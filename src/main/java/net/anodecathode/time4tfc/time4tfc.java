package net.anodecathode.time4tfc;


import net.anodecathode.time4tfc.data.SessionData;
import net.anodecathode.time4tfc.network.NetworkEventHandler;
import net.anodecathode.time4tfc.network.PacketHandler;
import net.anodecathode.time4tfc.world.WorldProviderTooMuchTime;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * @author AnodeCathode, dmillerw
 */
@Mod(modid = "time4tfc", name = "Time4TFC", version = "@VERSION@", dependencies = "required-after:tfc")
public class time4tfc {

    public static Configuration configuration;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configuration = new Configuration(event.getSuggestedConfigurationFile());
        configuration.load();

        SessionData.loadFromConfiguration(configuration);

        PacketHandler.initialize();
        NetworkEventHandler.register();

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        WorldProviderTooMuchTime.overrideDefault();
    }
}
