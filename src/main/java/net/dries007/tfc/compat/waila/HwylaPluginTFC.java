package net.dries007.tfc.compat.waila;

import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.WailaPlugin;
import net.dries007.tfc.compat.waila.interfaces.HwylaBlockInterface;
import net.dries007.tfc.compat.waila.interfaces.HwylaEntityInterface;
import net.dries007.tfc.compat.waila.providers.AnimalProvider;
import net.dries007.tfc.compat.waila.providers.BarrelProvider;
import net.dries007.tfc.compat.waila.providers.BerryBushProvider;
import net.dries007.tfc.compat.waila.providers.CropProvider;
import net.dries007.tfc.compat.waila.providers.FruitTreeProvider;
import net.dries007.tfc.compat.waila.providers.IngotPileProvider;
import net.dries007.tfc.compat.waila.providers.OreProvider;
import net.dries007.tfc.compat.waila.providers.PlacedItemProvider;
import net.dries007.tfc.compat.waila.providers.TreeProvider;

import java.util.Arrays;
import java.util.List;

@WailaPlugin
public class HwylaPluginTFC implements IWailaPlugin {

  public static final List<IWailaPlugin> WAILA_PLUGINS = Arrays.asList(
          new HwylaEntityInterface(new AnimalProvider()),
          new HwylaBlockInterface(new BarrelProvider()),
          new HwylaBlockInterface(new BerryBushProvider()),
          new HwylaBlockInterface(new CropProvider()),
          new HwylaBlockInterface(new FruitTreeProvider()),
          new HwylaBlockInterface(new OreProvider()),
          new HwylaBlockInterface(new PlacedItemProvider()),
          new HwylaBlockInterface(new TreeProvider()),
          new HwylaBlockInterface(new IngotPileProvider())
  );

  @Override
  public void register(IWailaRegistrar registrar) {
    for (IWailaPlugin plugin : WAILA_PLUGINS) {
      plugin.register(registrar);
    }
  }
}
