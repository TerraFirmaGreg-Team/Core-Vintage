package lyeoj.tfcthings.main;

import su.terrafirmagreg.Tags;
import su.terrafirmagreg.data.Constants;
import su.terrafirmagreg.data.lib.LoggingHelper;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import lyeoj.tfcthings.network.MessageHookJavelinUpdate;
import lyeoj.tfcthings.proxy.CommonProxy;

import static su.terrafirmagreg.data.Constants.MODID_TFCTHINGS;

@Mod(modid = MODID_TFCTHINGS, name = TFCThings.NAME, version = Tags.MOD_VERSION, dependencies = TFCThings.DEPENDENCIES)
public class TFCThings {

  public static final String NAME = "TerraFirmaThings";
  public static final String CLIENT_PROXY = "lyeoj.tfcthings.proxy.ClientProxy";
  public static final String COMMON_PROXY = "lyeoj.tfcthings.proxy.CommonProxy";
  public static final String DEPENDENCIES = "required-after:" + Constants.MODID_TFC;
  public static final LoggingHelper LOGGER = LoggingHelper.of(MODID_TFCTHINGS);
  @SidedProxy(clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
  public static CommonProxy proxy;
  @Mod.Instance(MODID_TFCTHINGS)
  public static TFCThings instance;
  public static SimpleNetworkWrapper network;

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    LOGGER.info("TFC Things: Starting Pre-Init...");
    network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID_TFCTHINGS);
    network.registerMessage(MessageHookJavelinUpdate.Handler.class, MessageHookJavelinUpdate.class, 1, Side.CLIENT);
  }

}
