package su.terrafirmagreg.modules.animal.plugin.top;

import su.terrafirmagreg.api.data.enums.Mods.ModIDs;
import su.terrafirmagreg.framework.manager.plugin.spi.PluginBase;
import su.terrafirmagreg.modules.animal.plugin.top.provider.AnimalProvider;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.ITheOneProbe;

public class PluginTheOneProbe extends PluginBase {

  public PluginTheOneProbe() {
    super(ModIDs.THEONEPROBE);
  }

  @Override
  public void onInit(FMLInitializationEvent event) {
    final ITheOneProbe oneProbe = TheOneProbe.theOneProbeImp;

    oneProbe.registerEntityProvider(new AnimalProvider());
  }
}
