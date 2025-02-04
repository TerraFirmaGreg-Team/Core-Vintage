package net.dries007.waterflasks;

/*
    Copyright (c) 2019 Gaelmare

    This file is part of Waterflasks.

    Waterflasks is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    WaterFlasks is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with WaterFlasks.  If not, see <https://www.gnu.org/licenses/>.
*/

import su.terrafirmagreg.api.data.enums.Mods;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import net.dries007.tfc.util.Helpers;
import net.dries007.waterflasks.item.ModItems;
import net.dries007.waterflasks.proxy.CommonProxy;

@Mod(
  modid = Waterflasks.MOD_ID,
  name = Waterflasks.MOD_NAME,
  version = Waterflasks.VERSION,
  dependencies = Waterflasks.DEPENDENCIES
)
public class Waterflasks {

  public static final String MOD_ID = "waterflasks";
  public static final String MOD_NAME = "WaterFlasks";
  public static final String VERSION = "1.9";
  public static final String DEPENDENCIES = "required-after:" + Mods.Names.TFC +
                                            "@[" + "1.0.0.127" + ",)";
  @GameRegistry.ObjectHolder("waterflasks:item.flaskbreak")
  public static final SoundEvent FLASK_BREAK = Helpers.getNull();
  /**
   * Many thanks to Shadowfacts' 1.12.2 modding tutorial. Fingerprints from it remain...
   */

  @Mod.Instance(MOD_ID)
  public static Waterflasks INSTANCE;
  @SidedProxy(serverSide = "net.dries007.waterflasks.proxy.CommonProxy",
              clientSide = "net.dries007.waterflasks.proxy.ClientProxy")
  public static CommonProxy proxy;

  /**
   * This is the first initialization event. Register tile entities here. The registry events below will have fired prior to entry to this method.
   */
  @Mod.EventHandler
  public void preinit(FMLPreInitializationEvent event) {
    System.out.println(MOD_NAME + " is loading");
  }


  @Mod.EventBusSubscriber(modid = MOD_ID)
  public static class ObjectRegistryHandler {

    /**
     * Listen for the register event for creating custom items
     */
    @SubscribeEvent
    public static void addItems(RegistryEvent.Register<Item> event) {
      ModItems.register(event.getRegistry());
    }

    /**
     * Listen for the register event for models
     */
    @SubscribeEvent
    public static void registerItems(ModelRegistryEvent event) {
      ModItems.registerModels();
    }


    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
      ResourceLocation soundID = new ResourceLocation(MOD_ID, "item.flaskbreak");
      event.getRegistry().register((new SoundEvent(soundID)).setRegistryName(soundID));
    }

  }
}
