package net.dries007.tfcfarming;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import net.dries007.tfcfarming.firmalife.TEHangingPlanterN;
import net.dries007.tfcfarming.firmalife.TEPlanterN;
import net.dries007.tfcfarming.firmalife.TEStemCropN;
import net.dries007.tfcfarming.network.PacketHandler;

//@Mod(modid = TFCFarming.modId, name = TFCFarming.name, version = TFCFarming.version, dependencies = "required-after:tfc;after:tfcflorae;after:firmalife")
public class TFCFarming {

  public static final String modId = "tfcfarming";
  public static final String name = "TFC Farming";
  public static final String version = "1.2.0";

  public static TFCFarming INSTANCE;
  @SidedProxy(modId = TFCFarming.modId, clientSide = "net.dries007.tfcfarming.ClientProxy", serverSide = "net.dries007.tfcfarming.CommonProxy")
  public static CommonProxy proxy;
  public FarmingWorldStorage worldStorage;

  public TFCFarming() {
    INSTANCE = this;
  }

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    Config.load(event.getModConfigurationDirectory());
  }

  @Mod.EventHandler
  public void init(FMLInitializationEvent event) {
    MinecraftForge.EVENT_BUS.register(proxy);
    PacketHandler.registerPackets();
    GameRegistry.registerTileEntity(TECropBaseN.class, new ResourceLocation(modId, TECropBaseN.class.getSimpleName()));
    GameRegistry.registerTileEntity(TEPlanterN.class, new ResourceLocation(modId, TEPlanterN.class.getSimpleName()));
    GameRegistry.registerTileEntity(TEHangingPlanterN.class, new ResourceLocation(modId, TEHangingPlanterN.class.getSimpleName()));
    GameRegistry.registerTileEntity(TEStemCropN.class, new ResourceLocation(modId, TEStemCropN.class.getSimpleName()));
    proxy.init();
  }

  @Mod.EventHandler
  public void serverStaring(FMLServerStartingEvent event) {
    worldStorage = FarmingWorldStorage.get(event.getServer().getWorld(0));
  }

}
