package se.gory_moon.horsepower;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.gory_moon.horsepower.blocks.ModBlocks;
import se.gory_moon.horsepower.items.ModItems;
import se.gory_moon.horsepower.network.PacketHandler;
import se.gory_moon.horsepower.proxy.CommonProxy;
import se.gory_moon.horsepower.recipes.HPRecipes;
import se.gory_moon.horsepower.tweaker.DummyTweakPluginImpl;
import se.gory_moon.horsepower.tweaker.ITweakerPlugin;
import se.gory_moon.horsepower.tweaker.TweakerPluginImpl;
import se.gory_moon.horsepower.util.Utils;
import su.terrafirmagreg.Tags;

import static se.gory_moon.horsepower.lib.Reference.*;
import static su.terrafirmagreg.api.lib.Constants.MODID_HORSEPOWER;

@Mod(modid = MODID_HORSEPOWER, version = Tags.VERSION, name = NAME, dependencies = "required-after:tfc;after:crafttweaker;after:jei;after:waila;after:theoneprobe;")
@EventBusSubscriber
public class HorsePowerMod {
	@Instance(MODID_HORSEPOWER)
	public static HorsePowerMod instance;

	@SidedProxy(clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
	public static CommonProxy proxy;

	public static HorsePowerCreativeTab creativeTab = new HorsePowerCreativeTab();
	public static ITweakerPlugin tweakerPlugin = new DummyTweakPluginImpl();
	public static Logger logger = LogManager.getLogger("HorsePower");

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit();
		PacketHandler.init();

		FMLInterModComms.sendMessage("waila", "register", WAILA_PROVIDER);

		ModBlocks.registerTileEntities();

		if (Loader.isModLoaded("crafttweaker"))
			tweakerPlugin = new TweakerPluginImpl();

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
