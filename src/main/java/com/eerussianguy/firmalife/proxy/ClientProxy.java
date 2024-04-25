package com.eerussianguy.firmalife.proxy;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import lyeoj.tfcthings.init.TFCThingsEntities;
import lyeoj.tfcthings.renderer.TESRGrindstone;
import lyeoj.tfcthings.tileentity.TileEntityGrindstone;

import static su.terrafirmagreg.api.lib.Constants.MODID_FL;

@Mod.EventBusSubscriber(value = { Side.CLIENT }, modid = MODID_FL)
@SuppressWarnings("unused")
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);

        TFCThingsEntities.registerEntityModels();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGrindstone.class, new TESRGrindstone());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void postInit(FMLPostInitializationEvent e) {

    }
}
