package su.terrafirmagreg.api.registry.spi;

import su.terrafirmagreg.api.base.fluid.spi.IFluidSettings;

import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.function.Function;

public interface IRegistryFluid
  extends IRegistryBlock {


  default Fluid fluid(Fluid fluid, Function<Fluid, Block> fluidBlock, String name) {

    final boolean useOwnFluid = FluidRegistry.registerFluid(fluid);
    if (!useOwnFluid) {
      var block = fluidBlock.apply(fluid);
      block(block, "fluid/" + name);

    } else {
      fluid = FluidRegistry.getFluid(name);
    }
    FluidRegistry.addBucketForFluid(fluid);

    return fluid;
  }

  default <F extends Fluid & IFluidSettings> Fluid fluid(F fluid) {
    var settings = fluid.getSettings();
    return this.fluid(fluid, settings.getFluidBlock(), settings.getRegistryKey());
  }
}
