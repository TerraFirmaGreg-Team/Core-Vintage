package net.dries007.firmalife;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import net.dries007.firmalife.compat.ModuleManager;
import net.dries007.firmalife.gui.FLGuiHandler;
import net.dries007.firmalife.network.PacketDrawBoundingBox;
import net.dries007.firmalife.network.PacketSpawnVanillaParticle;
import net.dries007.firmalife.proxy.CommonProxy;
import net.dries007.firmalife.util.HelpersFL;
import net.dries007.firmalife.util.OreDictsFL;
import org.apache.logging.log4j.Logger;

@Mod(modid = FirmaLife.MOD_ID, name = FirmaLife.MODNAME, version = FirmaLife.MODVERSION, dependencies = "required-after:tfc;after:dynamictreestfc")
public class FirmaLife {

  public static final String MOD_ID = "firmalife";
  public static final String MODNAME = "FirmaLife";
  public static final String MODVERSION = "0.5.1";
  @SidedProxy(clientSide = "net.dries007.firmalife.proxy.ClientProxy", serverSide = "net.dries007.firmalife.proxy.ServerProxy")
  public static CommonProxy proxy;
  public static Logger logger;
  @Mod.Instance(MOD_ID)
  private static FirmaLife INSTANCE;
  private SimpleNetworkWrapper network;

  public FirmaLife() {
    INSTANCE = this;
  }

  public static FirmaLife getInstance() {
    return INSTANCE;
  }

  public static SimpleNetworkWrapper getNetwork() {
    return INSTANCE.network;
  }

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    logger = event.getModLog();
    proxy.preInit(event);

    NetworkRegistry.INSTANCE.registerGuiHandler(this, new FLGuiHandler());
    network = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);
    int id = 0;
    // received client side
    network.registerMessage(new PacketSpawnVanillaParticle.Handler(), PacketSpawnVanillaParticle.class, ++id, Side.CLIENT);
    network.registerMessage(new PacketDrawBoundingBox.Handler(), PacketDrawBoundingBox.class, ++id, Side.CLIENT);

    HelpersFL.insertWhitelist();

    ModuleManager.initModules();
    ModuleManager.getModules().forEach(mod -> mod.preInit(event));
  }

  @Mod.EventHandler
  public void init(FMLInitializationEvent event) {
    proxy.init(event);
    ModuleManager.getModules().forEach(mod -> mod.init(event));


  }

  @Mod.EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    proxy.postInit(event);
    OreDictsFL.addStaticOres();
    ModuleManager.getModules().forEach(mod -> mod.postInit(event));
  }
}
