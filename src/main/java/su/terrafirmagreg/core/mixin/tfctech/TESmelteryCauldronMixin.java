package su.terrafirmagreg.core.mixin.tfctech;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import tfctech.objects.tileentities.TESmelteryCauldron;

@Mixin(value = TESmelteryCauldron.class, remap = false)
public class TESmelteryCauldronMixin {

    @Shadow
    @Final
    public static final int FLUID_CAPACITY = 4608;
}
