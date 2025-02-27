package net.dries007.tfc.compat.waila;

import mcjty.theoneprobe.api.ITheOneProbe;
import net.dries007.tfc.compat.waila.interfaces.TOPBlockInterface;
import net.dries007.tfc.compat.waila.providers.BarrelProvider;
import net.dries007.tfc.compat.waila.providers.BerryBushProvider;
import net.dries007.tfc.compat.waila.providers.CropProvider;
import net.dries007.tfc.compat.waila.providers.FruitTreeProvider;
import net.dries007.tfc.compat.waila.providers.InfoProvider;
import net.dries007.tfc.compat.waila.providers.IngotPileProvider;
import net.dries007.tfc.compat.waila.providers.LampProvider;
import net.dries007.tfc.compat.waila.providers.LogPileProvider;
import net.dries007.tfc.compat.waila.providers.OreProvider;
import net.dries007.tfc.compat.waila.providers.PlacedItemProvider;
import net.dries007.tfc.compat.waila.providers.QuernProvider;
import net.dries007.tfc.compat.waila.providers.TreeProvider;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class TOPPlugin implements Function<ITheOneProbe, Void> {

  public static final List<TOPBlockInterface> TOP_BLOCK_INTERFACES = Arrays.asList(
    new TOPBlockInterface(new BarrelProvider()),
    new TOPBlockInterface(new BerryBushProvider()),
    new TOPBlockInterface(new LampProvider()),
    new TOPBlockInterface(new CropProvider()),
    new TOPBlockInterface(new FruitTreeProvider()),
    new TOPBlockInterface(new OreProvider()),
    new TOPBlockInterface(new PlacedItemProvider()),
    new TOPBlockInterface(new InfoProvider()),
    new TOPBlockInterface(new TreeProvider()),
    new TOPBlockInterface(new IngotPileProvider()),
    new TOPBlockInterface(new LogPileProvider()),
    new TOPBlockInterface(new QuernProvider())
  );


  @Override
  public Void apply(ITheOneProbe probe) {
    for (TOPBlockInterface blockInterface : TOP_BLOCK_INTERFACES) {
      probe.registerProvider(blockInterface);
      if (blockInterface.overridesHeadInfo()) {
        probe.registerBlockDisplayOverride(blockInterface);
      }
    }
    return null;
  }
}
