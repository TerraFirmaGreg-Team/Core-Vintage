package net.dries007.horsepower;

import su.terrafirmagreg.Tags;
import su.terrafirmagreg.api.helper.LoggingHelper;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import net.dries007.horsepower.blocks.ModBlocks;
import net.dries007.horsepower.network.PacketHandler;
import net.dries007.horsepower.proxy.CommonProxy;

import static net.dries007.horsepower.lib.Reference.CLIENT_PROXY;
import static net.dries007.horsepower.lib.Reference.COMMON_PROXY;
import static net.dries007.horsepower.lib.Reference.NAME;
import static net.dries007.horsepower.lib.Reference.WAILA_PROVIDER;
import static su.terrafirmagreg.api.data.Reference.MODID_HORSEPOWER;

@Mod(modid = MODID_HORSEPOWER,
     version = Tags.MOD_VERSION,
     name = NAME,
     dependencies = "required-after:tfc;after:crafttweaker;after:jei;after:waila;after:theoneprobe;")
@EventBusSubscriber
public class HorsePowerMod {

  @Instance(MODID_HORSEPOWER)
  public static HorsePowerMod instance;

  @SidedProxy(clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
  public static CommonProxy proxy;

  public static LoggingHelper logger = LoggingHelper.of(MODID_HORSEPOWER);

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    proxy.preInit();
    PacketHandler.init();

    FMLInterModComms.sendMessage("waila", "register", WAILA_PROVIDER);

    ModBlocks.registerTileEntities();

  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
    proxy.init();
  }

  @EventHandler
  public void loadComplete(FMLPostInitializationEvent event) {

    proxy.loadComplete();
  }

}
