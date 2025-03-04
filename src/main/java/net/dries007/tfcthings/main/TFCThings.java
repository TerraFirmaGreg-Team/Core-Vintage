package net.dries007.tfcthings.main;

import su.terrafirmagreg.api.data.enums.Mods;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import net.dries007.tfc.network.MessageHookJavelinUpdate;
import net.dries007.tfcthings.proxy.CommonProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Mods.ModIDs.TFCTHINGS, name = TFCThings.NAME, version = TFCThings.VERSION, dependencies = TFCThings.DEPENDENCIES)
public class TFCThings {

  public static final String NAME = "TerraFirmaThings";
  public static final String VERSION = "1.3.5";
  public static final String CLIENT_PROXY = "net.dries007.tfcthings.proxy.ClientProxy";
  public static final String COMMON_PROXY = "net.dries007.tfcthings.proxy.CommonProxy";
  public static final String DEPENDENCIES = "required-after:" + Mods.ModIDs.TFC;
  public static final Logger LOGGER = LogManager.getLogger(Mods.ModIDs.TFCTHINGS);
  @SidedProxy(clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
  public static CommonProxy proxy;
  @Mod.Instance
  public static TFCThings instance;
  public static SimpleNetworkWrapper network;

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    LOGGER.info("TFC Things: Starting Pre-Init...");
    network = NetworkRegistry.INSTANCE.newSimpleChannel(Mods.ModIDs.TFCTHINGS);
    network.registerMessage(MessageHookJavelinUpdate.Handler.class, MessageHookJavelinUpdate.class, 1, Side.CLIENT);
    proxy.preInit(event);
  }

  @Mod.EventHandler
  public void init(FMLInitializationEvent event) {
    LOGGER.info("TFC Things: Starting Init...");
    proxy.init(event);
  }

  @Mod.EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    LOGGER.info("TFC Things: Starting Post-Init...");
    proxy.postInit(event);
  }

}
