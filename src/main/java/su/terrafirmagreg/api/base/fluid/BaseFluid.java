package su.terrafirmagreg.api.base.fluid;

import su.terrafirmagreg.api.base.fluid.spi.IFluidSettings;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import lombok.Getter;

import static su.terrafirmagreg.api.data.Reference.MODID_TFC;

@Getter
public abstract class BaseFluid extends Fluid implements IFluidSettings {

  public static final ResourceLocation STILL = new ResourceLocation(MODID_TFC, "blocks/fluid_still");
  public static final ResourceLocation FLOW = new ResourceLocation(MODID_TFC, "blocks/fluid_flow");

  private static final ResourceLocation LAVA_STILL = new ResourceLocation(MODID_TFC, "blocks/lava_still");
  private static final ResourceLocation LAVA_FLOW = new ResourceLocation(MODID_TFC, "blocks/lava_flow");
  private final Settings settings;

  public BaseFluid(Settings settings) {
    super(settings.getRegistryKey(), settings.getStill(), settings.getFlowing(), settings.getOverlay(), settings.getColor());

    this.settings = settings;
  }
}
