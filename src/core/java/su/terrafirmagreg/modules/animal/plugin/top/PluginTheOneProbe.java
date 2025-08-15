package su.terrafirmagreg.modules.animal.plugin.top;

import su.terrafirmagreg.api.data.enums.Mods.ModIDs;
import su.terrafirmagreg.framework.manager.plugin.base.BasePlugin;
import su.terrafirmagreg.framework.module.spi.StateEvent;
import su.terrafirmagreg.modules.animal.plugin.top.provider.AnimalProvider;

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
    final ITheOneProbe oneProbe = TheOneProbe.theOneProbeImp;

    oneProbe.registerEntityProvider(new AnimalProvider());
  }


}
