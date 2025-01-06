package tfctech.objects.fluids;

import su.terrafirmagreg.api.data.enums.Mods;
import su.terrafirmagreg.modules.core.init.FluidsCore;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import com.google.common.collect.HashBiMap;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;

import javax.annotation.Nonnull;

public final class TechFluids {

  private static final ResourceLocation LAVA_STILL = new ResourceLocation(Mods.Names.TFC, "blocks/lava_still");
  private static final ResourceLocation LAVA_FLOW = new ResourceLocation(Mods.Names.TFC, "blocks/lava_flow");
  private static final HashBiMap<Fluid, FluidWrapper> WRAPPERS = HashBiMap.create();

  public static void registerFluids() {
    FluidsCore.LATEX = registerFluid(new Fluid("latex", LAVA_STILL, LAVA_FLOW, 0xFFF8F8F8));
    FluidsCore.GLASS = registerFluid(new Fluid("glass", LAVA_STILL, LAVA_FLOW, 0xFFED97B5));

    setFluidTemperatures();
  }

  private static void setFluidTemperatures() {
    FluidsCore.GLASS.get().setTemperature(1073); // Kelvin
  }

  private static FluidWrapper registerFluid(@Nonnull Fluid newFluid) {
    boolean isDefault = FluidRegistry.registerFluid(newFluid);
    if (!isDefault) {
      // Fluid was already registered with this name, default to that fluid
      newFluid = FluidRegistry.getFluid(newFluid.getName());
    }
    FluidRegistry.addBucketForFluid(newFluid);
    FluidWrapper properties = new FluidWrapper(newFluid, isDefault);
    WRAPPERS.put(newFluid, properties);
    return properties;
  }
}
