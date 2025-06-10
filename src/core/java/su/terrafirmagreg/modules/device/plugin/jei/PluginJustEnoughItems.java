package su.terrafirmagreg.modules.device.plugin.jei;

import su.terrafirmagreg.api.data.enums.Mods.ModIDs;
import su.terrafirmagreg.framework.manager.plugin.base.BasePlugin;

public class PluginJustEnoughItems extends BasePlugin {
  

  public PluginJustEnoughItems() {
    super(Settings.of()
      .registryKey("jei")
      .modRequired(ModIDs.JEI)
    );
  }


}
