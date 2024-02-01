package su.terrafirmagreg.core.mixin.tfc;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;

import net.dries007.tfc.objects.te.ITileFields;
import net.dries007.tfc.objects.te.TEBlastFurnace;
import net.dries007.tfc.objects.te.TETickableInventory;
import net.dries007.tfc.util.Alloy;
import net.dries007.tfc.util.Helpers;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfctech.objects.tileentities.TEInductionCrucible;

import java.util.List;

@Mixin(value = TEBlastFurnace.class, remap = false)
public abstract class TEBlastFurnaceMixin extends TETickableInventory implements ITickable, ITileFields {

    @Shadow
    @Final
    @Mutable
    private final List<ItemStack> fuelStacks;
    @Shadow
    @Final
    @Mutable
    private final List<ItemStack> oreStacks;
    @Shadow
    @Final
    @Mutable
    private final Alloy alloy;
    @Shadow
    private int airTicks;

    protected TEBlastFurnaceMixin(int inventorySize, int airTicks, List<ItemStack> fuelStacks, List<ItemStack> oreStacks, Alloy alloy) {
        super(inventorySize);
        this.airTicks = airTicks;
        this.fuelStacks = fuelStacks;
        this.oreStacks = oreStacks;
        this.alloy = alloy;
    }

    /**
     * @author SpeeeDCraft
     * @reason Allow to use TFC-Tech crucible under the blast furnace
     */
    @Inject(method = "update", at = @At(value = "INVOKE", target = "Lnet/dries007/tfc/util/Alloy;removeAlloy(IZ)I", ordinal = 0))
    private void update(CallbackInfo ci) {

        TEInductionCrucible teI = Helpers.getTE(world, pos.down(), TEInductionCrucible.class);
        if (teI != null && teI.addMetal(alloy.getResult(), 1) <= 0) {
            alloy.removeAlloy(1, false);
        }
    }

}
