package se.gory_moon.horsepower.proxy;

import su.terrafirmagreg.modules.device.object.tile.TileChopperHorse;
import su.terrafirmagreg.modules.device.object.tile.TileChopperManual;
import su.terrafirmagreg.modules.device.object.tile.TilePressHorse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import se.gory_moon.horsepower.client.renderer.ClientHandler;
import se.gory_moon.horsepower.client.renderer.TESRChopperHorse;
import se.gory_moon.horsepower.client.renderer.TESRChoppingBlock;
import se.gory_moon.horsepower.client.renderer.TESRFiller;
import se.gory_moon.horsepower.client.renderer.TESRHPBase;
import se.gory_moon.horsepower.client.renderer.TESRPress;
import se.gory_moon.horsepower.tileentity.TileFiller;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

  @Override
  public void preInit() {
    super.preInit();
    ClientRegistry.bindTileEntitySpecialRenderer(TileChopperHorse.class, new TESRChopperHorse());
    ClientRegistry.bindTileEntitySpecialRenderer(TileFiller.class, new TESRFiller());
    ClientRegistry.bindTileEntitySpecialRenderer(TileChopperManual.class, new TESRChoppingBlock());
    ClientRegistry.bindTileEntitySpecialRenderer(TilePressHorse.class, new TESRPress());
  }

  @Override
  public void init() {
    MinecraftForge.EVENT_BUS.register(ClientHandler.class);
  }

  @Override
  public void loadComplete() {

    ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(resourceManager -> TESRHPBase.clearDestroyStageicons());

  }
}
