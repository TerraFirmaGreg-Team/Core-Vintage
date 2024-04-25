package com.example.examplemod;

import su.terrafirmagreg.Tags;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


import net.dries007.tfc.api.recipes.barrel.BarrelRecipe;

import static su.terrafirmagreg.api.lib.Constants.MODID_AGEDDRINKS;

@Mod(modid = MODID_AGEDDRINKS, name = TFCAgedDrinks.NAME, version = Tags.VERSION)
public class TFCAgedDrinks {

    public static final String NAME = "TFC Aged Drinks";

    @EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {

        @SubscribeEvent
        public static void onRegisterBarrelRecipeEvent(RegistryEvent.Register<BarrelRecipe> event) {
            AgedRegistry.registerAgedDrinks(event);
        }
    }

}
