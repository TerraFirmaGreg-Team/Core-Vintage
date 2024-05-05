package se.gory_moon.horsepower;

import su.terrafirmagreg.Tags;
import su.terrafirmagreg.api.lib.LoggingHelper;

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


import se.gory_moon.horsepower.blocks.ModBlocks;
import se.gory_moon.horsepower.items.ModItems;
import se.gory_moon.horsepower.network.PacketHandler;
import se.gory_moon.horsepower.proxy.CommonProxy;
import se.gory_moon.horsepower.recipes.HPRecipes;
import se.gory_moon.horsepower.util.Utils;

import static se.gory_moon.horsepower.lib.Reference.*;
import static su.terrafirmagreg.api.data.Constants.MODID_HORSEPOWER;

@Mod(modid = MODID_HORSEPOWER,
     version = Tags.VERSION,
     name = NAME,
     dependencies = "required-after:tfc;after:crafttweaker;after:jei;after:waila;after:theoneprobe;")
@EventBusSubscriber
public class HorsePowerMod {

    @Instance(MODID_HORSEPOWER)
    public static HorsePowerMod instance;

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
    public static CommonProxy proxy;

    public static LoggingHelper logger = new LoggingHelper(MODID_HORSEPOWER);

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
        ModItems.registerRecipes();
    }

    @EventHandler
    public void loadComplete(FMLPostInitializationEvent event) {

        HPEventHandler.reloadConfig();
        proxy.loadComplete();
    }

    @EventHandler
    public void serverLoad(FMLServerAboutToStartEvent event) {
        HPRecipes.instance().reloadRecipes();
        Utils.sendSavedErrors();
    }
}
