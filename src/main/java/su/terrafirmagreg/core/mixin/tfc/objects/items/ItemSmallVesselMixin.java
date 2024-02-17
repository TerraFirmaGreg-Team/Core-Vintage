package su.terrafirmagreg.core.mixin.tfc.objects.items;

import net.dries007.tfc.objects.blocks.BlockLargeVessel;
import net.dries007.tfc.objects.items.ceramics.ItemPottery;
import net.dries007.tfc.objects.items.ceramics.ItemSmallVessel;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.ItemStackHandler;

import com.eerussianguy.firmalife.items.ItemFoodFL;
import net.dries007.tfc.api.capability.ISmallVesselHandler;
import net.dries007.tfc.objects.inventory.capability.ISlotCallback;
import net.dries007.tfc.objects.items.food.ItemFoodTFC;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfcflorae.objects.items.food.ItemFoodTFCF;

import static net.dries007.tfc.api.capability.heat.CapabilityItemHeat.ITEM_HEAT_CAPABILITY;

@Mixin(targets = "net.dries007.tfc.objects.items.ceramics.ItemSmallVessel$SmallVesselCapability", remap = false)
public abstract class ItemSmallVesselMixin extends ItemStackHandler implements ICapabilityProvider, ISmallVesselHandler, ISlotCallback {

    /**
     * @author SpeeeDCraft
     * @reason Strict item test on insert in vessel
     */
    @Inject(method = "isItemValid", at = @At(value = "INVOKE", target = "Lnet/dries007/tfc/api/capability/size/Size;isSmallerThan(Lnet/dries007/tfc/api/capability/size/Size;)Z", shift = At.Shift.AFTER), remap = false, cancellable = true)
    private void onIsItemValid(int slot, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.hasCapability(ITEM_HEAT_CAPABILITY, null) && !(stack.getItem() instanceof ItemPottery)) {
            cir.setReturnValue(true);
        } else if (stack.getItem() instanceof ItemFoodTFC) {
            cir.setReturnValue(true);
        } else if (stack.getItem() instanceof ItemFoodTFCF) {
            cir.setReturnValue(true);
        } else if (stack.getItem() instanceof ItemFoodFL) {
            cir.setReturnValue(true);
        } else {
            cir.setReturnValue(false);
        }
    }
}
