package net.dries007.tfc.compat.waila;

import mcjty.theoneprobe.api.ITheOneProbe;
import net.dries007.tfc.compat.waila.interfaces.TOPBlockInterface;
import net.dries007.tfc.compat.waila.interfaces.TOPEntityInterface;
import net.dries007.tfc.compat.waila.providers.AnimalProvider;
import net.dries007.tfc.compat.waila.providers.BarrelProvider;
import net.dries007.tfc.compat.waila.providers.BerryBushProvider;
import net.dries007.tfc.compat.waila.providers.CropProvider;
import net.dries007.tfc.compat.waila.providers.FruitTreeProvider;
import net.dries007.tfc.compat.waila.providers.IngotPileProvider;
import net.dries007.tfc.compat.waila.providers.OreProvider;
import net.dries007.tfc.compat.waila.providers.PlacedItemProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class TOPPlugin implements Function<ITheOneProbe, Void> {

  public static final List<TOPBlockInterface> TOP_BLOCK_INTERFACES = Arrays.asList(
    new TOPBlockInterface(new BarrelProvider()),
    new TOPBlockInterface(new BerryBushProvider()),
    new TOPBlockInterface(new CropProvider()),
    new TOPBlockInterface(new FruitTreeProvider()),
    new TOPBlockInterface(new OreProvider()),
    new TOPBlockInterface(new PlacedItemProvider()),
    new TOPBlockInterface(new IngotPileProvider())
  );

  public static final List<TOPEntityInterface> TOP_ENTITY_INTERFACES = Collections.singletonList(
    new TOPEntityInterface(new AnimalProvider())
  );

  @Override
  public Void apply(ITheOneProbe probe) {
    for (TOPBlockInterface blockInterface : TOP_BLOCK_INTERFACES) {
      probe.registerProvider(blockInterface);
      if (blockInterface.overridesHeadInfo()) {
        probe.registerBlockDisplayOverride(blockInterface);
      }
    }
    for (TOPEntityInterface entityInterface : TOP_ENTITY_INTERFACES) {
      probe.registerEntityProvider(entityInterface);
      if (entityInterface.overridesHeadInfo()) {
        probe.registerEntityDisplayOverride(entityInterface);
      }
    }
    return null;
  }
}
