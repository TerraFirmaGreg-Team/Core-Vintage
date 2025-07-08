package su.terrafirmagreg.modules.animal.plugin.top;

import su.terrafirmagreg.api.data.enums.Mods.ModIDs;
import su.terrafirmagreg.framework.manager.plugin.base.BasePlugin;
import su.terrafirmagreg.modules.animal.plugin.top.provider.AnimalProvider;

import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.ITheOneProbe;

public class PluginTheOneProbe extends BasePlugin {


  public PluginTheOneProbe() {
    super(Settings.of()
      .modRequired(ModIDs.THEONEPROBE)
    );
  }

  @Override
  public void onInit() {
    final ITheOneProbe oneProbe = TheOneProbe.theOneProbeImp;

    oneProbe.registerEntityProvider(new AnimalProvider());
  }
}
