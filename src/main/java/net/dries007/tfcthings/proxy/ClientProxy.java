package net.dries007.tfcthings.proxy;

import su.terrafirmagreg.modules.device.object.tile.TileGrindstone;

import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import net.dries007.tfc.client.render.TESRGemDisplay;
import net.dries007.tfc.client.render.TESRGrindstone;
import net.dries007.tfc.objects.entity.projectile.EntityThrownHookJavelin;
import net.dries007.tfc.objects.te.TEGemDisplay;
import net.dries007.tfcthings.init.TFCThingsEntities;

public class ClientProxy extends CommonProxy {

  private final Minecraft MINECRAFT = Minecraft.getMinecraft();

  @Override
  public void preInit(FMLPreInitializationEvent event) {
    super.preInit(event);
    TFCThingsEntities.registerEntityModels();
  }

  @Override
  public void init(FMLInitializationEvent event) {
    super.init(event);
    ClientRegistry.bindTileEntitySpecialRenderer(TEGemDisplay.class, new TESRGemDisplay());
    ClientRegistry.bindTileEntitySpecialRenderer(TileGrindstone.class, new TESRGrindstone());
  }

  @Override
  public void postInit(FMLPostInitializationEvent event) {
    super.postInit(event);
  }

  @Override
  public void serverStarting(FMLServerStartingEvent event) {
    super.serverStarting(event);
  }

  @Override
  public void serverStopping(FMLServerStoppingEvent event) {
    super.serverStopping(event);
  }

  public IThreadListener getThreadListener(final MessageContext context) {
    if (context.side.isClient()) {
      return MINECRAFT;
    } else {
      return context.getServerHandler().player.server;
    }
  }

  public void syncJavelinGroundState(int javelinID, boolean inGround) {
    EntityThrownHookJavelin javelin = (EntityThrownHookJavelin) MINECRAFT.world.getEntityByID(javelinID);
    javelin.setInGroundSynced(inGround);
  }

}
