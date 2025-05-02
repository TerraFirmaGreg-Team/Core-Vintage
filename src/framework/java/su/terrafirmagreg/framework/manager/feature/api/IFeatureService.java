package su.terrafirmagreg.framework.manager.feature.api;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IFeatureService {

  // ===== FML Lifecycle

  void onPreInit(FMLPreInitializationEvent event);

  void onInit(FMLInitializationEvent event);

  void onPostInit(FMLPostInitializationEvent event);

  // ===== FML Lifecycle: Client

  @SideOnly(Side.CLIENT)
  void onClientPreInit(FMLPreInitializationEvent event);

  @SideOnly(Side.CLIENT)
  void onClientInit(FMLInitializationEvent event);

  @SideOnly(Side.CLIENT)
  void onClientPostInit(FMLPostInitializationEvent event);
}
