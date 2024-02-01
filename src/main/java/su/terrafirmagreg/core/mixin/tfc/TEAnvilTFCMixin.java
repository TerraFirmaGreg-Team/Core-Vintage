package su.terrafirmagreg.core.mixin.tfc;

import net.minecraft.item.ItemStack;

import gregtech.common.items.ToolItems;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.objects.te.TEAnvilTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = TEAnvilTFC.class, remap = false)
public class TEAnvilTFCMixin {

    /**
     * @author SpeeeDCraft
     * @reason
     */
    @Overwrite
    public boolean isItemValid(int slot, ItemStack stack) {
        return switch (slot) {
            case 0, 1 -> stack.hasCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
            case 2 -> stack.getItem() == ToolItems.HARD_HAMMER;
            case 3 -> OreDictionaryHelper.doesStackMatchOre(stack, "dustFlux");
            default -> false;
        };
    }
}
