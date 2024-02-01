package su.terrafirmagreg.core.mixin.tfc.objects.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.forge.ForgeableMeasurableMetalHandler;
import net.dries007.tfc.api.capability.forge.IForgeable;
import net.dries007.tfc.api.capability.forge.IForgeableMeasurableMetal;
import net.dries007.tfc.api.capability.metal.IMetalItem;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.ItemBloom;
import net.dries007.tfc.objects.items.ItemTFC;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nonnull;

@Mixin(value = ItemBloom.class, remap = false)
public abstract class ItemBloomMixin extends ItemTFC implements IMetalItem {

    /**
     * @author SpeeeDCraft
     * @reason Set 144mb for iron parts which are dropped from Blast Furnace
     */
    @Overwrite
    public int getSmeltAmount(ItemStack stack) {
        IForgeable cap = stack.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
        if (cap instanceof IForgeableMeasurableMetal) {
            int amount = ((IForgeableMeasurableMetal) cap).getMetalAmount();
            if (amount > 144) amount = 144;
            return amount;
        }
        return 0;
    }

    /**
     * @author SpeeeDCraft
     * @reason Display 144mb of Iron for IronBloom
     */
    @SideOnly(Side.CLIENT)
    @Overwrite
    public void func_150895_a(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            for (int i = 144; i <= 576; i += 144) {
                ItemStack stack = new ItemStack(this);
                IForgeable cap = stack.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal handler) {
                    handler.setMetal(Metal.WROUGHT_IRON);
                    handler.setMetalAmount(i);
                    items.add(stack);
                }
            }
        }
    }

    /**
     * @author SpeeeDCraft
     * @reason Initialize 144mb Iron for IronBloom
     */
    @Inject(method = "initCapabilities", at = @At(value = "HEAD"), remap = false, cancellable = true)
    public void initCapabilities(ItemStack stack, NBTTagCompound nbt, CallbackInfoReturnable<ICapabilityProvider> cir) {
        if (nbt == null) {
            cir.setReturnValue(new ForgeableMeasurableMetalHandler(Metal.WROUGHT_IRON, 144));
        } else {
            cir.setReturnValue(new ForgeableMeasurableMetalHandler(nbt));
        }
    }
}
