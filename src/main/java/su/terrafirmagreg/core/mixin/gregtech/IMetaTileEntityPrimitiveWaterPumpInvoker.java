package su.terrafirmagreg.core.mixin.gregtech;

import gregtech.common.metatileentities.multi.MetaTileEntityPrimitiveWaterPump;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = MetaTileEntityPrimitiveWaterPump.class, remap = false)
public interface IMetaTileEntityPrimitiveWaterPumpInvoker {

    @Invoker
    int invokeGetAmount();
}
