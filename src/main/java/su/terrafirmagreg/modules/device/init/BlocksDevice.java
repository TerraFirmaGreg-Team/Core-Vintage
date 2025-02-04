package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.framework.registry.api.IRegistryManager;
import su.terrafirmagreg.modules.device.object.block.BlockAlloyCalculator;

import java.util.function.Supplier;

public final class BlocksDevice {


  public static Supplier<BlockAlloyCalculator> ALLOY_CALCULATOR;


  public static void onRegister(IRegistryManager registry) {

    ALLOY_CALCULATOR = registry.block(new BlockAlloyCalculator());

  }
}
