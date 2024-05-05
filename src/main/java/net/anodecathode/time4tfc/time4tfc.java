package net.anodecathode.time4tfc;

import su.terrafirmagreg.Tags;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


import net.anodecathode.time4tfc.data.SessionData;
import net.anodecathode.time4tfc.network.NetworkEventHandler;
import net.anodecathode.time4tfc.network.PacketHandler;
import net.anodecathode.time4tfc.world.WorldProviderTooMuchTime;

import static su.terrafirmagreg.api.data.Constants.MODID_TIME4TFC;

/**
 * @author AnodeCathode, dmillerw
 */
@Mod(modid = MODID_TIME4TFC, name = "Time4TFC", version = Tags.VERSION, dependencies = "required-after:tfc")
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
