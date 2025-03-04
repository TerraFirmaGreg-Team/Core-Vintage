package su.terrafirmagreg.mixin.gregtech.common.metatileentities.multi;

import su.terrafirmagreg.temp.config.TFGConfig;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import gregtech.api.metatileentity.multiblock.IPrimitivePump;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.unification.material.Materials;
import gregtech.common.metatileentities.multi.MetaTileEntityPrimitiveWaterPump;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MetaTileEntityPrimitiveWaterPump.class, remap = false)
public abstract class MetaTileEntityPrimitiveWaterPumpMixin extends MultiblockControllerBase implements IPrimitivePump {

  @Shadow
  private IFluidTank waterTank;
  @Shadow
  private int biomeModifier = 0;

  public MetaTileEntityPrimitiveWaterPumpMixin(ResourceLocation metaTileEntityId) {
    super(metaTileEntityId);
  }

  @Shadow
  public abstract int getFluidProduction();

  @Shadow
  protected abstract int getAmount();

  @Inject(method = "update", at = @At(value = "HEAD"), cancellable = true)
  public void onUpdate(CallbackInfo ci) {
    super.update();
    if (!getWorld().isRemote && getOffsetTimer() % 20 == 0 && isStructureFormed()) {
      if (biomeModifier == 0) {
        biomeModifier = getAmount();
      } else if (biomeModifier > 0) {
        FluidStack fluidStack = FluidRegistry.getFluidStack(TFGConfig.General.fluidForPrimitivePump, getFluidProduction());
        if (fluidStack != null) {
          waterTank.fill(fluidStack, true);
        } else {
          waterTank.fill(Materials.SaltWater.getFluid(getFluidProduction()), true);
        }
      }
    }
    ci.cancel();
  }
}
