package net.dries007.horsepower;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.dries007.horsepower.blocks.ModBlocks;
import net.dries007.horsepower.items.ModItems;
import net.dries007.horsepower.lib.Reference;
import net.dries007.horsepower.network.PacketHandler;
import net.dries007.horsepower.proxy.CommonProxy;
import net.dries007.horsepower.recipes.HPRecipes;
import net.dries007.horsepower.tweaker.DummyTweakPluginImpl;
import net.dries007.horsepower.tweaker.ITweakerPlugin;
import net.dries007.horsepower.tweaker.TweakerPluginImpl;
import net.dries007.horsepower.util.Utils;

import static net.dries007.horsepower.lib.Reference.MODID;

@Mod(modid = MODID, version = Reference.VERSION, name = Reference.NAME, acceptedMinecraftVersions = "[1.12]", dependencies = "required-after:tfc;after:crafttweaker;after:jei;after:waila;after:theoneprobe;")
@EventBusSubscriber(modid = MODID)
public class HorsePowerMod {

  @Instance(MODID)
  public static HorsePowerMod instance;

  @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
  public static CommonProxy proxy;

  public static ITweakerPlugin tweakerPlugin = new DummyTweakPluginImpl();
  public static Logger logger = LogManager.getLogger("HorsePower");

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    proxy.preInit();
    PacketHandler.init();

    FMLInterModComms.sendMessage("waila", "register", Reference.WAILA_PROVIDER);

    ModBlocks.registerTileEntities();

    if (Loader.isModLoaded("crafttweaker")) {tweakerPlugin = new TweakerPluginImpl();}

    tweakerPlugin.register();
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
    proxy.init();
    ModItems.registerRecipes();
  }

  @EventHandler
  public void loadComplete(FMLPostInitializationEvent event) {
    tweakerPlugin.run();

    HPEventHandler.reloadConfig();
    proxy.loadComplete();
  }

  @EventHandler
  public void serverLoad(FMLServerAboutToStartEvent event) {
    HPRecipes.instance().reloadRecipes();
    Utils.sendSavedErrors();
  }
}
