package com.eerussianguy.firmalife.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;


import com.eerussianguy.firmalife.world.WorldGeneratorFL;
import lyeoj.tfcthings.capability.CapabilitySharpness;
import lyeoj.tfcthings.capability.TFCThingsCapabilityHandler;
import lyeoj.tfcthings.init.TFCThingsEntities;
import lyeoj.tfcthings.main.ConfigTFCThings;
import net.dries007.tfc.util.calendar.CalendarTFC;

import static su.terrafirmagreg.api.lib.Constants.MODID_FL;

@Mod.EventBusSubscriber(modid = MODID_FL)
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        GameRegistry.registerWorldGenerator(new WorldGeneratorFL(), 0);

        MinecraftForge.EVENT_BUS.register(new TFCThingsCapabilityHandler());
        CapabilitySharpness.setup();
        TFCThingsEntities.registerEntities();
        CalendarTFC.BIRTHDAYS.put("OCTOBER4", "Lyeoj's Birthday");
        for (int i = 0; i < ConfigTFCThings.Misc.BIRTHDAYS.dayList.length; i++) {
            ConfigTFCThings.addBirthday(ConfigTFCThings.Misc.BIRTHDAYS.dayList[i]);
        }
    }

    public void init(FMLInitializationEvent e) {

    }

    public void postInit(FMLPostInitializationEvent e) {
    }
}
