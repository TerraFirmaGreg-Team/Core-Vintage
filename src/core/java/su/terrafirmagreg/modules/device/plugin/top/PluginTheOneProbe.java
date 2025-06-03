package su.terrafirmagreg.modules.device.plugin.top;

import su.terrafirmagreg.api.data.enums.Mods.ModIDs;
import su.terrafirmagreg.framework.manager.plugin.spi.PluginBase;
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

import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.ITheOneProbe;

public class PluginTheOneProbe extends PluginBase {


  public PluginTheOneProbe() {
    super(ModIDs.THEONEPROBE);
  }

  @Override
  public void onInit(FMLInitializationEvent event) {
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
