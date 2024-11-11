package net.anodecathode.time4tfc;

import su.terrafirmagreg.Tags;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import net.anodecathode.time4tfc.network.NetworkEventHandler;
import net.anodecathode.time4tfc.network.PacketHandler;
import net.anodecathode.time4tfc.world.WorldProviderTooMuchTime;

import static su.terrafirmagreg.api.data.Reference.MODID_TIME4TFC;

/**
 * @author AnodeCathode, dmillerw
 */
@Mod(modid = MODID_TIME4TFC, name = "Time4TFC", version = Tags.MOD_VERSION, dependencies = "required-after:tfc")
public class time4tfc {

  public static boolean modEnabled = true;

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event) {

    PacketHandler.initialize();
    NetworkEventHandler.register();

  }

  @Mod.EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    WorldProviderTooMuchTime.overrideDefault();
  }
}
