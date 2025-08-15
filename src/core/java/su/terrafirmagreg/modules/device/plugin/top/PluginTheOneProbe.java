package su.terrafirmagreg.modules.device.plugin.top;

import su.terrafirmagreg.api.data.enums.Mods.ModIDs;
import su.terrafirmagreg.framework.manager.plugin.base.BasePlugin;
import su.terrafirmagreg.framework.module.spi.StateEvent;
import su.terrafirmagreg.modules.device.plugin.top.provider.ProviderBlastFurnace;
import su.terrafirmagreg.modules.device.plugin.top.provider.ProviderBloom;
import su.terrafirmagreg.modules.device.plugin.top.provider.ProviderBloomery;
import su.terrafirmagreg.modules.device.plugin.top.provider.ProviderCrucible;
import su.terrafirmagreg.modules.device.plugin.top.provider.ProviderFridge;
import su.terrafirmagreg.modules.device.plugin.top.provider.ProviderLatexExtractor;
import su.terrafirmagreg.modules.device.plugin.top.provider.ProviderLeafMat;
import su.terrafirmagreg.modules.device.plugin.top.provider.ProviderLogPile;
import su.terrafirmagreg.modules.device.plugin.top.provider.ProviderOven;
import su.terrafirmagreg.modules.device.plugin.top.provider.ProviderPitKiln;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.ITheOneProbe;

public class PluginTheOneProbe extends BasePlugin {


  public PluginTheOneProbe() {
    super(PluginSettings.of()
      .modRequired(ModIDs.THEONEPROBE)
    );
  }

  @SubscribeEvent
  public static void onPostInit(StateEvent.PostInitialization event) {
    ITheOneProbe oneProbe = TheOneProbe.theOneProbeImp;

    oneProbe.registerProvider(new ProviderFridge());
    oneProbe.registerProvider(new ProviderLatexExtractor());
    oneProbe.registerProvider(new ProviderPitKiln());
    oneProbe.registerProvider(new ProviderCrucible());
//    oneProbe.registerProvider(new ProviderQuernManual());
//    oneProbe.registerProvider(new ProviderQuernHorse());
    oneProbe.registerProvider(new ProviderBlastFurnace());
    oneProbe.registerProvider(new ProviderBloom());
    oneProbe.registerProvider(new ProviderBloomery());
    oneProbe.registerProvider(new ProviderLogPile());
    oneProbe.registerBlockDisplayOverride(new ProviderLogPile());
    oneProbe.registerProvider(new ProviderOven());
    oneProbe.registerProvider(new ProviderLeafMat());

  }
}
