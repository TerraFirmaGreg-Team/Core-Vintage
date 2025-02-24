package net.dries007.tfctech;

import su.terrafirmagreg.api.data.enums.Mods;
import su.terrafirmagreg.modules.core.init.ItemsCore;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;

import net.dries007.tfc.network.PacketFridgeUpdate;
import net.dries007.tfc.network.PacketTileEntityUpdate;
import net.dries007.tfctech.client.TechGuiHandler;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("WeakerAccess")
@Mod(modid = Mods.ModIDs.TFCTECH, name = TFCTech.NAME, version = TFCTech.VERSION, dependencies = TFCTech.DEPENDENCIES)
public class TFCTech {

  public static final String NAME = "TFCTech Unofficial";
  public static final String VERSION = "@VERSION@";
  public static final String DEPENDENCIES = "required-after:tfc@[1.0.0,);after:ic2;after:gregtech";
  @SuppressWarnings("FieldMayBeFinal")
  @Mod.Instance
  private static TFCTech instance = null;
  private static Logger logger;
  private SimpleNetworkWrapper network;

  public static SimpleNetworkWrapper getNetwork() {
    return instance.network;
  }

  public static Logger getLog() {
    return logger;
  }

  public static TFCTech getInstance() {
    return instance;
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
    // Register dust ash ore dictionary
    // Unfortunately, this has to be done after TFC registered it's items, which is only safe after preInit
    OreDictionary.registerOre("dustAsh", ItemsCore.WOOD_ASH.get());
    FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "tfctech.compat.waila.TOPPlugin");
  }

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    logger = event.getModLog();
    NetworkRegistry.INSTANCE.registerGuiHandler(this, new TechGuiHandler());
    network = NetworkRegistry.INSTANCE.newSimpleChannel(Mods.ModIDs.TFCTECH);
    int id = 0;
    network.registerMessage(new PacketTileEntityUpdate.Handler(), PacketTileEntityUpdate.class, ++id, Side.CLIENT);
    network.registerMessage(new PacketFridgeUpdate.Handler(), PacketFridgeUpdate.class, ++id, Side.CLIENT);
  }
}
