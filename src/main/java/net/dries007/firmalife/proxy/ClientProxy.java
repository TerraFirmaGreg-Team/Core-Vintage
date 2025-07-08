package net.dries007.firmalife.proxy;

import su.terrafirmagreg.api.data.enums.Mods.ModIDs;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(value = {Side.CLIENT}, modid = ModIDs.FL)
@SuppressWarnings("unused")
public class ClientProxy extends CommonProxy {

  @Override
  public void preInit(FMLPreInitializationEvent e) {
    super.preInit(e);
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void postInit(FMLPostInitializationEvent e) {

  }
}
